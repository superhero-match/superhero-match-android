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

import java.util.ArrayList;

public class OfflineMessagesResponse {

    private ArrayList<OfflineMessage> messages;
    private int status;

    public OfflineMessagesResponse() {
    }

    public OfflineMessagesResponse(int status) {
        this.status = status;
    }

    public OfflineMessagesResponse(ArrayList<OfflineMessage> messages, int status) {
        this.messages = messages;
        this.status = status;
    }

    public ArrayList<OfflineMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<OfflineMessage> messages) {
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
