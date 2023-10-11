package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class GetFollowersCountHandler extends BackgroundTaskHandler<FollowService.GetFollowersCountObserver> {
    public GetFollowersCountHandler(FollowService.GetFollowersCountObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.GetFollowersCountObserver observer, Bundle data) {
        int count = data.getInt(GetFollowersCountTask.COUNT_KEY);
        observer.displayFollowersCount(count);
    }
}
