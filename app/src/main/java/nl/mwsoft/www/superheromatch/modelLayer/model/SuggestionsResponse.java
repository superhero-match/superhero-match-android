package nl.mwsoft.www.superheromatch.modelLayer.model;

import java.util.ArrayList;

public class SuggestionsResponse {

    private int status;
    private ArrayList<Superhero> suggestions;

    public SuggestionsResponse() {
    }

    public SuggestionsResponse(int status, ArrayList<Superhero> superheroes) {
        this.status = status;
        this.suggestions = superheroes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Superhero> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(ArrayList<Superhero> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public String toString() {
        return "SuggestionsResponse{" +
                "status=" + status +
                ", suggestions=" + suggestions +
                '}';
    }
}
