package com.example.sqlite;

public class Expense {
    public Expense(int id, String category, String date, String time, String amount, String description, int rating, String type) {
        this.id = id;
        this.category = category;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.description = description;
        this.rating = rating;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    private int id;
    private String category;
    private String date;
    private String time;
    private String amount;
    private String description;
    private int rating;

    private String type;

    // Constructor, getters, and setters for the fields
    // (You can generate them automatically in Android Studio by right-clicking and selecting "Generate" > "Getter and Setter")
}
