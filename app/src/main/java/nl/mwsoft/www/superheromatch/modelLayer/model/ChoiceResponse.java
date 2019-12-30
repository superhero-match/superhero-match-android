package nl.mwsoft.www.superheromatch.modelLayer.model;

public class ChoiceResponse {

    private int status;
    private boolean isMatch;

    public ChoiceResponse() {
    }

    public ChoiceResponse(int status, boolean isMatch) {
        this.status = status;
        this.isMatch = isMatch;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }

    @Override
    public String toString() {
        return "ChoiceResponse{" +
                "status=" + status +
                ", isMatch=" + isMatch +
                '}';
    }
}
