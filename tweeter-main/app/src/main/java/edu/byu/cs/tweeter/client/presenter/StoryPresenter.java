package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> {
    public StoryPresenter(PagedView view) {
        this.view = view;
    }
    @Override
    protected void callServiceToLoad(User user) {
        statusService.loadMoreItemsForStory(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                lastItem, new PagedServiceObserver());
    }
}