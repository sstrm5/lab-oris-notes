package ru.oris.notes.model;

import java.time.LocalDateTime;

public class Note {
    private final int id;
    private final int userId;
    private final String name;
    private final String text;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Note(int id, int userId, String name, String text,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
