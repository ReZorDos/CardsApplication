# CardsService

CardService - микросервис по созданию и обработке банковских карт
___

## Стэк технологий:
- Java 17, Spring, Hibernate, PostgreSQL, JdbcTemplate
- Сборка: Maven
- Контейнеризация: Docker

___

## Функционал:
Микросервис отвечает только за карты:
- создание карты определённого типа;
- получение карточных продуктов
- получение документа по `id`,  `userId`, `pan`, `contractId`;
- получение выписки по карте за определенный период

___

## Модель данных

В сервисе используется 2 сущности — `Card`, `CardProduct`.

Поля сущности `Card`:
- `UUID id`
- `UUID user_id`
- `UUID card_product_id`
- `String plastic_name`
- `String exp_date`
- `Integer cvv`
- `String contract_name`
- `String  pan`
- `String open_document`
- `String close_document`
- `boolean close_flag`

Поля сущности `CardProduct`:
- `UUID id`
- `String card_name`
- `String description`
- `String card_image_link`

---

## REST API

### 1. Создание карты

**POST** `/api/v2/cards`

Пример запроса:
```json
{
  "cardProductId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
}
```

### 2. Получить выписку по карте за определенный период

`GET /api/v2/cards/statement/{cardId}?from={someDate}&to={someDate}`

Пример:

`GET /api/v2/cards/statement/CNDD98D151E9GUSEVBANK?from=2026-03-23T15:28:40.033819Z&to=2026-03-27T12:03:49.102663Z`

### 3. Получить все доступные карточные продукты

`GET /api/v2/cards/products`

### 4. Получить карточный продукт по id

`GET /api/v2/cards/products/{id}`

Пример:

`GET /api/v2/cards/products/6534e2e1-ff34-4ae1-977c-98cbc33b25c1

### 5. Получить все карты пользователя по его id

`GET /api/v2/cards/by-user/{userId}`

Пример:

`GET /api/v2/cards/by-user/3fe12a81-efc3-46ab-b835-a468f9a9a704`


### 6. Получить информацию по конкретной карте по ее номеру (pan)

`GET /api/v2/cards/by-pan/{pan}`

Пример:

`GET /api/v2/cards/by-pan/6831123550280456`


### 7. Получить информацию по конкретной карте по ее id

`GET /api/v2/cards/by-id/{id}`

Пример:

`GET /api/v2/cards/products/3fa85f64-5717-4562-b3fc-2c963f66afa6`


### 8. Получить информацию по конкретной карте по номеру счета

`GET /api/v2/cards/by-contract/{contract}`

Пример:

`GET /api/v2/cards/by-contract/CN1234567890RU116`


### 9. Закрыть карту

**POST** `/api/v2/cards/{cardId}`

Пример:

`POST /api/v2/cards/91353d28-c4e0-46ac-89f6-5641ec41b0ef`


## Что должно работать после запуска

После запуска сервиса должны быть доступны следующие адреса:

### REST API

- `POST /api/v2/cards` - создать карту
- `GET /api/v2/cards/statement/{cardId}?from={someDate}&to={someDate}` - получить выписку по карте за определенный период
- `GET /api/v2/cards/products` - получить все доступные карточные продукты
- `GET /api/v2/cards/products/{id}` - получить карточный продукт по id
- `GET /api/v2/cards/by-user/{userId}` - получить все карты пользователя по его id
- `GET /api/v2/cards/by-pan/{pan}` - получить информацию по конкретной карте по ее номеру (pan)
- `GET /api/v2/cards/by-id/{id}` - Получить информацию по конкретной карте по ее id
- `GET /api/v2/cards/by-contract/{contract}` - получить информацию по конкретной карте по номеру счета
- `POST /api/v2/cards/{cardId}` - закрыть карту


- внутри контейнера сервис слушает порт 8080
- снаружи можно пробросить, например, на 8085


## Настройки подключения

Подключение к БД задаётся в app.properties:

```
db.url=${DB_URL:jdbc:postgresql://localhost:5432/card-application-oris}
db.user=${DB_USER:card-application}
db.password=${DB_PASSWORD:123456}
```
## Важно

Перед запуском необходимо создать базу данных и выполнить SQL-скрипт `create-database.sql`.
Имя БД: card-application-oris

```sql
create table card_product (  
    id uuid primary key default gen_random_uuid(),  
    card_name varchar(255),  
    description varchar(255),  
    card_image_link varchar(255)  
);  
  
create table card (  
    id uuid primary key default gen_random_uuid(),  
    user_id uuid not null,  
    card_product_id uuid not null,  
    plastic_name varchar(255),  
    exp_date varchar(255),  
    cvv int,  
    contract_name varchar(255),  
    pan varchar(255) unique,  
    open_document varchar(255),  
    close_document varchar(255),  
    close_flag boolean default false  
);  
  
INSERT INTO card_product (card_name, description, card_image_link) VALUES  
    (  
       'Master Hasler',  
       'Карта с повышенным кэшбэком на путешествия и доступом в бизнес-залы.',  
       'https://i.ibb.co/Pbg6Vy0/photo.jpg'  
    ),  
    (  
       'Java Back Card',  
       'Для самых тестостероновых программистов',  
       'https://i.ibb.co/Ld2gFqsQ/photo.jpg'  
    ),  
    (  
       'Student Eco Line',  
       'Бесплатное обслуживание и специальные бонусы на посещение преподского лифта',  
       'https://i.ibb.co/xqQ920Vx/photo.jpg'  
    );
```

Также нужно использовать .env файл для подтягивания данных и url других api:
```
POSTGRES_DB=card-application-oris  
POSTGRES_USER=card-application  
POSTGRES_PASSWORD=123456  
  
DB_URL=jdbc:postgresql://postgres:5432/card-application-oris  
DB_USER=card-application  
DB_PASSWORD=123456  
  
API_DOCUMENTS=http://185.221.160.131/api/documents  
API_USERS=http://104.128.137.40/api/v1/users/  
API_TRANSFERS=http://26.122.215.84:8083/api/v1/transfers/  
  
APP_PORT=8085  
CONTAINER_PORT=8080
```

## Установка и запуск
1. Склонируйте репозиторий:

   ```bash
   git clone https://github.com/ReZorDos/CardsApplication.git
   cd CardsApplication
   ```
2. Создать базу данных и использовать файл `create-database.sql` для создания таблиц БД

4. Подключить Tomcat не ниже 10 версии

5. Запустить проект

___

# Имя Docker image: card-service:1.0

## Запуск Docker

```
docker load -i card-service.tar
docker run -d --name card-service \
--env-file .env \
-p 8085:8080 \
card-service:1.0
```

---

## Contributing
1. Сделайте форк проекта
2. Создайте новую ветку `feature/YourFeature`
3. Внесите изменения и зафиксируйте их
4. Откройте pull request


