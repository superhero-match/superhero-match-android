package nl.mwsoft.www.superheromatch.modelLayer.model;

public class UpdateResponse {
    private int status;
    private boolean updated;

    public UpdateResponse() {
    }

    public UpdateResponse(int status, boolean updated) {
        this.status = status;
        this.updated = updated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "UpdateResponse{" +
                "status=" + status +
                ", updated=" + updated +
                '}';
    }
}
