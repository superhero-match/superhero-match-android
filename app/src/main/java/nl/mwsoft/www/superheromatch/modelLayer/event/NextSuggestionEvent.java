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
