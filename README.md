🚀 Сравнение производительности: PostgreSQL против Redis
Это приложение загружает тестовую базу данных (world) через Liquibase, а затем выполняет один и тот же запрос дважды: сначала к PostgreSQL, затем к Redis (где данные предварительно кешируются). В консоль выводятся времена выполнения каждого запроса, что позволяет наглядно сравнить производительность.

🧰 Технологии
Java 21

Maven

Liquibase (миграции)

PostgreSQL 17

Redis 7

Docker / Docker Compose

📦 Что делает приложение?
Заселяет базу данных world (таблицы city, country, country_language) – миграции лежат в changelog/.

Выполняет запрос к PostgreSQL – замеряет время.

Записывает результат в Redis (кеш).

Выполняет запрос к Redis (получение тех же данных) – замеряет время.

Выводит в консоль два значения времени (в миллисекундах) для сравнения.

🐳 Запуск через Docker Compose (рекомендуемый способ)
1. Соберите JAR приложения локально
   bash
   mvn clean package
   Убедитесь, что в target/ появился файл final4-1.0-SNAPSHOT.jar (или аналогичный).

2. Запустите всё одной командой
   bash
   docker-compose up -d
   Эта команда поднимет:

PostgreSQL (порт 5432)

Redis (порт 6379)

Ваше приложение (одноразовый запуск миграций + тестовый запрос)

После выполнения контейнер с приложением остановится (политика restart: "no"), а БД и Redis продолжат работу.

3. Посмотрите результат
   bash
   docker-compose logs app
   В выводе вы увидите что-то вроде:

text
Redis:	35 ms
PostgreSQL:	40 ms
4. Остановка и очистка
   bash
   docker-compose down -v   # остановит все контейнеры и удалит тома с данными
   🖥️ Локальный запуск (без Docker)
   Требования
   Java 21

PostgreSQL 17 (запущен локально)

Redis 7 (запущен локально)

Настройка
Создайте базу данных world и пользователя postgres / postgres (или измените параметры в коде/переменных окружения).

Переменные окружения
Переменная	Значение по умолчанию	Описание
DB_URL	jdbc:postgresql://localhost:5432/world	URL подключения к PostgreSQL
DB_USER	postgres	Пользователь PostgreSQL
DB_PASSWORD	postgres	Пароль PostgreSQL
REDIS_HOST	localhost	Хост Redis
REDIS_PORT	6379	Порт Redis
Запуск
bash
java -jar target/final4-1.0-SNAPSHOT.jar
📁 Структура миграций (Liquibase)
changelog/01_create_tables.yml – создание таблиц city, country, country_language

changelog/02_insert_city.yml – данные о городах

changelog/03_insert_country.yml – данные о странах

changelog/04_insert_country_language.yml – языки

master.yml – главный файл, подключающий все изменения

Миграции применяются автоматически при первом запуске приложения.
