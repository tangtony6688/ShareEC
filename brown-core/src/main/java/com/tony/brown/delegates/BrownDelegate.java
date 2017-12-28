package com.tony.brown.delegates;

/**
 * Created by Tony on 2017/12/10.
 */

public abstract class BrownDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends BrownDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
