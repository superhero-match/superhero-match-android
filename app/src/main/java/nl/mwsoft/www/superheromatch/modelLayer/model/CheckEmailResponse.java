package nl.mwsoft.www.superheromatch.modelLayer.model;

import androidx.annotation.Nullable;

public class CheckEmailResponse {

    private int status;
    private boolean isRegistered;
    private boolean isDeleted;
    private boolean isBlocked;
    @Nullable
    private User superhero;

    public CheckEmailResponse() {
    }

    public CheckEmailResponse(int status, boolean isRegistered, boolean isDeleted, boolean isBlocked, @Nullable User superhero) {
        this.status = status;
        this.isRegistered = isRegistered;
        this.isDeleted = isDeleted;
        this.isBlocked = isBlocked;
        this.superhero = superhero;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Nullable
    public User getSuperhero() {
        return superhero;
    }

    public void setSuperhero(@Nullable User superhero) {
        this.superhero = superhero;
    }

    @Override
    public String toString() {
        return "CheckEmailResponse{" +
                "status=" + status +
                ", isRegistered=" + isRegistered +
                ", isDeleted=" + isDeleted +
                ", isBlocked=" + isBlocked +
                ", superhero=" + superhero +
                '}';
    }
}
