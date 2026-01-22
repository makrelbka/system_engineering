# Jenkins (локально в Docker)

Поднимает Jenkins в контейнере и даёт ему доступ к Docker на хосте через `/var/run/docker.sock`, чтобы пайплайны могли выполнять `docker`/`docker compose`.

## Требования
- Docker Desktop (или docker engine)
- Docker Compose v2 (`docker compose`)

## Запуск
Из корня репозитория:

```bash
cd jenkins
docker compose up -d --build
```

Jenkins UI: `http://localhost:8081`

## Первый вход
Начальный пароль администратора:

```bash
docker exec -it flowershop-jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Дальше в UI:
- Install suggested plugins
- Create first admin user

## Что дальше (минимум для CI/CD)
Для пайплайна обычно нужны плагины:
- Pipeline
- Git
- Credentials Binding

Если будешь собирать/деплоить контейнеры из Jenkins — полезны:
- Docker Pipeline (опционально; можно и просто `sh "docker ..."` без него)

## Автозапуск при пуше
### Вариант A (работает всегда): Poll SCM
В репозитории уже добавлен `Jenkinsfile` с `pollSCM('H/1 * * * *')` — Jenkins будет раз в минуту проверять изменения в репе и запускать деплой.

### Вариант B (как “настоящий push”): Webhook
Для webhook Jenkins должен быть доступен из Git-хостинга по URL (если Jenkins только на локальной машине — обычно нужен публичный адрес или туннель).

Общий принцип:
1) В Jenkins создай job типа **Pipeline** или **Multibranch Pipeline** и выбери “Pipeline script from SCM” (Git).
2) В настройках job включи триггер по webhook (зависит от провайдера).
3) В Git-хостинге добавь webhook на Jenkins.

Для GitHub чаще всего endpoint: `http://<host>:8081/github-webhook/` (нужен плагин GitHub).
Для GitLab обычно используют плагин GitLab и webhook на Jenkins (или Generic Webhook Trigger).

Если Jenkins недоступен снаружи — оставь Poll SCM.

## Деплой на эту же машину
`Jenkinsfile` делает:
`cd website && docker compose up -d --build --remove-orphans`
