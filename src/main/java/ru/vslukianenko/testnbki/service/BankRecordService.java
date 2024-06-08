package ru.vslukianenko.testnbki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vslukianenko.testnbki.model.BankRecord;
import ru.vslukianenko.testnbki.repo.BankRecordRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankRecordService {


    private BankRecordRepository recordRepository;

    public BankRecord createRecord(BankRecord record) {
        return recordRepository.save(record);
    }

    public Optional<BankRecord> getRecordById(UUID id) {
        return recordRepository.findById(id);
    }

    public Optional<BankRecord> updateRecord(UUID id, BankRecord recordDetails) {
        return recordRepository.findById(id).map(record -> {
            record.setData(recordDetails.getData());
            return recordRepository.save(record);
        });
    }

    public boolean deleteRecord(UUID id) {
        if (recordRepository.existsById(id)) {
            recordRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
