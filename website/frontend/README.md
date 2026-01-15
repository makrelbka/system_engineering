# FlowerShop - Vue приложение

Веб-приложение для онлайн-заказа цветов, переписанное на Vue 3.

## Технологии

- Vue 3 (Composition API)
- Vite
- Tailwind CSS
- Lucide Icons

## Установка

```bash
npm install
```

## Запуск в режиме разработки

```bash
npm run dev
```

Приложение будет доступно по адресу `http://localhost:5173`

## Сборка для продакшена

```bash
npm run build
```

Собранные файлы будут в папке `dist`

## Структура проекта

```
frontend/
├── src/
│   ├── components/        # Компоненты приложения
│   │   ├── ui/           # UI компоненты (Button, Card, Input и т.д.)
│   │   ├── Header.vue    # Шапка с корзиной
│   │   ├── HeroTitle.vue # Заголовок главной страницы
│   │   ├── CallbackForm.vue # Форма обратной связи
│   │   ├── ProductGrid.vue  # Сетка товаров
│   │   ├── ProductCard.vue  # Карточка товара
│   │   ├── Footer.vue       # Подвал
│   │   └── Toaster.vue      # Компонент уведомлений
│   ├── composables/      # Композаблы (useToast)
│   ├── App.vue           # Главный компонент
│   ├── main.js           # Точка входа
│   └── style.css         # Глобальные стили
├── index.html
├── package.json
└── vite.config.js
```

## Функциональность

- Просмотр каталога товаров
- Добавление товаров в корзину
- Управление количеством товаров в корзине
- Форма обратной связи для заказа кастомного букета
- Адаптивный дизайн
