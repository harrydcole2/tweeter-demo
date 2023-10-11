package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class UnfollowHandler extends BackgroundTaskHandler<FollowService.UnfollowObserver> {
    public UnfollowHandler(FollowService.UnfollowObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.UnfollowObserver observer, Bundle data) {
        observer.updateSelectedUserFollowingAndFollowers();
    }
}
