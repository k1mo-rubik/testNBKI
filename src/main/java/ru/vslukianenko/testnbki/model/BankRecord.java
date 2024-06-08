package ru.vslukianenko.testnbki.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class BankRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String data;

}
