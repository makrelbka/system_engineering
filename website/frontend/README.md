### Описание
- Vue 3 фронтенд для FlowerShop.
- Показывает каталог, корзину, оформление заказа и форму callback.
- Каталог не ограничен по остаткам, количество товаров можно увеличивать без лимита.
- В админке есть формы для добавления и удаления товаров.

### Технологии
- Vue 3, Vite, Tailwind.

### Запуск
1. Основной способ (как в проекте): из корня `docker compose up -d --build`.
2. Локальная разработка:
   - `cd website/frontend`
   - `npm ci`
   - `npm run dev`

### Использование
- Docker-версия: `http://localhost` (nginx).
- Dev-версия: `http://localhost:5173`.
- Фронт ходит в backend по `/api/*` (например, `/api/products`).

### Сборка
1. `cd website/frontend`
2. `npm ci`
3. `npm run build` (результат в `dist/`)
