package nl.mwsoft.www.superheromatch.modelLayer.model;

public class CheckEmailResponse {

    private int status;
    private boolean isRegistered;
    private boolean isDeleted;
    private boolean isBlocked;

    public CheckEmailResponse() {
    }

    public CheckEmailResponse(int status, boolean isRegistered, boolean isDeleted, boolean isBlocked) {
        this.status = status;
        this.isRegistered = isRegistered;
        this.isDeleted = isDeleted;
        this.isBlocked = isBlocked;
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

    @Override
    public String toString() {
        return "CheckEmailResponse{" +
                "status=" + status +
                ", isRegistered=" + isRegistered +
                ", isDeleted=" + isDeleted +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
