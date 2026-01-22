# FlowerShop

### Описание
- Онлайн-магазин цветов: Vue фронтенд, Spring Boot backend, PostgreSQL.
- Есть база, заказная логика, мониторинг Prometheus/Grafana.

### Функционал
- Каталог товаров, корзина, оформление заказов, callback-форма.
- REST API для товаров, заказов, заявок и админки.
- Скрипты и диаграмма базы в `website/database`.

### Запуск
1. `docker compose up -d --build` — поднимает backend, frontend, postgres, Prometheus, Grafana.
2. `docker compose down` — останавливает всю систему.

### Использование
- `http://localhost` — пользовательский интерфейс.
- `http://localhost:8080` — API.
- `http://localhost:9090` — Prometheus.
- `http://localhost:3000` — Grafana (admin/admin).
- `website/backend/logs/flowershop.log` — лог backend.
