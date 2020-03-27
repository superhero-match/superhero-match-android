package nl.mwsoft.www.superheromatch.modelLayer.helper.compare;

import java.util.Comparator;

import nl.mwsoft.www.superheromatch.modelLayer.model.ProfilePicture;

public class ProfilePicturePositionComparator implements Comparator<ProfilePicture> {
    public int compare(ProfilePicture left, ProfilePicture right) {
        return Integer.compare(left.getPosition(), right.getPosition());
    }
}