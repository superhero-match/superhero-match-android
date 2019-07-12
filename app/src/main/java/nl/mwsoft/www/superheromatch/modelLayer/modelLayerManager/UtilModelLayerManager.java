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
