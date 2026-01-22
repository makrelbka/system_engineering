# Мониторинг

### 1 Что это
- Prometheus собирает метрики с backend.
- Grafana рисует графики и показывает алерты.

### 2 Как запустить
1. `docker compose up -d --build`.
2. Ждём, пока поднимутся контейнеры.

### 3 Где смотреть
- Grafana: http://localhost:3000 (admin/admin).
- Prometheus: http://localhost:9090.
- Метрики backend: http://localhost:8080/actuator/prometheus.

### 4 Что можно смотреть
- В Grafana → Explore выбираешь `up{job="flowershop-backend"}` → видишь доступность.
- `rate(http_server_requests_seconds_count{job="flowershop-backend"}[5m])` — скважность запросов.
- `histogram_quantile(0.95, rate(http_server_requests_seconds_bucket{job="flowershop-backend"}[5m]))` — 95-й перцентиль по времени ответа.

### 5 Алерты
- `ServiceDown` — `up{job="flowershop-backend"} == 0`.
- `HighErrorRate` — `rate(...status="5..")` слишком высокий.
- `SlowResponse` — `histogram_quantile(...)` > 2s.
- Всё конфиг в `website/monitoring/prometheus/alerts.yml`.
