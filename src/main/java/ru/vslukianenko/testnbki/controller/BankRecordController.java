package ru.vslukianenko.testnbki.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vslukianenko.testnbki.model.BankRecord;
import ru.vslukianenko.testnbki.service.BankRecordService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Контроллер для управления записями банка.
 * Обеспечивает API для создания, получения, обновления и удаления записей.
 *
 * <p>Аннотации:</p>
 * <ul>
 *   <li>{@link RestController} - Указывает, что этот класс является контроллером в стиле REST.</li>
 *   <li>{@link RequestMapping} - Устанавливает базовый путь для всех эндпоинтов этого контроллера.</li>
 *   <li>{@link RequiredArgsConstructor} - Автоматически генерирует конструктор с обязательными аргументами (final поля).</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class BankRecordController {


    private final BankRecordService bankRecordService;
    /**
     * Создает новую запись банка.
     *
     * @param bankRecord объект {@link BankRecord}, представляющий данные новой записи
     * @return созданная запись банка
     */
    @PostMapping
    public BankRecord createBankRecord(@RequestBody BankRecord bankRecord) {
        return bankRecordService.createBankRecord(bankRecord);
    }
    /**
     * Массовое создание записей банка.
     *
     * @param bankRecords список объектов {@link BankRecord}, представляющих данные новых записей
     */
    @PostMapping("/list")
    public void batchCreateBankRecords(@RequestBody List<BankRecord> bankRecords) {
        bankRecordService.batchCreateBankRecords(bankRecords);
    }
    /**
     * Получает запись банка по её идентификатору.
     *
     * @param id уникальный идентификатор записи банка
     * @return {@link ResponseEntity} с найденной записью банка или статусом 404, если запись не найдена
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankRecord> getBankRecordById(@PathVariable UUID id) {
        Optional<BankRecord> bankRecord = bankRecordService.getBankRecordById(id);
        return bankRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Обновляет существующую запись банка.
     *
     * @param id уникальный идентификатор записи банка
     * @param bankRecordDetails объект {@link BankRecord}, содержащий обновленные данные
     * @return {@link ResponseEntity} с обновленной записью банка или статусом 404, если запись не найдена
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankRecord> updateBankRecord(@PathVariable UUID id, @RequestBody BankRecord bankRecordDetails) {
        Optional<BankRecord> updatedBankRecord = bankRecordService.updateBankRecord(id, bankRecordDetails);
        return updatedBankRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Массовое обновление записей банка.
     *
     * @param bankRecords список объектов {@link BankRecord}, представляющих обновленные данные записей
     */
    @PutMapping("/list")
    public void batchUpdateBankRecords(@RequestBody List<BankRecord> bankRecords) {
        bankRecordService.batchUpdateBankRecords(bankRecords);
    }
    /**
     * Удаляет запись банка по её идентификатору.
     *
     * @param id уникальный идентификатор записи банка
     * @return {@link ResponseEntity} со статусом 204, если удаление прошло успешно, или статусом 404, если запись не найдена
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankRecord(@PathVariable UUID id) {
        if (bankRecordService.deleteBankRecord(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Массовое удаление записей банка.
     *
     * @param ids список уникальных идентификаторов записей банка для удаления
     */
    @DeleteMapping("/list")
    public void batchDeleteBankRecords(@RequestBody List<UUID> ids) {
        bankRecordService.batchDeleteBankRecords(ids);
    }
}