package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

// IsFollowerHandler
public class IsFollowerHandler extends BackgroundTaskHandler<FollowService.IsFollowerObserver> {
    public IsFollowerHandler(FollowService.IsFollowerObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.IsFollowerObserver observer, Bundle data) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        observer.setupFollowButton(isFollower);
    }
}
