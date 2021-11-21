# Lord planet
1) Создать две базы в postgres(предварительно его установив с официального сайта). Доступен по ссылке: https://www.postgresql.org/download/

2) Заменить на свои учетные данные для бд(application.properties и application_test.properties ):

      spring.datasource.url=jdbc:postgresql://localhost:5433/nti

      spring.datasource.username=postgres

      spring.datasource.password=123
      

3) Также для корректной работы тестов selenium прошу установить последнюю версию google chrome с официального сайта. Доступна по ссылке:
https://support.google.com/chrome/answer/95346?hl=ru&co=GENIE.Platform%3DDesktop
