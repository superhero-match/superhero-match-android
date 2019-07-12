package nl.mwsoft.www.superheromatch.modelLayer.model;

public class CheckEmailResponse {

    private int status;
    private boolean registered;

    public CheckEmailResponse() {
    }

    public CheckEmailResponse(int status, boolean registered) {
        this.status = status;
        this.registered = registered;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "CheckEmailResponse{" +
                "status=" + status +
                ", registered=" + registered +
                '}';
    }
}
