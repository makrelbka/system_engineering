
CREATE OR REPLACE VIEW v_products_full AS
SELECT
    p.id,
    p.name AS product_name,
    p.price,
    p.description,
    p.image,
    p.stock,
    CASE
        WHEN p.stock > 0 THEN TRUE
        ELSE FALSE
    END AS available,
    c.id AS category_id,
    c.name AS category_name,
    c.description AS category_description
FROM products p
LEFT JOIN categories c ON c.id = p.category_id;

COMMENT ON VIEW v_products_full IS 'Полная информация о товарах с категориями';

CREATE OR REPLACE VIEW v_orders_full AS
SELECT
    o.id AS order_id,
    o.customer_name,
    o.customer_email,
    o.customer_phone,
    o.delivery_address,
    o.status,
    o.total_price,
    o.created_at,
    u.id AS user_id,
    u.name AS user_name,
    u.email AS user_email,
    COUNT(oi.id) AS item_count,
    SUM(oi.quantity) AS total_items_quantity
FROM orders o
LEFT JOIN users u ON u.id = o.user_id
LEFT JOIN order_items oi ON oi.order_id = o.id
GROUP BY
    o.id, o.customer_name, o.customer_email, o.customer_phone,
    o.delivery_address, o.status, o.total_price, o.created_at,
    u.id, u.name, u.email;

COMMENT ON VIEW v_orders_full IS 'Детальная информация о заказах с данными пользователя и количеством товаров';

CREATE OR REPLACE VIEW v_order_items_details AS
SELECT
    oi.id AS order_item_id,
    oi.order_id,
    oi.product_id,
    oi.quantity,
    oi.price AS item_price,
    (oi.quantity * oi.price) AS item_total,
    p.name AS product_name,
    p.image AS product_image,
    c.name AS category_name,
    o.customer_name,
    o.status AS order_status,
    o.created_at AS order_date
FROM order_items oi
JOIN products p ON p.id = oi.product_id
LEFT JOIN categories c ON c.id = p.category_id
JOIN orders o ON o.id = oi.order_id;

COMMENT ON VIEW v_order_items_details IS 'Детальная информация об элементах заказов с данными товаров';

CREATE OR REPLACE VIEW v_orders_daily_stats AS
SELECT
    DATE(o.created_at) AS order_date,
    COUNT(o.id) AS orders_count,
    COUNT(CASE WHEN o.status = 'NEW' THEN 1 END) AS new_orders,
    COUNT(CASE WHEN o.status = 'IN_PROCESS' THEN 1 END) AS in_process_orders,
    COUNT(CASE WHEN o.status = 'DELIVERING' THEN 1 END) AS delivering_orders,
    COUNT(CASE WHEN o.status = 'COMPLETED' THEN 1 END) AS completed_orders,
    COUNT(CASE WHEN o.status = 'CANCELLED' THEN 1 END) AS cancelled_orders,
    COALESCE(SUM(o.total_price), 0) AS total_revenue,
    COALESCE(SUM(CASE WHEN o.status = 'COMPLETED' THEN o.total_price ELSE 0 END), 0) AS completed_revenue,
    COALESCE(AVG(o.total_price), 0) AS avg_order_price
FROM orders o
GROUP BY DATE(o.created_at)
ORDER BY order_date DESC;

COMMENT ON VIEW v_orders_daily_stats IS 'Статистика по заказам, сгруппированная по дням';

CREATE OR REPLACE VIEW v_products_low_stock AS
SELECT
    p.id,
    p.name AS product_name,
    p.stock,
    p.price,
    c.name AS category_name,
    CASE
        WHEN p.stock = 0 THEN 'Нет в наличии'
        WHEN p.stock < 5 THEN 'Осталось мало'
        WHEN p.stock < 10 THEN 'Средний остаток'
        ELSE 'Достаточно'
    END AS stock_status
FROM products p
LEFT JOIN categories c ON c.id = p.category_id
WHERE p.stock < 10
ORDER BY p.stock ASC, p.name;

COMMENT ON VIEW v_products_low_stock IS 'Товары с низким остатком на складе (меньше 10 единиц)';

CREATE OR REPLACE VIEW v_admin_users AS
SELECT
    id,
    email,
    name,
    phone,
    role
FROM users
WHERE role = 'ADMIN';

COMMENT ON VIEW v_admin_users IS 'Список всех администраторов системы';

CREATE OR REPLACE VIEW v_clients_stats AS
SELECT
    u.id AS user_id,
    u.email,
    u.name,
    u.phone,
    COUNT(o.id) AS total_orders,
    COUNT(CASE WHEN o.status = 'COMPLETED' THEN 1 END) AS completed_orders,
    COALESCE(SUM(CASE WHEN o.status = 'COMPLETED' THEN o.total_price ELSE 0 END), 0) AS total_spent,
    COALESCE(MAX(o.created_at), NULL) AS last_order_date
FROM users u
LEFT JOIN orders o ON o.user_id = u.id
WHERE u.role = 'CLIENT'
GROUP BY u.id, u.email, u.name, u.phone;

COMMENT ON VIEW v_clients_stats IS 'Статистика по клиентам с информацией о заказах';

CREATE OR REPLACE VIEW v_pending_callbacks AS
SELECT
    id,
    name,
    phone,
    created_at,
    EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - created_at)) / 3600 AS hours_waiting
FROM callback_requests
WHERE completed = FALSE
ORDER BY created_at ASC;

COMMENT ON VIEW v_pending_callbacks IS 'Необработанные заявки на звонок с временем ожидания';
