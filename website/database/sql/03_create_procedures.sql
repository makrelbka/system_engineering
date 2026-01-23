
CREATE OR REPLACE FUNCTION update_order_status(
    p_order_id BIGINT,
    p_new_status VARCHAR(20)
) RETURNS BOOLEAN AS $$
DECLARE
    current_status VARCHAR(20);
BEGIN
    IF p_new_status NOT IN ('NEW', 'IN_PROCESS', 'DELIVERING', 'COMPLETED', 'CANCELLED') THEN
        RAISE EXCEPTION 'Некорректный статус заказа: %', p_new_status;
    END IF;

    SELECT status INTO current_status
    FROM orders
    WHERE id = p_order_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Заказ с id % не найден', p_order_id;
    END IF;

    IF current_status = p_new_status THEN
        RAISE EXCEPTION 'Заказ уже имеет статус %', p_new_status;
    END IF;

    UPDATE orders
    SET status = p_new_status
    WHERE id = p_order_id;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION update_order_status IS 'Обновляет статус заказа с проверкой корректности';
