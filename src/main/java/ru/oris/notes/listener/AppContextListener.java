package ru.oris.notes.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.oris.notes.db.Database;

import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            Database.initialize();
        } catch (SQLException exception) {
            throw new IllegalStateException("Не удалось инициализировать базу данных", exception);
        }
    }
}
