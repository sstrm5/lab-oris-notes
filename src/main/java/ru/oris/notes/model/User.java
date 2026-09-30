package ru.oris.notes.model;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String login;

    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public int getId() { return id; }
    public String getLogin() { return login; }

    @Override
    public String toString() { return login; }
}
