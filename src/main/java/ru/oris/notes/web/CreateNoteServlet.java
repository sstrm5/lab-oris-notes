package ru.oris.notes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.oris.notes.dao.NoteDao;
import ru.oris.notes.model.User;
import ru.oris.notes.util.Validation;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/notes/create")
public class CreateNoteServlet extends ServletSupport {
    private final NoteDao noteDao = new NoteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mode", "create");
        request.setAttribute("currentUser", currentUser(request));
        view(request, response, "note-form.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = currentUser(request);
        String name = trim(request.getParameter("name"));
        String text = trim(request.getParameter("text"));
        String error = Validation.note(name, text);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("mode", "create");
            request.setAttribute("currentUser", user);
            request.setAttribute("draftName", name);
            request.setAttribute("draftText", text);
            view(request, response, "note-form.jsp");
            return;
        }
        try {
            noteDao.create(user.getId(), name, text);
            redirect(request, response, "/notes");
        } catch (SQLException exception) {
            throw new ServletException("Не удалось создать заметку", exception);
        }
    }

    private String trim(String value) { return value == null ? "" : value.trim(); }
}
