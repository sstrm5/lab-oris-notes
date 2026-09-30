package ru.oris.notes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.oris.notes.model.User;

import java.io.IOException;

abstract class ServletSupport extends HttpServlet {
    protected User currentUser(HttpServletRequest request) {
        return (User) request.getSession(false).getAttribute("currentUser");
    }

    protected Integer id(HttpServletRequest request) {
        try {
            int value = Integer.parseInt(request.getParameter("id"));
            return value > 0 ? value : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    protected void view(HttpServletRequest request, HttpServletResponse response, String jsp)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + jsp).forward(request, response);
    }

    protected void redirect(HttpServletRequest request, HttpServletResponse response, String path)
            throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
