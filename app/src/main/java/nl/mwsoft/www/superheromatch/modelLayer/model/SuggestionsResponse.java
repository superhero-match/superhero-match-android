/*
  Copyright (C) 2019 - 2020 MWSOFT
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
