# Диаграммы BPMN и Sequence (Лаба 4)

## Описание

Диаграммы бизнес-процессов и взаимодействия компонентов системы FlowerShop.

## Структура

```
diagrams/
├── database-schema.dbml        # Схема БД (Лаба 3) - dbdiagram.io формат
├── bpmn-order-process.puml     # BPMN: Процесс оформления заказа (Лаба 4)
├── bpmn-order-processing.puml  # BPMN: Процесс обработки заказа (Лаба 4)
├── sequence-order-creation.puml    # Sequence: Оформление заказа (Лаба 4)
├── sequence-order-processing.puml  # Sequence: Обработка заказа (Лаба 4)
├── HOW_TO_EXPORT.md            # Инструкция по экспорту в PNG
├── EXPLANATION.md              # Объяснение: что делает каждая диаграмма
└── README.md
```

## Диаграммы

### Схема базы данных (Лаба 3)

#### 1. Схема БД в формате dbdiagram.io (`database-schema.dbml`)
Диаграмма всех таблиц БД FlowerShop в формате dbdiagram.io:
- **users** - пользователи системы (CLIENT, ADMIN)
- **categories** - категории товаров
- **products** - товары (букеты)
- **orders** - заказы
- **order_items** - элементы заказа
- **callback_requests** - заявки на звонок

**Просмотр:**
1. Откройте https://dbdiagram.io/
2. Нажмите "Import" → "Import from file"
3. Загрузите `database-schema.dbml`
4. Диаграмма отобразится автоматически

**Или онлайн:**
- Скопируйте содержимое `database-schema.dbml`
- Вставьте в редактор на https://dbdiagram.io/d


### BPMN и Sequence диаграммы (Лаба 4)

**Подробное объяснение:** см. `EXPLANATION.md` - там объяснено, что делает каждая диаграмма и зачем она нужна.

**BPMN диаграммы (бизнес-процессы):**
1. **`bpmn-order-process.puml`** - Процесс оформления заказа клиентом
   - Показывает ЧТО делает система (бизнес-логика)
   - Путь клиента от каталога до заказа
   
2. **`bpmn-order-processing.puml`** - Процесс обработки заказа администратором
   - Показывает жизненный цикл заказа
   - Статусы: NEW → IN_PROCESS → DELIVERING → COMPLETED

**Sequence диаграммы (техническая реализация):**
3. **`sequence-order-creation.puml`** - Оформление заказа клиентом
   - Показывает КАК это реализовано технически
   - Взаимодействие: Frontend → Backend → Services → Database
   - API вызовы, методы, SQL-запросы

4. **`sequence-order-processing.puml`** - Обработка заказа администратором
   - Показывает реализацию админ-панели
   - Авторизация, API-эндпоинты, запросы к БД

## Просмотр и экспорт диаграмм

### ⚠️ ВАЖНО: Для отчета нужны изображения (PNG/PDF), а не код!

**Как получить картинки:**
1. Откройте https://www.plantuml.com/plantuml/uml/
2. Скопируйте содержимое `.puml` файла
3. Вставьте в редактор
4. **Правой кнопкой на диаграмму** → **"Save image as..."** → сохраните PNG
5. Или нажмите **"Download PNG"** / **"Download SVG"**

**Подробная инструкция:** см. `HOW_TO_EXPORT.md`

### Просмотр онлайн:
1. Перейдите на https://plantuml.com/plantuml/uml/
2. Скопируйте содержимое файла `.puml`
3. Вставьте в редактор
4. Диаграмма отобразится автоматически

### VS Code:
1. Установите расширение "PlantUML"
2. Откройте файл `.puml`
3. Нажмите `Alt+D` для предпросмотра

### IntelliJ IDEA:
1. Откройте файл `.puml`
2. Диаграмма отобразится автоматически (встроенная поддержка)

### Экспорт в изображение:
```bash
# Установка PlantUML (требуется Java)
brew install plantuml

# Генерация PNG из всех .puml файлов
plantuml *.puml

# Или онлайн (проще):
# Откройте https://www.plantuml.com/plantuml/uml/
# Скопируйте содержимое .puml файла
# Сохраните PNG отдельно для каждого файла
```

## Для Лаб

### Лаба 3: Схема БД
- ✅ Схема БД в dbdiagram.io формате (`database-schema.dbml`)

### Лаба 4: BPMN и Sequence
- ✅ BPMN: Оформление заказа (`bpmn-order-process.puml`)
- ✅ BPMN: Обработка заказа (`bpmn-order-processing.puml`)
- ✅ Sequence: Оформление заказа (`sequence-order-creation.puml`)
- ✅ Sequence: Обработка заказа (`sequence-order-processing.puml`)

**См. `EXPLANATION.md`** для подробного объяснения каждой диаграммы

Все диаграммы соответствуют реализованной функциональности системы.
