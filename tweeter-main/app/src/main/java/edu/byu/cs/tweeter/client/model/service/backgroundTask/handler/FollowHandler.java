package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class FollowHandler extends BackgroundTaskHandler<FollowService.ChangeFollowObserver> {

    public FollowHandler(FollowService.ChangeFollowObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.ChangeFollowObserver observer, Bundle data) {
        observer.updateSelectedUserFollowingAndFollowers();
    }
}
