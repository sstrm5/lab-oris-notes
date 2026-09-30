package ru.oris.notes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.oris.notes.dao.NoteDao;
import ru.oris.notes.model.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/notes")
public class NotesServlet extends ServletSupport {
    private final NoteDao noteDao = new NoteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = currentUser(request);
        String search = request.getParameter("search");
        search = search == null ? "" : search.trim();
        if (search.length() > 200) search = search.substring(0, 200);
        try {
            request.setAttribute("notes", noteDao.findAllByUser(user.getId(), search));
            request.setAttribute("currentUser", user);
            request.setAttribute("searchQuery", search);
            view(request, response, "notes.jsp");
        } catch (SQLException exception) {
            throw new ServletException("Не удалось загрузить заметки", exception);
        }
    }
}
