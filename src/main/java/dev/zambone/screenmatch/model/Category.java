package dev.zambone.screenmatch.model;

public enum Category {
    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    ADVENTURE ("Adventure");

    private final String categoryOmdb;

    Category(String categoryOmdb) {
        this.categoryOmdb = categoryOmdb;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.categoryOmdb.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No categories found for the provided term: " + text);
    }
}
