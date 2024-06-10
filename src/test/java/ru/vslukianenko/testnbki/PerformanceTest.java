package ru.vslukianenko.testnbki;


import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vslukianenko.testnbki.model.BankRecord;
import ru.vslukianenko.testnbki.repo.BankRecordRepository;
import ru.vslukianenko.testnbki.service.BankRecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Тесты производительности для операций с записями банка.
 *
 * <p>Этот класс тестирует производительность создания и чтения записей в базе данных.</p>
 *
 * <p>Константы:</p>
 * <ul>
 *   <li>{@code NUM_RECORDS} - количество записей для тестов создания и чтения.</li>
 *   <li>{@code NUM_CONNECTIONS} - количество параллельных потоков для тестов чтения.</li>
 *   <li>{@code NUM_QUERIES} - количество запросов для тестов чтения.</li>
 * </ul>
 */
@SpringBootTest
public class PerformanceTest {

    private final int NUM_RECORDS = 100_000;
    private final int NUM_CONNECTIONS = 100;
    private final int NUM_QUERIES = 1_000_000;

    @Autowired
    private BankRecordRepository recordRepository;

    @Autowired
    private BankRecordService recordService;
    /**
     * Устанавливает начальные условия перед каждым тестом.
     *
     * <p>Удаляет все записи из репозитория перед запуском каждого теста.</p>
     */
    @BeforeEach
    public void setUp() {
        recordRepository.deleteAll();
    }

    /**
     * Тестирует создание записей в базе данных.
     *
     * <p>Создает {@code NUM_RECORDS} записей и проверяет, что все они были успешно сохранены.</p>
     */
    @Test
    public void testCreateRecords() {
        List<BankRecord> records = new ArrayList<>();

        for (int i = 0; i < NUM_RECORDS; i++) {
            BankRecord record = new BankRecord();
            record.setData("record"+i);
            records.add(record);
        }
        recordService.batchCreateBankRecords(records);
        int currentSize = recordRepository.findAll().size();
        assertEquals(currentSize, NUM_RECORDS);
    }



    /**
     * Тестирует чтение записей из базы данных.
     *
     * <p>Создает и обновляет {@code NUM_RECORDS} записей, затем выполняет {@code NUM_QUERIES} параллельных запросов для чтения случайных записей.</p>
     *
     * <p>Измеряет и выводит статистику времени выполнения запросов.</p>
     *
     * @throws InterruptedException если выполнение потока было прервано
     * @throws ExecutionException если выполнение потока завершилось с ошибкой
     */
    @Test
    public void testReadRecords() throws InterruptedException, ExecutionException {

        List<BankRecord> records = new ArrayList<>();
        List<UUID> ids = new ArrayList<>();
        for (int i = 0; i < NUM_RECORDS; i++) {
            BankRecord record = new BankRecord();
            record.setData("record "+i);
            ids.add(UUID.randomUUID());
            record.setId(ids.get(i));
            records.add(record);
        }
        recordService.batchUpdateBankRecords(records);

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_CONNECTIONS);

        List<Callable<Long>> tasks = new ArrayList<>();

        for (int i = 0; i < NUM_QUERIES; i++) {
            tasks.add(() -> {
                Random random = new Random();
                long startTime = System.nanoTime();
                recordService.getBankRecordById(ids.get(random.nextInt(NUM_RECORDS-1)));
                long endTime = System.nanoTime();
                return endTime - startTime;
            });
        }

        List<Future<Long>> results = executorService.invokeAll(tasks);
        executorService.shutdown();

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Future<Long> result : results) {
            stats.addValue(result.get() / 1_000_000.0);
        }

        System.out.println("Total time: " + stats.getSum() + " ms");
        System.out.println("Median time: " + stats.getPercentile(50) + " ms");
        System.out.println("95th percentile: " + stats.getPercentile(95) + " ms");
        System.out.println("99th percentile: " + stats.getPercentile(99) + " ms");
    }
}
