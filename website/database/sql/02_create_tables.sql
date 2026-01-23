
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    image TEXT,
    CONSTRAINT chk_product_price CHECK (price >= 0)
);

COMMENT ON TABLE products IS 'Таблица товаров';
COMMENT ON COLUMN products.id IS 'Уникальный идентификатор товара';
COMMENT ON COLUMN products.name IS 'Название товара';
COMMENT ON COLUMN products.price IS 'Цена товара';
COMMENT ON COLUMN products.description IS 'Описание товара';
COMMENT ON COLUMN products.image IS 'Изображение товара (URL или base64)';

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(255) NOT NULL,
    delivery_address VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'NEW',
    total_price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_order_status CHECK (status IN ('NEW', 'IN_PROCESS', 'DELIVERING', 'COMPLETED', 'CANCELLED')),
    CONSTRAINT chk_order_total_price CHECK (total_price >= 0)
);

COMMENT ON TABLE orders IS 'Таблица заказов';
COMMENT ON COLUMN orders.id IS 'Уникальный идентификатор заказа';
COMMENT ON COLUMN orders.customer_name IS 'Имя клиента';
COMMENT ON COLUMN orders.customer_email IS 'Email клиента';
COMMENT ON COLUMN orders.customer_phone IS 'Телефон клиента';
COMMENT ON COLUMN orders.delivery_address IS 'Адрес доставки';
COMMENT ON COLUMN orders.status IS 'Статус заказа: NEW, IN_PROCESS, DELIVERING, COMPLETED, CANCELLED';
COMMENT ON COLUMN orders.total_price IS 'Общая стоимость заказа';
COMMENT ON COLUMN orders.created_at IS 'Дата и время создания заказа';

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_image VARCHAR(500),
    quantity INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id)
        REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT chk_order_item_quantity CHECK (quantity > 0),
    CONSTRAINT chk_order_item_price CHECK (price >= 0)
);

COMMENT ON TABLE order_items IS 'Таблица элементов заказа';
COMMENT ON COLUMN order_items.id IS 'Уникальный идентификатор элемента заказа';
COMMENT ON COLUMN order_items.order_id IS 'Идентификатор заказа';
COMMENT ON COLUMN order_items.product_id IS 'Идентификатор товара';
COMMENT ON COLUMN order_items.product_name IS 'Название товара на момент заказа';
COMMENT ON COLUMN order_items.product_image IS 'Изображение товара на момент заказа';
COMMENT ON COLUMN order_items.quantity IS 'Количество товара в заказе';
COMMENT ON COLUMN order_items.price IS 'Цена товара на момент заказа';

CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created_at ON orders(created_at);
CREATE INDEX idx_orders_customer_email ON orders(customer_email);

CREATE INDEX idx_products_name ON products(name);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);
