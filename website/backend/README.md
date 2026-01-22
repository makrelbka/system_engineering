### Описание
- Spring Boot backend: товары, заказы, callback-заявки, WebSocket события.
- Реализует REST API, записывает логи и экспортирует метрики.

### Технологии
- Java 17, Spring Boot 3.2, Spring Data JPA, WebSocket (STOMP), Lombok, Actuator/Prometheus.

### Запуск
1. `cd website/backend`, `./mvnw spring-boot:run` (или `mvn spring-boot:run`).
2. Приложение работает на порту 8080.

### Конечные точки
- `GET /api/products`, `GET /api/products/{id}`.
- `POST /api/orders`, `GET /api/orders`.
- `POST /api/callback`.
- Админ: логин, получение заказов+заявок, смена статусов, CRUD админов.

### Логи и метрики
- Логи в `website/backend/logs/flowershop.log`.
- Метрики в `/actuator/prometheus`, WebSocket топик `/topic/products`.
