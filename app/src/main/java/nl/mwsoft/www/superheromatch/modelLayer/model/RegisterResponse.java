package nl.mwsoft.www.superheromatch.modelLayer.model;

public class RegisterResponse  {

    private int status;
    private boolean registered;

    public RegisterResponse() {
    }

    public RegisterResponse(int status, boolean registered) {
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
        return "RegisterResponse{" +
                "status=" + status +
                ", registered=" + registered +
                '}';
    }
}
