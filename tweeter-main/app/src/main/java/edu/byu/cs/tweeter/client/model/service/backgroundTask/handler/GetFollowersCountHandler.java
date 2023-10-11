package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class GetFollowersCountHandler extends BackgroundTaskHandler {
    public GetFollowersCountHandler(FollowService.GetFollowersCountObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        FollowService.GetFollowersCountObserver followersCountObserver =
                (FollowService.GetFollowersCountObserver) observer;

        int count = data.getInt(GetFollowersCountTask.COUNT_KEY);
        followersCountObserver.displayFollowersCount(count);
    }
}
