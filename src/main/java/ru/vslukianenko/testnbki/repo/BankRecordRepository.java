package ru.vslukianenko.testnbki.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vslukianenko.testnbki.model.BankRecord;

import java.util.UUID;

public interface BankRecordRepository extends JpaRepository<BankRecord, UUID> {
}
