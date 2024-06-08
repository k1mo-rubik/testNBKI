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

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class BankRecordController {


    private final BankRecordService bankRecordService;

    @PostMapping
    public BankRecord createBankRecord(@RequestBody BankRecord bankRecord) {
        return bankRecordService.createBankRecord(bankRecord);
    }

    @PostMapping("/list")
    public void batchCreateBankRecords(@RequestBody List<BankRecord> bankRecords) {
        bankRecordService.batchCreateBankRecords(bankRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankRecord> getBankRecordById(@PathVariable UUID id) {
        Optional<BankRecord> bankRecord = bankRecordService.getBankRecordById(id);
        return bankRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankRecord> updateBankRecord(@PathVariable UUID id, @RequestBody BankRecord bankRecordDetails) {
        Optional<BankRecord> updatedBankRecord = bankRecordService.updateBankRecord(id, bankRecordDetails);
        return updatedBankRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/list")
    public void batchUpdateBankRecords(@RequestBody List<BankRecord> bankRecords) {
        bankRecordService.batchUpdateBankRecords(bankRecords);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankRecord(@PathVariable UUID id) {
        if (bankRecordService.deleteBankRecord(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/list")
    public void batchDeleteBankRecords(@RequestBody List<UUID> ids) {
        bankRecordService.batchDeleteBankRecords(ids);
    }
}