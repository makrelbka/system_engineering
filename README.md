# FlowerShop

Система онлайн-заказа цветов.

## Структура проекта

```
website/
├── frontend/     # Vue 3 + Vite + Tailwind
├── backend/      # Java Spring Boot
├── database/     # JPA модели + SQL-скрипты + ER-диаграмма
└── monitoring/   # Prometheus + Grafana (дашборд, алерты)
```

## Запуск через Docker

### Требования
- Docker Desktop установлен и запущен
- Docker Compose

### Команды

```bash
# Запустить все сервисы (включая мониторинг)
docker compose up --build

# Запустить в фоне
docker compose up -d --build

# Остановить
docker compose down

# Посмотреть логи
docker compose logs -f

# Пересобрать и запустить
docker compose up --build --force-recreate
```

### Доступ

- **Фронтенд**: http://localhost
- **Бэкенд API**: http://localhost:8080
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (admin/admin)
- **Логи**: `website/backend/logs/flowershop.log`

### База данных
- SQL-скрипты: `website/database/sql/`
- ER-диаграмма: `website/database/diagrams/database-schema.dbml` (открывается через dbdiagram.io)

### Мониторинг (Prometheus + Grafana)
- Конфиги: `website/monitoring/`
- Алёрты: `website/monitoring/prometheus/alerts.yml`
- Дашборд: `website/monitoring/grafana/dashboards/flowershop-dashboard.json`

## Запуск без Docker

### Бэкенд
```bash
cd website/backend
mvn spring-boot:run
```

### Фронтенд
```bash
cd website/frontend
npm install
npm run dev
```

Или через Makefile:
```bash
make all
```
