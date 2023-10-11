package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class FollowHandler extends BackgroundTaskHandler {

    public FollowHandler(FollowService.FollowObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        FollowService.FollowObserver followObserver = (FollowService.FollowObserver) observer;

        followObserver.updateSelectedUserFollowingAndFollowers();
    }
}
