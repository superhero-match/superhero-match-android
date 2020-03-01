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

import androidx.annotation.Nullable;

public class CheckEmailResponse {

    private int status;
    private boolean isRegistered;
    private boolean isDeleted;
    private boolean isBlocked;
    @Nullable
    private User superhero;

    public CheckEmailResponse() {
    }

    public CheckEmailResponse(int status, boolean isRegistered, boolean isDeleted, boolean isBlocked, @Nullable User superhero) {
        this.status = status;
        this.isRegistered = isRegistered;
        this.isDeleted = isDeleted;
        this.isBlocked = isBlocked;
        this.superhero = superhero;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Nullable
    public User getSuperhero() {
        return superhero;
    }

    public void setSuperhero(@Nullable User superhero) {
        this.superhero = superhero;
    }

    @Override
    public String toString() {
        return "CheckEmailResponse{" +
                "status=" + status +
                ", isRegistered=" + isRegistered +
                ", isDeleted=" + isDeleted +
                ", isBlocked=" + isBlocked +
                ", superhero=" + superhero +
                '}';
    }
}
