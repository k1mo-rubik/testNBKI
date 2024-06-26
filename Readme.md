# Тестирование производительности операций с записями банка

Этот проект представляет собой Spring Boot приложение, которое предоставляет API для управления записями банка, а также включает тесты производительности для создания и чтения записей в базе данных.

## Структура проекта

Проект состоит из следующих основных компонентов:

- `model` - содержит модель данных `BankRecord`, которая представляет запись банка.
- `repo` - содержит репозиторий `BankRecordRepository` для взаимодействия с базой данных.
- `service` - содержит сервис `BankRecordService` для выполнения операций с записями банка.
- `controller` - содержит контроллер `BankRecordController` для обработки HTTP-запросов.
- `PerformanceTest` - содержит тесты производительности для создания и чтения записей в базе данных.

## Начало работы

### Предварительные требования

- Java 17 или выше
- Maven 4.0.0 или выше

### Установка

1. Клонируйте репозиторий:
    ```sh
    git clone https://github.com/k1mo-rubik/testNBKI.git
    ```
2. Перейдите в директорию проекта:
    ```sh
    cd ./testNBKI
    ```

### Запуск приложения

1. Соберите проект с помощью Maven:
    ```sh
    mvn clean install
    ```
2. Запустите приложение:
    ```sh
    mvn spring-boot:run
    ```

Приложение будет доступно по адресу `http://localhost:8080`.

### Использование API

- Создание записи:
    ```http
    POST /api/records
    Content-Type: application/json

    {
        "data": "Пример данных"
    }
    ```

- Массовое создание записей:
    ```http
    POST /api/records/list
    Content-Type: application/json

    [
        { "data": "Пример данных 1" },
        { "data": "Пример данных 2" }
    ]
    ```

- Получение записи по ID:
    ```http
    GET /api/records/{id}
    ```

- Обновление записи:
    ```http
    PUT /api/records/{id}
    Content-Type: application/json

    {
        "data": "Обновленные данные"
    }
    ```

- Массовое обновление записей:
    ```http
    PUT /api/records/list
    Content-Type: application/json

    [
        { "id": "UUID1", "data": "Обновленные данные 1" },
        { "id": "UUID2", "data": "Обновленные данные 2" }
    ]
    ```

- Удаление записи:
    ```http
    DELETE /api/records/{id}
    ```

- Массовое удаление записей:
    ```http
    DELETE /api/records/list
    Content-Type: application/json

    [
        "UUID1",
        "UUID2"
    ]
    ```

## Тесты производительности

Тесты производительности находятся в классе `PerformanceTest`. Они измеряют производительность операций создания и чтения записей.

### Запуск тестов

Для запуска тестов выполните следующую команду:
```sh
mvn test
