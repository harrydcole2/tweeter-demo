package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for GetFollowersTask.
 */
public class GetFollowersHandler extends BackgroundTaskHandler {
    public GetFollowersHandler(FollowService.FollowersObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        FollowService.FollowersObserver followersObserver = (FollowService.FollowersObserver) observer;

        List<User> followers = (List<User>) data.getSerializable(GetFollowersTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(GetFollowingTask.MORE_PAGES_KEY);
        followersObserver.addMoreFollowers(followers, hasMorePages);
    }
}
