# FlowerShop

### Описание
- Онлайн-магазин цветов: Vue фронтенд, Spring Boot backend, PostgreSQL.
- Каталог товаров хранится в базе данных, админка управляет товарами и заказами.
- Количество цветов не ограничено (остатки не ведутся).

### Функционал
- Каталог товаров, корзина, оформление заказов, callback-форма.
- Админка: просмотр заказов, статусы, заявки на звонок, добавление/удаление товаров.
- REST API для каталога, заказов, заявок и админки.
- Скрипты и схема базы в `website/database`.

### Запуск
1. `docker compose up -d --build` — поднимает backend, frontend, postgres, Prometheus, Grafana.
2. `docker compose down` — останавливает всю систему.

### Использование
- `http://localhost` — пользовательский интерфейс.
- Админка: `http://localhost/#/admin` (логин `admin@flowershop.ru`, пароль `admin`).
- `http://localhost:8080` — API.
- `http://localhost:9090` — Prometheus.
- `http://localhost:3000` — Grafana (admin/admin).
- `website/logs/flowershop.log` — лог backend (заказы, заявки, действия админа).
