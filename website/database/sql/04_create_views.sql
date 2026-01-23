
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
    COUNT(oi.id) AS item_count,
    SUM(oi.quantity) AS total_items_quantity
FROM orders o
LEFT JOIN order_items oi ON oi.order_id = o.id
GROUP BY
    o.id, o.customer_name, o.customer_email, o.customer_phone,
    o.delivery_address, o.status, o.total_price, o.created_at;

COMMENT ON VIEW v_orders_full IS 'Детальная информация о заказах с количеством товаров';

CREATE OR REPLACE VIEW v_order_items_details AS
SELECT
    oi.id AS order_item_id,
    oi.order_id,
    oi.product_id,
    oi.product_name,
    oi.product_image,
    oi.quantity,
    oi.price AS item_price,
    (oi.quantity * oi.price) AS item_total,
    o.customer_name,
    o.status AS order_status,
    o.created_at AS order_date
FROM order_items oi
JOIN orders o ON o.id = oi.order_id;

COMMENT ON VIEW v_order_items_details IS 'Детальная информация об элементах заказов';

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
