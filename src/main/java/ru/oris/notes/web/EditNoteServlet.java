package ru.oris.notes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.oris.notes.dao.NoteDao;
import ru.oris.notes.model.User;
import ru.oris.notes.util.Validation;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/notes/edit")
public class EditNoteServlet extends ServletSupport {
    private final NoteDao noteDao = new NoteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = currentUser(request);
        Integer id = id(request);
        if (id == null) { redirect(request, response, "/notes"); return; }
        try {
            var note = noteDao.findByIdAndUser(id, user.getId());
            if (note.isEmpty()) { redirect(request, response, "/notes"); return; }
            request.setAttribute("mode", "edit");
            request.setAttribute("note", note.get());
            request.setAttribute("currentUser", user);
            view(request, response, "note-form.jsp");
        } catch (SQLException exception) {
            throw new ServletException("Не удалось загрузить заметку", exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = currentUser(request);
        Integer id = id(request);
        if (id == null) { redirect(request, response, "/notes"); return; }
        String name = trim(request.getParameter("name"));
        String text = trim(request.getParameter("text"));
        String error = Validation.note(name, text);
        try {
            var existing = noteDao.findByIdAndUser(id, user.getId());
            if (existing.isEmpty()) { redirect(request, response, "/notes"); return; }
            if (error != null) {
                request.setAttribute("error", error);
                request.setAttribute("mode", "edit");
                request.setAttribute("note", existing.get());
                request.setAttribute("currentUser", user);
                request.setAttribute("draftName", name);
                request.setAttribute("draftText", text);
                view(request, response, "note-form.jsp");
                return;
            }
            noteDao.update(id, user.getId(), name, text);
            redirect(request, response, "/notes");
        } catch (SQLException exception) {
            throw new ServletException("Не удалось сохранить заметку", exception);
        }
    }

    private String trim(String value) { return value == null ? "" : value.trim(); }
}
