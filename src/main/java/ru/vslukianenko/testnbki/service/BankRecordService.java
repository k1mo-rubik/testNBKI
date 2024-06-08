package ru.vslukianenko.testnbki.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vslukianenko.testnbki.model.BankRecord;
import ru.vslukianenko.testnbki.repo.BankRecordRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankRecordService {


    private final BankRecordRepository bankRecordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public BankRecord createBankRecord(BankRecord bankRecord) {
        return bankRecordRepository.save(bankRecord);
    }

    public Optional<BankRecord> getBankRecordById(UUID id) {
        return bankRecordRepository.findById(id);
    }

    public Optional<BankRecord> updateBankRecord(UUID id, BankRecord bankRecordDetails) {
        return bankRecordRepository.findById(id).map(bankRecord -> {
            bankRecord.setData(bankRecordDetails.getData());
            return bankRecordRepository.save(bankRecord);
        });
    }

    public boolean deleteBankRecord(UUID id) {
        if (bankRecordRepository.existsById(id)) {
            bankRecordRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void batchCreateBankRecords(List<BankRecord> bankRecords) {
        int batchSize = 1000;
        for (int i = 0; i < bankRecords.size(); i++) {
            entityManager.persist(bankRecords.get(i));
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }


    @Transactional
    public void batchUpdateBankRecords(List<BankRecord> bankRecords) {
        int batchSize = 1000;
        for (int i = 0; i < bankRecords.size(); i++) {
            entityManager.merge(bankRecords.get(i));
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Transactional
    public void batchDeleteBankRecords(List<UUID> ids) {
        int batchSize = 1000;
        for (int i = 0; i < ids.size(); i++) {
            BankRecord bankRecord = entityManager.find(BankRecord.class, ids.get(i));
            if (bankRecord != null) {
                entityManager.remove(bankRecord);
            }
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }


}
