# База данных PostgreSQL

## Описание

Модуль базы данных для системы FlowerShop. Содержит:
- **Entities** (JPA модели) - `model/`
- **Repositories** (Spring Data JPA) - `repository/`
- **DataInitializer** - автоматическая инициализация данных

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
