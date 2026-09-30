# Личные заметки

Учебное веб-приложение на Jakarta Servlet/JSP для регистрации пользователей и управления приватными заметками.

## Стек

- Java 21, Jakarta Servlet 6, JSP/JSTL 3
- H2 (файловая база данных)
- BCrypt для паролей
- Maven, JUnit 5

## Запуск

1. Соберите приложение:

   ```bash
   mvn clean package
   ```

2. Разверните `target/notes.war` в контейнере Jakarta Servlet 6, например Apache Tomcat 10.1.
3. Откройте `http://localhost:8080/notes/` (контекст зависит от имени развернутого WAR).

По умолчанию база создаётся в `./data/notes.mv.db`. Настройки можно переопределить переменными окружения:

- `NOTES_DB_URL`
- `NOTES_DB_USER`
- `NOTES_DB_PASSWORD`

Например, для временной базы: `NOTES_DB_URL=jdbc:h2:mem:notes;DB_CLOSE_DELAY=-1`.

## Проверка

```bash
mvn test
```

Интеграционные тесты проверяют поиск и невозможность изменить или удалить чужую заметку.
