
DO $$
DECLARE
    category_id_var BIGINT;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users) THEN
        INSERT INTO users (email, password, name, phone, role)
        VALUES ('admin@flowershop.ru', 'admin', 'Администратор', '+7 (495) 123-45-67', 'ADMIN');
    END IF;

    IF NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Букеты') THEN
        INSERT INTO categories (name, description)
        VALUES ('Букеты', 'Свежие букеты цветов')
        RETURNING id INTO category_id_var;
    ELSE
        SELECT id INTO category_id_var FROM categories WHERE name = 'Букеты' LIMIT 1;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM products) THEN
        INSERT INTO products (name, price, description, image, stock, category_id) VALUES
        ('Розовые розы', 45.99,
         'Элегантный букет свежих розовых роз',
         'https://images.unsplash.com/photo-1716982360804-b0bfdb28103e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwaW5rJTIwcm9zZXMlMjBib3VxdWV0fGVufDF8fHx8MTc2ODM3MTA3MHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral',
         10, category_id_var),

        ('Подсолнухи', 39.99,
         'Яркая и жизнерадостная композиция',
         'https://images.unsplash.com/photo-1629386255808-c3ceb405173c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzdW5mbG93ZXIlMjBib3VxdWV0fGVufDF8fHx8MTc2ODMwNjMwMHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral',
         8, category_id_var),

        ('Тюльпаны микс', 42.99,
         'Разноцветные свежие тюльпаны',
         'https://images.unsplash.com/photo-1556291474-7ded8a3b2d38?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0dWxpcHMlMjBjb2xvcmZ1bHxlbnwxfHx8fDE3Njg0MDM0NDV8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral',
         15, category_id_var),

        ('Лаванда', 34.99,
         'Ароматная лаванда для релаксации',
         'https://images.unsplash.com/photo-1541927634837-a7d5c4892527?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsYXZlbmRlciUyMGZsb3dlcnN8ZW58MXx8fHwxNzY4MzcyOTI4fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral',
         12, category_id_var),

        ('Белые орхидеи', 59.99,
         'Экзотические орхидеи в горшке',
         'https://images.unsplash.com/photo-1710524784485-5c77ae822ecc?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3aGl0ZSUyMG9yY2hpZHxlbnwxfHx8fDE3Njg0MDM0NDZ8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral',
         5, category_id_var),

        ('Букет из лилий', 48.99,
         'Роскошная композиция из свежих лилий',
         'https://images.unsplash.com/photo-1712258090342-f31b387221a3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsaWx5JTIwZmxvd2VycyUyMGJvdXF1ZXR8ZW58MXx8fHwxNzY4NDAzNDQ2fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral',
         7, category_id_var),

        ('Розовые пионы', 54.99,
         'Роскошный букет розовых пионов',
         'https://images.unsplash.com/photo-1588457776180-4206b4909301?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZW9ueSUyMGZsb3dlcnMlMjBwaW5rfGVufDF8fHx8MTc2ODMzNzgzN3ww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral',
         9, category_id_var),

        ('Белые ромашки', 32.99,
         'Простые и элегантные белые ромашки',
         'https://images.unsplash.com/photo-1618929990290-e32fa75925be?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxkYWlzeSUyMGZsb3dlcnMlMjB3aGl0ZXxlbnwxfHx8fDE3Njg0MDM0NDd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral',
         20, category_id_var);
    END IF;
END $$;
