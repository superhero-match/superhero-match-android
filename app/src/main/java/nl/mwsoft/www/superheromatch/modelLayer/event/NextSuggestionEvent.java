package nl.mwsoft.www.superheromatch.modelLayer.event;

public class NextSuggestionEvent {

    private boolean nextHasBeenClicked;

    public NextSuggestionEvent() {
    }

    public NextSuggestionEvent(boolean nextHasBeenClicked) {
        this.nextHasBeenClicked = nextHasBeenClicked;
    }

    public boolean isNextHasBeenClicked() {
        return nextHasBeenClicked;
    }

    public void setNextHasBeenClicked(boolean nextHasBeenClicked) {
        this.nextHasBeenClicked = nextHasBeenClicked;
    }
}
