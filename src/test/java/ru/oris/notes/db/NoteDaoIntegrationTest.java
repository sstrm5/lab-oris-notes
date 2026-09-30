package ru.oris.notes.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.oris.notes.dao.NoteDao;
import ru.oris.notes.dao.UserDao;

import static org.junit.jupiter.api.Assertions.*;

class NoteDaoIntegrationTest {
    private static final UserDao users = new UserDao();
    private static final NoteDao notes = new NoteDao();

    @BeforeAll
    static void setUp() throws Exception {
        Database.configureForTests("jdbc:h2:mem:notes-test;DB_CLOSE_DELAY=-1");
        Database.initialize();
    }

    @Test
    void isolatesNotesByOwnerAndSupportsSearch() throws Exception {
        var alice = users.create("alice", "hash");
        var bob = users.create("bob", "hash");
        notes.create(alice.getId(), "Планы", "Купить молоко");
        notes.create(bob.getId(), "Секрет", "Только для Боба");

        assertEquals(1, notes.findAllByUser(alice.getId(), "молоко").size());
        assertTrue(notes.findAllByUser(alice.getId(), "секрет").isEmpty());
        assertEquals("Планы", notes.findAllByUser(alice.getId(), "").getFirst().getName());
    }

    @Test
    void cannotUpdateOrDeleteAnotherUsersNote() throws Exception {
        var owner = users.create("owner", "hash");
        var stranger = users.create("stranger", "hash");
        notes.create(owner.getId(), "Личная", "Исходный текст");
        int noteId = notes.findAllByUser(owner.getId(), "").getFirst().getId();

        assertFalse(notes.update(noteId, stranger.getId(), "Взломано", "Чужой текст"));
        assertFalse(notes.delete(noteId, stranger.getId()));
        assertEquals("Исходный текст", notes.findByIdAndUser(noteId, owner.getId()).orElseThrow().getText());
    }
}
