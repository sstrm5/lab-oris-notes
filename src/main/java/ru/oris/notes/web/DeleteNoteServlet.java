package ru.oris.notes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.oris.notes.dao.NoteDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/notes/delete")
public class DeleteNoteServlet extends ServletSupport {
    private final NoteDao noteDao = new NoteDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = id(request);
        if (id != null) {
            try {
                noteDao.delete(id, currentUser(request).getId());
            } catch (SQLException exception) {
                throw new ServletException("Не удалось удалить заметку", exception);
            }
        }
        redirect(request, response, "/notes");
    }
}
