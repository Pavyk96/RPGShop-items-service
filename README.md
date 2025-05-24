Итоговый проект для курса по Java SpringBoot

Выполнил Мезев Даниил Олегович

Все вопросы можно задать в тг: @payk96

В работу входят 4 микро сервиса:
1) Сервис товаров и заказов: (текущий репозиторий)
2) Сервис аутентификации и авторизации (https://github.com/Pavyk96/RPGShop-users-service)
3) Сервис нотификации (https://github.com/Pavyk96/RPGShop-notification-service)
4) Апи гетевей (https://github.com/Pavyk96/RPGShop-items-service)

Я использовал паттерн микросервисной архитектуры с использованием Kafka, чтобы ассинхронно отправлять уведомления на почту, использовал кеширование через Redis.
Применял апи гетевей, чтобы маршрутизировать запросы и создать единую точку входа, принципы АОП для логирования, метрики, работу с БД и т.д

Вот список всех текм, которые я реализовал в данном проекте:
1. Настройка окружения
2. Hello Spring Boot
3. Dependencies Injections
4. Web Service
5. Валидация, Интернационализация
6. Конфигурация и профили
7. Работа с БД
8. Spring security
9. Application Events and Listeners
10. Аспекты
11. Брокеры сообщений (JMS, AMQP, Kafka)
12. Обслуживание
13. Кэширование

P.S Так же я использовал АПИ Keycloak, библиотеку для отправки уведомлений на почту, контейнеризацию для развертывания






# ЗАПУСК ПРИЛОЖЕНИЯ #

1) Скачайте докер-композ файл из текущего репозитория
2) Запустите контейнер keycloak
3) Перейдите по http://localhost:8484/admin/master/console/#/rpg-shop/realms, для входа используйте логин: admin и пароль: admin
4) Скачайте настройку реалма по этой ссылке: https://drive.google.com/file/d/1Sk2CzSljQGUqXmlv3Xv_o8N4LtzIRuZW/view?usp=sharing
5) Импортируйте реалм:
![image](https://github.com/user-attachments/assets/64549649-0064-4747-bb9e-27c25f8eef50)
6) После импорта реалма, вы должны в докер-композе вставить свои KEYCLOAK_CLIENT_SECRET и KEYCLOAK_PUBLIC_KEY, в микросерсиве аутентификации (auth-service)
7) На данных скринах вы можете увидеть где найти эти значения:
Секрет клиента:
![image](https://github.com/user-attachments/assets/3407dd1b-8eff-40c1-b411-96b0cf059863)
![image](https://github.com/user-attachments/assets/46bb8d63-f617-49ce-862a-1a8a8fa27f4f)
Публиный ключ:
![image](https://github.com/user-attachments/assets/50f89481-16e7-4c8b-a02c-0d9471cfabe8)
8) Замените KEYCLOAK_CLIENT_SECRET и KEYCLOAK_PUBLIC_KEY на полученные значения.
9) Запустите докер композ полностью
10) Перейдите еще раз вам новый реалм кейклока и создайте пользователя (не забудьте назначить пароль во вкладке credentinals)
11) Теперь вы можете использовать апи




