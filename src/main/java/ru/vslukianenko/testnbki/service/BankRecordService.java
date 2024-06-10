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
/**
 * Сервис для управления записями банка.
 * Обеспечивает методы для создания, получения, обновления и удаления записей,
 * а также массовые операции с записями.
 *
 * <p>Аннотации:</p>
 * <ul>
 *   <li>{@link Service} - Указывает, что этот класс является сервисом Spring.</li>
 *   <li>{@link RequiredArgsConstructor} - Автоматически генерирует конструктор с обязательными аргументами (final поля).</li>
 *   <li>{@link Transactional} - Обеспечивает управление транзакциями для методов, выполняющих массовые операции.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class BankRecordService {


    private final BankRecordRepository bankRecordRepository;

    @PersistenceContext
    private EntityManager entityManager;
    /**
     * Создает новую запись банка.
     *
     * @param bankRecord объект {@link BankRecord}, представляющий данные новой записи
     * @return созданная запись банка
     */
    public BankRecord createBankRecord(BankRecord bankRecord) {
        return bankRecordRepository.save(bankRecord);
    }
    /**
     * Получает запись банка по её идентификатору.
     *
     * @param id уникальный идентификатор записи банка
     * @return {@link Optional} с найденной записью банка или пустой {@link Optional}, если запись не найдена
     */
    public Optional<BankRecord> getBankRecordById(UUID id) {
        return bankRecordRepository.findById(id);
    }
    /**
     * Обновляет существующую запись банка.
     *
     * @param id уникальный идентификатор записи банка
     * @param bankRecordDetails объект {@link BankRecord}, содержащий обновленные данные
     * @return {@link Optional} с обновленной записью банка или пустой {@link Optional}, если запись не найдена
     */
    public Optional<BankRecord> updateBankRecord(UUID id, BankRecord bankRecordDetails) {
        return bankRecordRepository.findById(id).map(bankRecord -> {
            bankRecord.setData(bankRecordDetails.getData());
            return bankRecordRepository.save(bankRecord);
        });
    }
    /**
     * Удаляет запись банка по её идентификатору.
     *
     * @param id уникальный идентификатор записи банка
     * @return true, если запись успешно удалена, иначе false
     */
    public boolean deleteBankRecord(UUID id) {
        if (bankRecordRepository.existsById(id)) {
            bankRecordRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Массовое создание записей банка.
     *
     * @param bankRecords список объектов {@link BankRecord}, представляющих данные новых записей
     */
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

    /**
     * Массовое обновление записей банка.
     *
     * @param bankRecords список объектов {@link BankRecord}, представляющих обновленные данные записей
     */
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
    /**
     * Массовое удаление записей банка.
     *
     * @param ids список уникальных идентификаторов записей банка для удаления
     */
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
