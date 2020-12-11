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
package nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager;

import nl.mwsoft.www.superheromatch.modelLayer.helper.util.dateTimeUtil.DateTimeUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.internet.InternetConnectionUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.uuid.UUIDUtil;

public class UtilModelLayerManager {

    private UUIDUtil uuidUtil;
    private InternetConnectionUtil internetConnectionUtil;
    private DateTimeUtil dateTimeUtil;


    // region Constructors

    public UtilModelLayerManager() {
    }

    public UtilModelLayerManager(UUIDUtil chatsterUUIDUtil, InternetConnectionUtil internetConnectionUtil, DateTimeUtil chatsterDateTimeUtil) {
        this.uuidUtil = chatsterUUIDUtil;
        this.internetConnectionUtil = internetConnectionUtil;
        this.dateTimeUtil = chatsterDateTimeUtil;
    }

    public UtilModelLayerManager(DateTimeUtil chatsterDateTimeUtil) {
        this.dateTimeUtil = chatsterDateTimeUtil;
    }

    public UtilModelLayerManager(UUIDUtil chatsterUUIDUtil) {
        this.uuidUtil = uuidUtil;
    }

    public UtilModelLayerManager(InternetConnectionUtil internetConnectionUtil) {
        this.internetConnectionUtil = internetConnectionUtil;
    }

    public UtilModelLayerManager(UUIDUtil uuidUtil, InternetConnectionUtil internetConnectionUtil) {
        this.uuidUtil = uuidUtil;
        this.internetConnectionUtil = internetConnectionUtil;
    }

    // endregion

    // region UUID

    public String createUUID() {
        return uuidUtil.generateUUID();
    }

    // endregion

    // region DateTime

    public String getDateTime(){
        return dateTimeUtil.getDateTime();
    }
    public String convertFromUtcToLocal(String utc){
        return dateTimeUtil.convertFromUtcToLocal(utc);
    }

    // endregion

    // region Internet

    public boolean hasInternetConnection() {
        return internetConnectionUtil.hasInternetConnection();
    }

    // endregion
}
