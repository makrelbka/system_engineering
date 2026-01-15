# FlowerShop Backend

Backend приложение для системы онлайн-заказа цветов на Spring Boot.

## Технологии

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory)
- WebSocket (STOMP) для обновления фронтенда в реальном времени
- Lombok

## Требования

- Java 17 или выше
- Maven (или используйте Maven Wrapper - `./mvnw`)

## Запуск

### Вариант 1: С Maven Wrapper (рекомендуется)

```bash
cd website/backend
./mvnw spring-boot:run
```

### Вариант 2: С установленным Maven

```bash
cd website/backend
mvn spring-boot:run
```

### Вариант 3: Через IDE

Откройте проект в IntelliJ IDEA или Eclipse и запустите класс `FlowerShopApplication`.

Приложение запустится на порту **8080**.

## API Endpoints

### Товары

- `GET http://localhost:8080/api/products` - Получить все товары
- `GET http://localhost:8080/api/products/available` - Получить только доступные товары
- `GET http://localhost:8080/api/products/{id}` - Получить товар по ID

### Заказы

- `POST http://localhost:8080/api/orders` - Создать заказ
- `GET http://localhost:8080/api/orders` - Получить все заказы

## WebSocket

Подключение: `ws://localhost:8080/ws`
Топик для обновлений: `/topic/products`

## Логи

Логи сохраняются в файл `logs/flowershop.log` и содержат:
- Информацию о покупках (кто, что, сколько)
- Остатки товаров после операций
- Обновления состояния товаров

## H2 Console

Для просмотра базы данных:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:flowershopdb`
- Username: `sa`
- Password: (пусто)
