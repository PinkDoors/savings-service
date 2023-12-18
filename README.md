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

# Индивидуальные проекты

В течение второй половины курса вы будете работать над своими собственными проектами.

## Ограничения

1. Проект может быть чем угодно, главное ограничение -- это присутствие работы с сетью в любом виде (подключение к БД, работа с внешним API и т.д.)
2. Весь код должен следовать практикам, изученным в течение курса, а так же быть написан в функциональной парадигме.

3. Короткий и ясный README.md про то, как задеплоить и запустить ваш проект, а также инструкции, как его использовать. Важно, чтобы проверяющие могли задеплоить приложение на своей машине сразу же со всей необходимой для него инфраструктурой. 
   1. Для сервисов, которым для работы нужна своя собственная инфраструктура, например база данных или кэш, требуется использовать [docker-compose](https://docs.docker.com/compose/). Также, если ваш сервис предоставляет API по протоколу HTTP, то должен быть ендпоинт `/docs` с запущенным на нем Swagger UI, в котором должны быть описания для ендпоинтов. Если будете использовать `tapir`, то сделать это можно [так](https://tapir.softwaremill.com/en/latest/docs/openapi.html#using-swagger).
   2. Если ваш сервис зависит только от сервисов третьей стороны (например API Telegram), то можно обойтись [sbt-assembly](https://github.com/sbt/sbt-assembly) или [GraalVM Native Image](https://www.graalvm.org/22.0/reference-manual/native-image/) для создания одиночных исполняемых файлов. В этом случае также необходимо указать, с каким аргументами можно запускать ваш файл (например через опцию `--help`), а также максимально простой пример запуска с описанием. Если используете сервисы третьей стороны, то убедитесь, что его можно использовать бесплатно (ну или можете предоставить проверяющим ваши токены 😈)

Вы можете создать функциональность по своему выбору.

## Оценивание

Вы можете **2** раза запросить промежуточное ревью. Оно не повлияет на итоговую оценку. Ревью можно запросить не позднее, чем за 5 дней до дедлайна (мы укажем более точные дедлайны в формах на EDU, а также в чате курса). Имейте в виду, что ревью не подразумевает выставление промежуточной оценки, мы оставим лишь комментарии в МР.

Постарайтесь найти баланс между функционалом и аттрибутами качества программного обеспечения.

Ваши проекты будут оцениваться по следующим параметрам:

* Корректность работы приложения (логика)
* Корректность использования библиотек и тулзов
* Читабельность кода
* Тесты
* Следование указанным ограничениям

В зависимости от размера проекта, допускается как ужесточение требований, так и их послабление.

## Отправка на проверку

1. Ведите основную работу на ветке `dev`.
1. Если вы хотите отправить работу на предварительное ревью, создайте новую ветку (`review1`, `review2`) от последнего коммита в `dev`, который вы хотите включить в ревью, откройте MR ⚠️**в свой репозиторий**⚠️ и отправьте ссылку в нужную форму на EDU. Мержить после ревью ничего не нужно! Вы так же можете указать, кому из проверяющих отдать работу на проверку, однако мы не гарантируем, что именно он ее проверит в случае, если будет слишком много работ, отданных ему. Если не указывать, то ваш МР проверит случайно выбранный проверяющий. 
1. Если вы хотите отправить финальную версию на оценку:
   1. Откройте MR из dev в master ⚠️**в свой репозиторий**⚠️
   1. Убедитесь, что все пайплайны зеленые (вам придется написать свои или скопировать из ваших ДЗ)
   1. Отправьте ссылку на MR и хэш последнего коммита в финальную форму на EDU.
   1. ⚠️Имейте в виду, что проверка будет идти по коммиту, хэш которого был указан в форме; если такого хэша не будет в истории коммитов, работа проверяться не будет. ⚠️
1. Из-за ограничений платформы EDU просим ответственно отнестись к отправке решений и делать это заранее, чтобы у нас была возможность позволить вам сдать решение еще раз в случае возникновения проблем.
1. Вы молодец!

## Идеи для проектов

| #   | Название                               | Описание                                                                                                                                                                                                        |
|-----|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1.  | Система контроля доступа               | Система для контроллера, которая либо разрешает / не разрешает доступ для сотрудника по его карте. Система логов проходов по контроллерам                                                                       |
| 2.  | CRUD для ведения расходов              | Система для добавления и учета расходов                                                                                                                                                                         |
| 3.  | Менеджер персональных данных           | Система для хранения паролей и документов в шифрованном виде                                                                                                                                                    |
| 4.  | Remind-бот для telegram                | Бот позволяет запланировать напоминалку разово или по расписанию, отправляет сообщение в нужное время.                                                                                                          |
| 5.  | Сервис для шаринга текста              | Аналог pastebin. Ревизии текста, владение по токенам без регистрации.                                                                                                                                           |
| 6.  | Сервис для шаринга файлов              | Аплоад, даунлоад, хранение файлов на файловой системе сервера, владение по токенам без регистрации.                                                                                                             |
| 7.  | Healthcheck-сервис с нотификациями     | Сервис мониторит состояние других ресурсов по http, и шлет нотификации (куда - решайте сами) если что-то начинает проваливать health check                                                                      |
| 8.  | Сокращалка урлов                       | Сокращенные URL живут вечно, проверка уникальности, увеличение длины ключа по мере исчерпания ключей. Проверка доступа до ресурса по сокращенному URL.                                                          |
| 9.  | Телеграм-бот для проведения экзаменов  | Для преподавателей: создание экзаменов, различных вопросов к ним, разбалловки, ограничение по времени и т.д. Для учеников: получение уведомлений (о начале экзамена и времени окончания), возможность ответить. |


## Примеры проектов

Примеры использования стеков, которые вы можете использовать в проектах. Размер ваших проектов может отличаться от представленных.

1. [Shopping Cart](https://github.com/gvolpe/pfps-shopping-cart)
2. [ZIO CRUD sample](https://github.com/adrianfilip/zio-crud-sample)
3. [ZIO Pet Clinic](https://github.com/zio/zio-petclinic)
4. [Typelevel Pet Store](https://github.com/pauljamescleary/scala-pet-store)
5. [Хороший пример Telegram-бота (написан на Scala 3, но по сути каких-то новых фич языка не использует, так что актуален и для Scala 2)](https://github.com/VladKopanev/CITelegramBot)
6. [Fintracker](https://github.com/dimfatal/fintracker) - проект достаточно старый, лучше использовать [Telegramium](https://github.com/apimorphism/telegramium)

## Список популярных библиотек
1. Typelevel (cats стек)
    1. https://github.com/tpolecat/doobie - для взаимодействия с базами данных по протоколу JDBC
    2. https://tapir.softwaremill.com/en/latest/ - для определения HTTP-ендпоинтов, в качестве бекенда можете использовать http4s
    3. https://sttp.softwaremill.com/en/stable/ - HTTP-клиент
    4. https://github.com/typelevel/log4cats или https://docs.tofu.tf/docs/tofu.logging.home - логирование
    5. https://github.com/profunktor/redis4cats - клиент для Redis, можете использовать в качестве кэша
    6. https://fs2.io/#/getstarted/install - функциональный стриминг данных
    7. https://fd4s.github.io/fs2-kafka/ - клиент для Apache Kafka, реализованный через fs2 стримы
1. ZIO 
    1. https://zio.dev/ecosystem/ - список официальных и не только либ
    2. https://zio.dev/zio2-interop-cats3/ - если нужно связать библиотеки Typelevel стека с ZIO
3. https://circe.github.io/circe/ - для парсинга JSON
4. https://github.com/apimorphism/telegramium - библиотека написания ботов для Telegram
5. https://github.com/bot4s/telegram - еще одна библиотека для написания ботов для Telegram
