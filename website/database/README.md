### Описание
- PostgreSQL база для FlowerShop.
- SQL-скрипты и схема лежат в `website/database/`.

### Содержимое
- `website/database/sql/` — скрипты создания БД/таблиц/представлений/процедур и стартовые данные.
- `website/database/diagrams/database-schema.dbml` — схема БД (DBML).

### Запуск
1. Из корня проекта: `docker compose up -d --build` (PostgreSQL поднимется на `5432`).

### Подключение
1. `psql -h localhost -U flowershop -d flowershop`
2. Данные для входа (как в `website/docker-compose.yml`):
   - user: `flowershop`
   - password: `flowershop`
   - db: `flowershop`

### Бэкап
- Дамп: `docker exec flowershop-postgres pg_dump -U flowershop flowershop > backup.sql`
- Восстановление: `docker exec -i flowershop-postgres psql -U flowershop flowershop < backup.sql`
