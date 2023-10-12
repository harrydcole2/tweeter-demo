package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter<User>{
    public FollowersPresenter(PagedView view) {
        this.view = view;
    }
    @Override
    protected void callServiceToLoad(User user) {
        followService.loadMoreItemsForFollowers(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                lastItem, new PagedServiceObserver());
    }
}
