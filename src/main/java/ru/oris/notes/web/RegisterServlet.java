package ru.oris.notes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import ru.oris.notes.dao.UserDao;
import ru.oris.notes.util.Validation;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends ServletSupport {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) != null && request.getSession(false).getAttribute("currentUser") != null) {
            redirect(request, response, "/notes");
            return;
        }
        view(request, response, "register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login") == null ? "" : request.getParameter("login").trim();
        String password = request.getParameter("password");
        String error = Validation.login(login);
        if (error == null) error = Validation.password(password);

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("login", login);
            view(request, response, "register.jsp");
            return;
        }

        try {
            userDao.create(login, BCrypt.hashpw(password, BCrypt.gensalt(12)));
            request.getSession(true).setAttribute("flashMessage", "Аккаунт создан. Теперь войдите.");
            redirect(request, response, "/login");
        } catch (SQLException exception) {
            if ("23505".equals(exception.getSQLState())) {
                request.setAttribute("error", "Этот логин уже занят");
                request.setAttribute("login", login);
                view(request, response, "register.jsp");
            } else {
                throw new ServletException("Ошибка регистрации", exception);
            }
        }
    }
}
