package com.example.bookingapp.dtos.usersDTOs;

public class BlockDTO {
    private boolean blocked;

    public BlockDTO() {
    }

    public BlockDTO(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
