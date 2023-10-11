package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status>{

    public FeedPresenter(FeedPresenter.View view) {
        this.view = view;
    }

    public void getUserProfile(String userAlias) {
        userService.getUserProfile(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new UserServiceObserver());
    }

    public interface View extends PagedView<Status> {}

    @Override
    protected void callServiceToLoad(User user) {
        statusService.loadMoreItemsForFeed(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                lastItem, new FeedServiceObserver());
    }

    private class FeedServiceObserver extends PagedServiceObserver implements StatusService.FeedObserver {}

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
