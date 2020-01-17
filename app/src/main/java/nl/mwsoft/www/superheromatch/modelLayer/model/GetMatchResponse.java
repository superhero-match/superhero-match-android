package nl.mwsoft.www.superheromatch.modelLayer.model;

public class GetMatchResponse {

    private int status;
    private Superhero match;

    public GetMatchResponse() {
    }

    public GetMatchResponse(int status, Superhero match) {
        this.status = status;
        this.match = match;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Superhero getMatch() {
        return match;
    }

    public void setMatch(Superhero match) {
        this.match = match;
    }
}
