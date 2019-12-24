package nl.mwsoft.www.superheromatch.modelLayer.model;

import java.util.ArrayList;

public class SuggestionsResponse {

    private int status;
    private ArrayList<Superhero> suggestions;
    private ArrayList<String> superheroIds;

    public SuggestionsResponse() {
    }

    public SuggestionsResponse(int status, ArrayList<Superhero> suggestions, ArrayList<String> superheroIds) {
        this.status = status;
        this.suggestions = suggestions;
        this.superheroIds = superheroIds;
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

    public ArrayList<String> getSuperheroIds() {
        return superheroIds;
    }

    public void setSuperheroIds(ArrayList<String> superheroIds) {
        this.superheroIds = superheroIds;
    }

    @Override
    public String toString() {
        return "SuggestionsResponse{" +
                "status=" + status +
                ", suggestions=" + suggestions +
                ", superheroIds=" + superheroIds +
                '}';
    }
}
