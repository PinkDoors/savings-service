# Savings Service

У меня с коллегами по ВШЭ есть пет-проект, который позволяет создавать визуальные новеллы с использованием 
визуального программирования. Вся логика данного движка работает на узлах, которые выполняют определенную логику.

Обычные пользователи могут играть в созданные новеллы и сохранять свой прогресс. Прогрессом как раз и будет узел,
на котором они остановились.

Таким образом, у нас есть сущности user, novel, node. И мы должны с ними работать по их Id (UUID).
Savings-service является CRUD-системой для сохранений визуальных новелл.

# Как запускать

* sbt docker:publishLocal
* docker-compose up -d
* sbt "project integration_tests" IntegrationTest/test (Тесты)

http://localhost:8080/docs/ - swagger сервиса  
http://localhost:8081/ - mongo express. Креды: user, pass

# Ручки

* /save/get/ получает сохранение в новелле для указанного пользователя.
* /save/create/ создает новое сохранение в новелле для указанного пользователя.  
* /save/update/ обновляет сохранение в новелле для указанного пользователя.
* /save/delete/ удаляет сохранение в новелле для указанного пользователя.
