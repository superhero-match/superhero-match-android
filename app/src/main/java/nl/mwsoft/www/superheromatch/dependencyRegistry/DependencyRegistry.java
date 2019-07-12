package nl.mwsoft.www.superheromatch.dependencyRegistry;

import nl.mwsoft.www.superheromatch.coordinator.RootCoordinator;
import nl.mwsoft.www.superheromatch.modelLayer.database.matchedUser.MatchedUserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.dateTimeUtil.DateTimeUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.imageProcessing.ImageProcessingUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.internet.InternetConnectionUtil;
import nl.mwsoft.www.superheromatch.modelLayer.helper.util.uuid.UUIDUtil;
import nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager.ModelLayerManager;
import nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager.UserModelLayerManager;
import nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager.UtilModelLayerManager;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;
import nl.mwsoft.www.superheromatch.presenterLayer.main.MainPresenter;
import nl.mwsoft.www.superheromatch.presenterLayer.register.RegisterPresenter;
import nl.mwsoft.www.superheromatch.presenterLayer.verifyIdentity.VerifyIdentityPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.intro.activity.IntroActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.permissionsRequest.activity.PermissionsRequestActivity;
import nl.mwsoft.www.superheromatch.viewLayer.register.activity.RegisterActivity;
import nl.mwsoft.www.superheromatch.viewLayer.verifyIdentity.VerifyIdentityActivity;

public class DependencyRegistry {

    public static DependencyRegistry shared = new DependencyRegistry();

    // region Coordinators

    public RootCoordinator rootCoordinator = new RootCoordinator();

    // endregion

    // region Database Layer

    public UserDatabaseLayer createUserDatabaseLayer(){
        return new UserDatabaseLayer();
    }

    public MatchedUserDatabaseLayer createMatchedUserDatabaseLayer(){
        return new MatchedUserDatabaseLayer();
    }

    // endregion

    // region Util

    public UUIDUtil createUUIDUtil(){
        return new UUIDUtil();
    }

    public DateTimeUtil createDateTimeUtil(){
        return new DateTimeUtil();
    }

    public ImageProcessingUtil createImageProcessingUtil(DateTimeUtil dateTimeUtil){
        return new ImageProcessingUtil(dateTimeUtil);
    }

    public InternetConnectionUtil createInternetConnectionUtil(){
        return new InternetConnectionUtil();
    }

    // endregion

    // region UtilModelLayerManager

    // region AddNewGroupMemberPresenter

    private UtilModelLayerManager createUtilModelLayerManager(InternetConnectionUtil internetConnectionUtil){
        return new UtilModelLayerManager(internetConnectionUtil);
    }

    // endregion

    // region ChatPresenter

    private UtilModelLayerManager createUtilModelLayerManager(InternetConnectionUtil internetConnectionUtil,
                                                              UUIDUtil uuidUtil,
                                                              DateTimeUtil dateTimeUtil){
        return new UtilModelLayerManager(uuidUtil, internetConnectionUtil, dateTimeUtil);
    }
    private UtilModelLayerManager createUtilModelLayerManager(DateTimeUtil dateTimeUtil){
        return new UtilModelLayerManager(dateTimeUtil);
    }

    private UtilModelLayerManager createUtilModelLayerManager(InternetConnectionUtil internetConnectionUtil,
                                                              UUIDUtil uuidUtil){
        return new UtilModelLayerManager(uuidUtil, internetConnectionUtil);
    }

    // endregion


    private UserModelLayerManager createUserModelLayerManager(){
        return new UserModelLayerManager();
    }


    // region Network

    public NetworkLayer createNetworkLayer(){
        return new NetworkLayer();
    }

    //

    // region Model Layer Manager

    public ModelLayerManager createModelLayerManager(UserDatabaseLayer userDatabaseLayer,
                                                     InternetConnectionUtil internetConnectionUtil,
                                                     NetworkLayer networkLayer) {
        return new ModelLayerManager(userDatabaseLayer, internetConnectionUtil, networkLayer);
    }

    public ModelLayerManager createModelLayerManager(UserDatabaseLayer userDatabaseLayer,
                                                     InternetConnectionUtil internetConnectionUtil,
                                                     NetworkLayer networkLayer,
                                                    UUIDUtil uuidUtil){
        return new ModelLayerManager(userDatabaseLayer, internetConnectionUtil, networkLayer, uuidUtil);
    }

    public ModelLayerManager createModelLayerManager(UserDatabaseLayer userDatabaseLayer,
                                                     MatchedUserDatabaseLayer matchedUserDatabaseLayer,
                                                     UUIDUtil uuidUtil,
                                                     DateTimeUtil dateTimeUtil,
                                                     ImageProcessingUtil imageProcessingUtil,
                                                     InternetConnectionUtil internetConnectionUtil,
                                                     NetworkLayer networkLayer){
        return new ModelLayerManager(
                userDatabaseLayer,
                matchedUserDatabaseLayer,
                uuidUtil,
                dateTimeUtil,
                imageProcessingUtil,
                internetConnectionUtil,
                networkLayer
        );
    }

    public ModelLayerManager createModelLayerManager(InternetConnectionUtil internetConnectionUtil,
                                                     NetworkLayer networkLayer){
        return new ModelLayerManager(internetConnectionUtil, networkLayer);
    }

    // endregion

    // region inject

    public void inject(MainActivity mainActivity){
        mainActivity.configureWith(
                rootCoordinator,
                new MainPresenter(
                        createModelLayerManager(
                                createUserDatabaseLayer(),
                                createMatchedUserDatabaseLayer(),
                                createUUIDUtil(),
                                createDateTimeUtil(),
                                createImageProcessingUtil(createDateTimeUtil()),
                                createInternetConnectionUtil(),
                                createNetworkLayer()
                        )
                )
        );
    }

    public void inject(RegisterActivity registerActivity){
        registerActivity.configureWith(
                rootCoordinator,
                new RegisterPresenter(
                    createModelLayerManager(
                            createUserDatabaseLayer(),
                            createInternetConnectionUtil(),
                            createNetworkLayer(),
                            createUUIDUtil()
                    )
                )
        );
    }

    public void inject(IntroActivity introActivity){
        introActivity.configureWith(rootCoordinator);
    }

    public void inject(PermissionsRequestActivity permissionsRequestActivity){
        permissionsRequestActivity.configureWith(rootCoordinator);
    }

    public void inject(VerifyIdentityActivity verifyIdentityActivity){
        verifyIdentityActivity.configureWith(
                rootCoordinator,
                new VerifyIdentityPresenter(
                        createModelLayerManager(
                            createInternetConnectionUtil(),
                            createNetworkLayer()
                        )
                )
        );
    }

    // endregion
}
