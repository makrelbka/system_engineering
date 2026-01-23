DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users) THEN
        INSERT INTO users (email, password, name, phone, role)
        VALUES ('admin@flowershop.ru', 'admin', 'Администратор', '+7 (495) 123-45-67', 'ADMIN');
    END IF;

    IF NOT EXISTS (SELECT 1 FROM products) THEN
        INSERT INTO products (name, price, description, image) VALUES
        ('Розовые розы', 45.99,
         'Элегантный букет свежих розовых роз',
         'https://images.unsplash.com/photo-1716982360804-b0bfdb28103e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwaW5rJTIwcm9zZXMlMjBib3VxdWV0fGVufDF8fHx8MTc2ODM3MTA3MHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral'),

        ('Подсолнухи', 39.99,
         'Яркая и жизнерадостная композиция',
         'https://images.unsplash.com/photo-1629386255808-c3ceb405173c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzdW5mbG93ZXIlMjBib3VxdWV0fGVufDF8fHx8MTc2ODMwNjMwMHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral'),

        ('Тюльпаны микс', 42.99,
         'Разноцветные свежие тюльпаны',
         'https://images.unsplash.com/photo-1556291474-7ded8a3b2d38?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0dWxpcHMlMjBjb2xvcmZ1bHxlbnwxfHx8fDE3Njg0MDM0NDV8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral'),

        ('Лаванда', 34.99,
         'Ароматная лаванда для релаксации',
         'https://images.unsplash.com/photo-1541927634837-a7d5c4892527?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsYXZlbmRlciUyMGZsb3dlcnN8ZW58MXx8fHwxNzY4MzcyOTI4fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral');
    END IF;
END $$;
