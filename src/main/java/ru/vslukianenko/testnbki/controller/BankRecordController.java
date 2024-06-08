package ru.vslukianenko.testnbki.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class BankRecordController {

    private BankRecordService recordService;

    @PostMapping
    public BankRecord createRecord(@RequestBody BankRecord record) {
        return recordService.createRecord(record);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankRecord> getRecordById(@PathVariable UUID id) {
        Optional<BankRecord> record = recordService.getRecordById(id);
        return record.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankRecord> updateRecord(@PathVariable UUID id, @RequestBody BankRecord recordDetails) {
        Optional<BankRecord> updatedRecord = recordService.updateRecord(id, recordDetails);
        return updatedRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable UUID id) {
        if (recordService.deleteRecord(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
