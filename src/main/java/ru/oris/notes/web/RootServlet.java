package ru.oris.notes.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("")
public class RootServlet extends ServletSupport {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        redirect(request, response, session != null && session.getAttribute("currentUser") != null ? "/notes" : "/login");
    }
}
