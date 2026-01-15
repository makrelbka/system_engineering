# FlowerShop

Система онлайн-заказа цветов.

## Структура проекта

```
website/
├── frontend/    # Vue 3 + Vite + Tailwind
└── backend/     # Java Spring Boot
```

## Запуск через Docker

### Требования
- Docker Desktop установлен и запущен
- Docker Compose

### Команды

```bash
# Запустить все сервисы
docker-compose up --build

# Запустить в фоне
docker-compose up -d --build

# Остановить
docker-compose down

# Посмотреть логи
docker-compose logs -f

# Пересобрать и запустить
docker-compose up --build --force-recreate
```

### Доступ

- **Фронтенд**: http://localhost
- **Бэкенд API**: http://localhost:8080
- **Логи**: `website/backend/logs/flowershop.log`

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
