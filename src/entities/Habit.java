package entities;

import java.time.LocalDate;

public class Habit {
    private int id;                // identificador único del hábito
    private int userId;            // usuario al que pertenece
    private String name;           // ejemplo: "Correr 30 min", "Tomar 2L agua"
    private String category;       // "ejercicio", "alimentación", "descanso"
    private boolean completed;     // si se completó o no
    private LocalDate date;        // fecha del hábito

    // Constructor
    public Habit(int id, int userId, String name, String category, boolean completed, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.completed = completed;
        this.date = date;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", completed=" + completed +
                ", date=" + date +
                '}';
    }
}