-- ============================================
-- Скрипт создания таблиц базы данных FlowerShop
-- Лабораторная работа 3
-- ============================================

-- Удаление таблиц (если существуют) в правильном порядке
-- для соблюдения внешних ключей
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS callback_requests CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============================================
-- Создание таблицы users (Пользователи)
-- ============================================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'CLIENT',
    CONSTRAINT chk_user_role CHECK (role IN ('CLIENT', 'ADMIN'))
);

COMMENT ON TABLE users IS 'Таблица пользователей системы';
COMMENT ON COLUMN users.id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN users.email IS 'Email пользователя (уникальный)';
COMMENT ON COLUMN users.password IS 'Пароль пользователя';
COMMENT ON COLUMN users.name IS 'Имя пользователя';
COMMENT ON COLUMN users.phone IS 'Телефон пользователя';
COMMENT ON COLUMN users.role IS 'Роль пользователя: CLIENT или ADMIN';

-- ============================================
-- Создание таблицы categories (Категории товаров)
-- ============================================
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

COMMENT ON TABLE categories IS 'Таблица категорий товаров';
COMMENT ON COLUMN categories.id IS 'Уникальный идентификатор категории';
COMMENT ON COLUMN categories.name IS 'Название категории (уникальное)';
COMMENT ON COLUMN categories.description IS 'Описание категории';

-- ============================================
-- Создание таблицы products (Товары)
-- ============================================
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    image VARCHAR(500),
    stock INTEGER NOT NULL DEFAULT 0,
    category_id BIGINT,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) 
        REFERENCES categories(id) ON DELETE SET NULL,
    CONSTRAINT chk_product_price CHECK (price >= 0),
    CONSTRAINT chk_product_stock CHECK (stock >= 0)
);

COMMENT ON TABLE products IS 'Таблица товаров (букетов)';
COMMENT ON COLUMN products.id IS 'Уникальный идентификатор товара';
COMMENT ON COLUMN products.name IS 'Название товара';
COMMENT ON COLUMN products.price IS 'Цена товара';
COMMENT ON COLUMN products.description IS 'Описание товара';
COMMENT ON COLUMN products.image IS 'URL изображения товара';
COMMENT ON COLUMN products.stock IS 'Количество товара в наличии';
COMMENT ON COLUMN products.category_id IS 'Идентификатор категории товара';

-- ============================================
-- Создание таблицы orders (Заказы)
-- ============================================
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(255) NOT NULL,
    delivery_address VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'NEW',
    total_price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT chk_order_status CHECK (status IN ('NEW', 'IN_PROCESS', 'DELIVERING', 'COMPLETED', 'CANCELLED')),
    CONSTRAINT chk_order_total_price CHECK (total_price >= 0)
);

COMMENT ON TABLE orders IS 'Таблица заказов';
COMMENT ON COLUMN orders.id IS 'Уникальный идентификатор заказа';
COMMENT ON COLUMN orders.user_id IS 'Идентификатор пользователя (может быть NULL для гостевых заказов)';
COMMENT ON COLUMN orders.customer_name IS 'Имя клиента';
COMMENT ON COLUMN orders.customer_email IS 'Email клиента';
COMMENT ON COLUMN orders.customer_phone IS 'Телефон клиента';
COMMENT ON COLUMN orders.delivery_address IS 'Адрес доставки';
COMMENT ON COLUMN orders.status IS 'Статус заказа: NEW, IN_PROCESS, DELIVERING, COMPLETED, CANCELLED';
COMMENT ON COLUMN orders.total_price IS 'Общая стоимость заказа';
COMMENT ON COLUMN orders.created_at IS 'Дата и время создания заказа';

-- ============================================
-- Создание таблицы order_items (Элементы заказа)
-- ============================================
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) 
        REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE RESTRICT,
    CONSTRAINT chk_order_item_quantity CHECK (quantity > 0),
    CONSTRAINT chk_order_item_price CHECK (price >= 0)
);

COMMENT ON TABLE order_items IS 'Таблица элементов заказа';
COMMENT ON COLUMN order_items.id IS 'Уникальный идентификатор элемента заказа';
COMMENT ON COLUMN order_items.order_id IS 'Идентификатор заказа';
COMMENT ON COLUMN order_items.product_id IS 'Идентификатор товара';
COMMENT ON COLUMN order_items.quantity IS 'Количество товара в заказе';
COMMENT ON COLUMN order_items.price IS 'Цена товара на момент заказа';

-- ============================================
-- Создание таблицы callback_requests (Заявки на звонок)
-- ============================================
CREATE TABLE callback_requests (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed BOOLEAN NOT NULL DEFAULT FALSE
);

COMMENT ON TABLE callback_requests IS 'Таблица заявок на обратный звонок';
COMMENT ON COLUMN callback_requests.id IS 'Уникальный идентификатор заявки';
COMMENT ON COLUMN callback_requests.name IS 'Имя клиента';
COMMENT ON COLUMN callback_requests.phone IS 'Телефон клиента';
COMMENT ON COLUMN callback_requests.created_at IS 'Дата и время создания заявки';
COMMENT ON COLUMN callback_requests.completed IS 'Флаг обработки заявки';

-- ============================================
-- Создание индексов для улучшения производительности
-- ============================================

-- Индексы для таблицы users
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);

-- Индексы для таблицы products
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_stock ON products(stock);

-- Индексы для таблицы orders
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created_at ON orders(created_at);
CREATE INDEX idx_orders_customer_email ON orders(customer_email);

-- Индексы для таблицы order_items
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);

-- Индексы для таблицы callback_requests
CREATE INDEX idx_callback_requests_completed ON callback_requests(completed);
CREATE INDEX idx_callback_requests_created_at ON callback_requests(created_at);
