-- ============================================
-- Скрипт создания базы данных FlowerShop
-- Лабораторная работа 3
-- ============================================

-- Создание базы данных
-- Примечание: Для создания БД нужно подключиться к базе данных 'postgres'
-- psql -U postgres -c "CREATE DATABASE flowershop_db;"

-- Создание пользователя (если не существует)
DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_user WHERE usename = 'flowershop_user') THEN
    CREATE USER flowershop_user WITH PASSWORD 'flowershop_password';
  END IF;
END
$$;

-- Выдача прав на базу данных
GRANT ALL PRIVILEGES ON DATABASE flowershop_db TO flowershop_user;

-- Подключение к созданной базе данных
-- \c flowershop_db

-- Выдача прав на схему public
GRANT ALL ON SCHEMA public TO flowershop_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO flowershop_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO flowershop_user;

-- Установка прав по умолчанию для будущих объектов
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO flowershop_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO flowershop_user;

-- Комментарий к базе данных
COMMENT ON DATABASE flowershop_db IS 'База данных интернет-магазина цветов FlowerShop';
