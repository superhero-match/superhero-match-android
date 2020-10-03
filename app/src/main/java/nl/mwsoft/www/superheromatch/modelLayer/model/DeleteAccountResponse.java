package nl.mwsoft.www.superheromatch.modelLayer.model;

public class DeleteAccountResponse {
    private int status;

    public DeleteAccountResponse() {
    }

    public DeleteAccountResponse(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
