package nl.mwsoft.www.superheromatch.presenterLayer.verifyIdentity;

import io.reactivex.Observable;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager.ModelLayerManager;

public class VerifyIdentityPresenter {

    private ModelLayerManager modelLayerManager;

    public VerifyIdentityPresenter() {
    }

    public VerifyIdentityPresenter(ModelLayerManager modelLayerManager) {
        this.modelLayerManager = modelLayerManager;
    }

    // region Check If Email Already Exists

    public Observable<CheckEmailResponse> checkEmailAlreadyExists(String email) {
        return this.modelLayerManager.checkEmailAlreadyExists(email);
    }

    // endregion
}
