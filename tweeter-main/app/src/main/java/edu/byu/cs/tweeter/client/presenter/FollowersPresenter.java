package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter<User>{
    public interface View extends PagedView<User> {}
    public FollowersPresenter(FollowersPresenter.View view) {
        this.view = view;
    }
    @Override
    protected void callServiceToLoad(User user) {
        followService.loadMoreItemsForFollowers(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                lastItem, new FollowersServiceObserver());
    }

    public void getUserProfile(String userAlias) {
        userService.getUserProfile(Cache.getInstance().getCurrUserAuthToken(), userAlias, new UserServiceObserver());
    }

    private class FollowersServiceObserver extends PagedServiceObserver implements FollowService.FollowersObserver {}

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
