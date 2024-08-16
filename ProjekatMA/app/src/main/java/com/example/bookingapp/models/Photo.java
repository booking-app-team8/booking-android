package com.example.bookingapp.models;

public class Photo {
    private Long id;
    private String path;
    private boolean deleted;

    public Photo() {
    }

    public Photo(Long id, String path, boolean deleted) {
        this.id = id;
        this.path = path;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
