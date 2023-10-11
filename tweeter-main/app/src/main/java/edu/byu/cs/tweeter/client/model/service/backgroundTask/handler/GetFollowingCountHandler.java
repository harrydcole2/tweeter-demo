package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class GetFollowingCountHandler extends BackgroundTaskHandler<FollowService.GetFollowingCountObserver> {

    public GetFollowingCountHandler(FollowService.GetFollowingCountObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.GetFollowingCountObserver observer, Bundle data) {
        int count = data.getInt(GetFollowingCountTask.COUNT_KEY);
        observer.displayFollowingCount(count);
    }
}
