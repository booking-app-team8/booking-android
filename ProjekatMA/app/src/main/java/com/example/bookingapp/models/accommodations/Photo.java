package com.example.bookingapp.models.accommodations;

import java.util.Objects;

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

    public Photo(String path) {
        this.path = path;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id.equals(photo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
