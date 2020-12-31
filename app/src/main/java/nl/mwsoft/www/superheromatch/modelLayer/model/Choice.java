/*
  Copyright (C) 2019 - 2021 MWSOFT
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

public class Choice {

    private int choiceId;
    private String chosenUserId;
    private int choice;
    private String createdAt;

    public Choice() {
    }

    public Choice(int choiceId, String chosenUserId, int choice, String createdAt) {
        this.choiceId = choiceId;
        this.chosenUserId = chosenUserId;
        this.choice = choice;
        this.createdAt = createdAt;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public String getChosenUserId() {
        return chosenUserId;
    }

    public void setChosenUserId(String chosenUserId) {
        this.chosenUserId = chosenUserId;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "choiceId=" + choiceId +
                ", chosenUserId='" + chosenUserId + '\'' +
                ", choice=" + choice +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
