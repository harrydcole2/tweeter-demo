package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> {
    public StoryPresenter(StoryPresenter.View view) {
        this.view = view;
    }

    @Override
    protected void callServiceToLoad(User user) {
        statusService.loadMoreItemsForStory(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                lastItem, new StoryServiceObserver());
    }

    public void getUserProfile(String userAlias) {
        userService.getUserProfile(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new UserServiceObserver());
    }

    public interface View extends PagedView<Status>{}

    private class StoryServiceObserver extends PagedServiceObserver implements StatusService.StoryObserver {}

    private class UserServiceObserver extends BaseServiceObserver implements UserService.UserObserver {
        @Override
        public void startActivity(Bundle bundle) {
            view.startMainActivity(bundle);
        }

        @Override
        protected String getTaskString() {
            return "get user's profile";
        }
    }
}