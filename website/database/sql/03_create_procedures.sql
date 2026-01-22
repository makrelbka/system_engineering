-- ============================================
-- Хранимые процедуры базы данных FlowerShop
-- Лабораторная работа 3
-- ============================================

-- ============================================
-- Процедура: Уменьшение количества товара на складе
-- ============================================
CREATE OR REPLACE FUNCTION decrease_product_stock(
    p_product_id BIGINT,
    p_quantity INTEGER
) RETURNS BOOLEAN AS $$
DECLARE
    current_stock INTEGER;
BEGIN
    -- Получаем текущее количество товара
    SELECT stock INTO current_stock
    FROM products
    WHERE id = p_product_id;
    
    -- Проверяем существование товара
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Товар с id % не найден', p_product_id;
    END IF;
    
    -- Проверяем достаточность товара
    IF current_stock < p_quantity THEN
        RAISE EXCEPTION 'Недостаточно товара на складе. Доступно: %, требуется: %', 
            current_stock, p_quantity;
    END IF;
    
    -- Уменьшаем количество
    UPDATE products
    SET stock = stock - p_quantity
    WHERE id = p_product_id;
    
    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION decrease_product_stock IS 'Уменьшает количество товара на складе. Возвращает TRUE при успехе.';

-- ============================================
-- Процедура: Получение статистики продаж по категориям
-- ============================================
CREATE OR REPLACE FUNCTION get_sales_by_category(
    p_start_date TIMESTAMP DEFAULT NULL,
    p_end_date TIMESTAMP DEFAULT NULL
) RETURNS TABLE (
    category_id BIGINT,
    category_name VARCHAR(255),
    total_sales DECIMAL(10, 2),
    total_quantity BIGINT,
    order_count BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        c.id AS category_id,
        c.name AS category_name,
        COALESCE(SUM(oi.price * oi.quantity), 0) AS total_sales,
        COALESCE(SUM(oi.quantity), 0) AS total_quantity,
        COUNT(DISTINCT o.id) AS order_count
    FROM categories c
    LEFT JOIN products p ON p.category_id = c.id
    LEFT JOIN order_items oi ON oi.product_id = p.id
    LEFT JOIN orders o ON o.id = oi.order_id
        AND o.status IN ('IN_PROCESS', 'DELIVERING', 'COMPLETED')
        AND (p_start_date IS NULL OR o.created_at >= p_start_date)
        AND (p_end_date IS NULL OR o.created_at <= p_end_date)
    GROUP BY c.id, c.name
    ORDER BY total_sales DESC;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_sales_by_category IS 'Возвращает статистику продаж по категориям за указанный период';

-- ============================================
-- Процедура: Получение популярных товаров
-- ============================================
CREATE OR REPLACE FUNCTION get_popular_products(
    p_limit INTEGER DEFAULT 10,
    p_start_date TIMESTAMP DEFAULT NULL,
    p_end_date TIMESTAMP DEFAULT NULL
) RETURNS TABLE (
    product_id BIGINT,
    product_name VARCHAR(255),
    category_name VARCHAR(255),
    total_sold BIGINT,
    total_revenue DECIMAL(10, 2),
    order_count BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        p.id AS product_id,
        p.name AS product_name,
        c.name AS category_name,
        COALESCE(SUM(oi.quantity), 0) AS total_sold,
        COALESCE(SUM(oi.price * oi.quantity), 0) AS total_revenue,
        COUNT(DISTINCT o.id) AS order_count
    FROM products p
    LEFT JOIN categories c ON c.id = p.category_id
    LEFT JOIN order_items oi ON oi.product_id = p.id
    LEFT JOIN orders o ON o.id = oi.order_id
        AND o.status IN ('IN_PROCESS', 'DELIVERING', 'COMPLETED')
        AND (p_start_date IS NULL OR o.created_at >= p_start_date)
        AND (p_end_date IS NULL OR o.created_at <= p_end_date)
    GROUP BY p.id, p.name, c.name
    ORDER BY total_sold DESC
    LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_popular_products IS 'Возвращает список популярных товаров по количеству продаж';

-- ============================================
-- Процедура: Обновление статуса заказа с проверкой
-- ============================================
CREATE OR REPLACE FUNCTION update_order_status(
    p_order_id BIGINT,
    p_new_status VARCHAR(20)
) RETURNS BOOLEAN AS $$
DECLARE
    current_status VARCHAR(20);
BEGIN
    -- Проверяем корректность нового статуса
    IF p_new_status NOT IN ('NEW', 'IN_PROCESS', 'DELIVERING', 'COMPLETED', 'CANCELLED') THEN
        RAISE EXCEPTION 'Некорректный статус заказа: %', p_new_status;
    END IF;
    
    -- Получаем текущий статус
    SELECT status INTO current_status
    FROM orders
    WHERE id = p_order_id;
    
    -- Проверяем существование заказа
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Заказ с id % не найден', p_order_id;
    END IF;
    
    -- Проверяем, что статус изменяется
    IF current_status = p_new_status THEN
        RAISE EXCEPTION 'Заказ уже имеет статус %', p_new_status;
    END IF;
    
    -- Обновляем статус
    UPDATE orders
    SET status = p_new_status
    WHERE id = p_order_id;
    
    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION update_order_status IS 'Обновляет статус заказа с проверкой корректности';

-- ============================================
-- Процедура: Получение необработанных заявок на звонок
-- ============================================
CREATE OR REPLACE FUNCTION get_pending_callbacks(
    p_limit INTEGER DEFAULT 100
) RETURNS TABLE (
    id BIGINT,
    name VARCHAR(255),
    phone VARCHAR(255),
    created_at TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        cr.id,
        cr.name,
        cr.phone,
        cr.created_at
    FROM callback_requests cr
    WHERE cr.completed = FALSE
    ORDER BY cr.created_at ASC
    LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_pending_callbacks IS 'Возвращает список необработанных заявок на звонок';
