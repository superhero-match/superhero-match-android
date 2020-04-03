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
