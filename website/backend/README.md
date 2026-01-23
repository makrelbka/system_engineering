### Описание
- Spring Boot backend: каталог, заказы, callback-заявки, админка.
- Каталог товаров хранится в базе данных, админка управляет товарами и заказами.
- Реализует REST API, записывает логи и экспортирует метрики.

### Технологии
- Java 17, Spring Boot 3.2, Spring Data JPA, Lombok, Actuator/Prometheus.

### Запуск
1. `cd website/backend`, `./mvnw spring-boot:run` (или `mvn spring-boot:run`).
2. Приложение работает на порту 8080.

### Конечные точки
- `GET /api/products`, `GET /api/products/{id}` — каталог (из БД).
- `POST /api/orders`, `GET /api/orders` — заказы (в базе).
- `POST /api/callback` — заявка на звонок (в БД).
- Админ: логин, заказы, заявки, управление товарами (в БД).

### Логи и метрики
- Логи в `website/logs/flowershop.log` (заказы клиентов, заявки на звонок, действия админа).
- Метрики в `/actuator/prometheus`.
