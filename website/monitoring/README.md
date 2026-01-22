# Мониторинг FlowerShop (Лаба 10)

## Описание

Настройка Grafana и Prometheus для мониторинга системы FlowerShop.

## Компоненты

1. **Spring Boot Actuator** - предоставляет метрики приложения
2. **Prometheus** - собирает и хранит метрики
3. **Grafana** - визуализирует метрики и настраивает алерты

## Структура

```
monitoring/
├── prometheus/
│   ├── prometheus.yml      # Конфигурация Prometheus
│   └── alerts.yml          # Правила алертов
├── grafana/
│   ├── datasources.yml     # Настройка источников данных
│   └── dashboard.json      # Дашборд Grafana
└── README.md
```

## Запуск

```bash
cd website
docker-compose up -d prometheus grafana
```

Или все вместе:

```bash
docker-compose up -d
```

## Доступ к сервисам

- **Grafana**: http://localhost:3000
  - Логин: `admin`
  - Пароль: `admin`

- **Prometheus**: http://localhost:9090
  - Метрики backend: http://localhost:9090/graph?g0.expr=up{job%3D"flowershop-backend"}

- **Spring Boot Actuator**: http://localhost:8080/actuator
  - Health: http://localhost:8080/actuator/health
  - Metrics: http://localhost:8080/actuator/metrics
  - Prometheus endpoint: http://localhost:8080/actuator/prometheus

## Настройка Grafana

### 1. Автоматическая загрузка дашборда

Дашборд загружается автоматически при старте Grafana из папки `monitoring/grafana/dashboards/`.

После перезапуска Grafana:
```bash
docker-compose restart grafana
```

Дашборд "FlowerShop Monitoring" появится автоматически.

### 2. Ручной импорт дашборда (если нужно)

1. Перейдите в **Dashboards** → **Import** (или "+" → "Import")
2. Нажмите **"Upload JSON file"**
3. Загрузите файл: `website/monitoring/grafana/dashboards/flowershop-dashboard.json`
4. Выберите источник данных **Prometheus**
5. Нажмите **"Import"**

### 3. Создание дашборда вручную

Если хотите создать свой дашборд:
1. Нажмите **"+ Create dashboard"**
2. Нажмите **"Add visualization"** или **"Add panel"**
3. Выберите источник данных **Prometheus**
4. Введите PromQL запрос, например: `up{job="flowershop-backend"}`
5. Настройте панель (тип графика, цвета, легенда)
6. Нажмите **"Apply"** и **"Save dashboard"**

### 2. Настройка алертов

Алерты уже настроены в Prometheus (`monitoring/prometheus/alerts.yml`):
- **ServiceDown** - сервис недоступен
- **HighErrorRate** - высокий уровень ошибок HTTP 5xx
- **SlowResponse** - медленный ответ (> 2 сек)
- **HighJvmMemoryUsage** - высокое использование памяти JVM
- **HighDatabaseConnections** - много активных подключений к БД

Для просмотра алертов в Grafana:
1. Перейдите в **Alerting** → **Alert rules**
2. Алерты будут отображаться автоматически из Prometheus

## Метрики

### Доступные метрики Spring Boot:

- `http_server_requests_seconds` - HTTP запросы и время ответа
- `jvm_memory_used_bytes` - Использование памяти JVM
- `jvm_gc_pause_seconds` - GC паузы
- `hikari_connections_active` - Активные подключения к БД
- `hikari_connections_idle` - Неактивные подключения к БД
- `hikari_connections_max` - Максимум подключений к БД

### Полезные запросы Prometheus:

```promql
# Количество HTTP запросов в секунду
rate(http_server_requests_seconds_count{job="flowershop-backend"}[5m])

# Время ответа (99-й перцентиль)
histogram_quantile(0.99, rate(http_server_requests_seconds_bucket{job="flowershop-backend"}[5m]))

# Использование памяти JVM
jvm_memory_used_bytes{job="flowershop-backend",area="heap"}

# Количество ошибок 5xx
rate(http_server_requests_seconds_count{job="flowershop-backend",status=~"5.."}[5m])

# Статус сервиса (1 = доступен, 0 = недоступен)
up{job="flowershop-backend"}
```

## Уведомления при ошибках

Алерты настроены на:
1. **Недоступность сервиса** - когда `up{job="flowershop-backend"}` = 0
2. **Высокий уровень ошибок** - когда ошибки HTTP 5xx > 0.1 запросов/сек
3. **Медленный ответ** - когда 99-й перцентиль > 2 секунд
4. **Высокое использование памяти** - когда heap > 90%
5. **Много подключений к БД** - когда активные подключения > 80%

## Проверка работы

1. Запустите все сервисы: `docker-compose up -d`
2. Откройте Grafana: http://localhost:3000
3. Проверьте метрики: http://localhost:8080/actuator/prometheus
4. Остановите backend: `docker-compose stop backend`
5. Через 1 минуту в Grafana появится алерт **ServiceDown**

## Troubleshooting

### Prometheus не собирает метрики

1. Проверьте, что backend запущен: `docker-compose ps`
2. Проверьте endpoint: `curl http://localhost:8080/actuator/prometheus`
3. Проверьте конфигурацию Prometheus: `docker-compose logs prometheus`

### Grafana не подключается к Prometheus

1. Проверьте, что Prometheus запущен: `docker-compose ps prometheus`
2. Проверьте datasource в Grafana: Configuration → Data Sources
3. Проверьте сеть Docker: `docker network ls`

### Алерты не срабатывают

1. Проверьте конфигурацию алертов в Prometheus: http://localhost:9090/alerts
2. Проверьте, что условия алертов выполнены
3. Проверьте логи Prometheus: `docker-compose logs prometheus`
