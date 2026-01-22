
DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_user WHERE usename = 'flowershop_user') THEN
    CREATE USER flowershop_user WITH PASSWORD 'flowershop_password';
  END IF;
END
$$;

GRANT ALL PRIVILEGES ON DATABASE flowershop_db TO flowershop_user;

GRANT ALL ON SCHEMA public TO flowershop_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO flowershop_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO flowershop_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO flowershop_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO flowershop_user;

COMMENT ON DATABASE flowershop_db IS 'База данных интернет-магазина цветов FlowerShop';
