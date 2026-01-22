# База данных PostgreSQL

## Описание

Модуль базы данных для системы FlowerShop. Содержит:
- **Entities** (JPA модели) - `model/`
- **Repositories** (Spring Data JPA) - `repository/`
- **DataInitializer** - автоматическая инициализация данных
- **SQL-скрипты** для ручного развёртывания - `sql/`
- **Диаграммы** (dbdiagram.io) - `diagrams/`

## Структура

```
database/
├── src/main/java/com/flowershop/
│   ├── model/          # JPA Entities
│   │   ├── User.java
│   │   ├── Category.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   └── OrderItem.java
│   ├── repository/     # Spring Data Repositories
│   │   ├── UserRepository.java
│   │   ├── CategoryRepository.java
│   │   ├── ProductRepository.java
│   │   └── OrderRepository.java
│   └── config/
│       └── DataInitializer.java  # Инициализация начальных данных
└── pom.xml
├── sql/                    # SQL-скрипты (ручной запуск)
│   ├── 01_create_database.sql
│   ├── 02_create_tables.sql
│   ├── 03_create_procedures.sql
│   ├── 04_create_views.sql
│   └── 05_insert_initial_data.sql
└── diagrams/               # ER-диаграмма (dbdiagram.io)
    └── database-schema.dbml
```

## Использование

Модуль подключается как зависимость в `backend/pom.xml`:

```xml
<dependency>
    <groupId>com.flowershop</groupId>
    <artifactId>flowershop-database</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Конфигурация БД

База данных настраивается через `docker-compose.yml`:

- **База данных**: `flowershop`
- **Пользователь**: `flowershop`
- **Пароль**: `flowershop`
- **Порт**: `5432`

## Подключение

```bash
# Из контейнера backend
psql -h postgres -U flowershop -d flowershop

# С хоста (если порт проброшен)
psql -h localhost -U flowershop -d flowershop
```

## Резервное копирование

```bash
# Создать бэкап
docker exec flowershop-postgres pg_dump -U flowershop flowershop > backup.sql

# Восстановить из бэкапа
docker exec -i flowershop-postgres psql -U flowershop flowershop < backup.sql
```

## Ручное создание БД (скрипты)

```bash
cd website
docker compose exec postgres psql -U flowershop -d flowershop -f sql/02_create_tables.sql
docker compose exec postgres psql -U flowershop -d flowershop -f sql/03_create_procedures.sql
docker compose exec postgres psql -U flowershop -d flowershop -f sql/04_create_views.sql
docker compose exec postgres psql -U flowershop -d flowershop -f sql/05_insert_initial_data.sql
```

## Диаграмма БД

`diagrams/database-schema.dbml` можно открыть в https://dbdiagram.io (Import from file) или через любой PlantUML/dbdiagram-плагин.
