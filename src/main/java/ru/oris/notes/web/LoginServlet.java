package ru.oris.notes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import ru.oris.notes.dao.UserDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends ServletSupport {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            redirect(request, response, "/notes");
            return;
        }
        if (session != null) {
            request.setAttribute("message", session.getAttribute("flashMessage"));
            session.removeAttribute("flashMessage");
        }
        view(request, response, "login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login") == null ? "" : request.getParameter("login").trim();
        String password = request.getParameter("password");
        try {
            var record = userDao.findByLogin(login);
            if (record.isEmpty() || password == null || !BCrypt.checkpw(password, record.get().passwordHash())) {
                request.setAttribute("error", "Неверный логин или пароль");
                request.setAttribute("login", login);
                view(request, response, "login.jsp");
                return;
            }
            HttpSession session = request.getSession(true);
            request.changeSessionId();
            session.setAttribute("currentUser", record.get().asUser());
            session.setMaxInactiveInterval(30 * 60);
            redirect(request, response, "/notes");
        } catch (SQLException exception) {
            throw new ServletException("Ошибка авторизации", exception);
        }
    }
}
