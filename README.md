# CardApplication

CardApplication - микросервис по созданию и обработке банковских карт

___

## Стэк технологий:
- Java 17, Java Servlets, PostgreSQL, JdbcTemplate
- Сборка: Maven

___

## Функционал:
- Создание банковской карты
- Получение данных о карте
- Получение всех карт пользователя
- Получение списка карточных продуктов
- Закрытие существующей карты
- Получение выписки по карте
- Получение счета по номеру карты

___

## Примеры API запросов
1. Запрос: GET **/api/cards/get-info?id=uuid**

Ответ:
```json
{
   "message": "success",
   "data": {
      "userId": "123e4567-e89b-12d3-a456-426614174000",
      "cardProductId": "123e4567-e89b-12d3-a456-426614174001",
      "plasticName": "1950624594978740",
      "expDate": "2035-11-13",
      "contractName": "Договор №12345",
      "cardName": "Название карты #1",
      "openDocument": "1a7deadf-1288-4e7a-a6bb-e6b02fedf5ec", 
      "closeDocument": null,
      "closeFlag": false
    }
}
```

2. Запрос GET **/api/cards/card-products**
```json
{
   "message": "success",
   "data": [
      {
         "id": "de383476-26c9-4019-bb44-25ca65457cfa", 
         "cardName": "Карта #1", 
         "description": "Описание карты #1", 
         "cardImageLink": "https://example.com/images/gold-card.jpg"
      }, 
      {
         "id": "15795164-255a-449d-b064-db51e0d82cb5", 
         "cardName": "Карта #2", 
         "description": "Описание карты #2", 
         "cardImageLink": "https://example.com/images/classic-card.png"
      }
   ]
}
```

## Установка и запуск
1. Склонируйте репозиторий:

   ```bash
   git clone https://github.com/ReZorDos/CardsApplication.git
   cd CardsApplication
   ```
2. Создать базу данных и использовать файл `create-database.sql` для создания таблиц БД

3. Создать файл `app.properties` в _src/main/resources/_ и заполнить его по примеру `app.properties.origin`

4. Подключить Tomcat не ниже 10 версии

5. Запустить проект

___

## Contributing
1. Сделайте форк проекта
2. Создайте новую ветку `feature/YourFeature`
3. Внесите изменения и зафиксируйте их
4. Откройте pull request


