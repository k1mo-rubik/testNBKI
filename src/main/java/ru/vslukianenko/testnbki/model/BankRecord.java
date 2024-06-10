package ru.vslukianenko.testnbki.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Представляет запись в базе данных банка.
 * Каждая запись имеет уникальный идентификатор и связанные с ней данные.
 *
 * <p>Этот класс аннотирован JPA аннотациями для отображения его в таблицу базы данных,
 * а также аннотациями Lombok для генерации стандартного кода, такого как геттеры и сеттеры.</p>
 *
 * <p>Аннотации:</p>
 * <ul>
 *   <li>{@link Entity} - Указывает, что класс является сущностью и отображается в таблицу базы данных.</li>
 *   <li>{@link Getter} - Аннотация Lombok для генерации методов получения (геттеров) для всех полей.</li>
 *   <li>{@link Setter} - Аннотация Lombok для генерации методов установки (сеттеров) для всех полей.</li>
 *   <li>{@link Id} - Указывает первичный ключ сущности.</li>
 *   <li>{@link GeneratedValue} - Определяет стратегии генерации значений для первичных ключей.</li>
 * </ul>
 *
 * <p>Поля:</p>
 * <ul>
 *   <li>{@code id} - Уникальный идентификатор записи банка, генерируется автоматически.</li>
 *   <li>{@code data} - Произвольные данные, связанные с записью банка.</li>
 * </ul>
 *
 * <p>Пример использования:</p>
 * <pre>
 *     BankRecord record = new BankRecord();
 *     record.setData("Пример данных");
 *     UUID id = record.getId();
 *     String data = record.getData();
 * </pre>
 *
 * @see Entity
 * @see Getter
 * @see Setter
 * @see Id
 * @see GeneratedValue
 */

@Entity
@Getter
@Setter
public class BankRecord {
    /**
     * Уникальный идентификатор записи банка.
     * Генерируется автоматически с использованием {@link GenerationType#AUTO}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    /**
     * Произвольные данные, связанные с записью банка.
     */
    private String data;

}
