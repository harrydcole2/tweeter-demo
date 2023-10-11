package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User>{
    public interface View extends PagedView<User>{}

    public FollowingPresenter(View view) {
        this.view = view;
    }
    @Override
    protected void callServiceToLoad(User user) {
        followService.loadMoreItemsForFollowing(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                lastItem, new FollowServiceObserver());
    }

    public void getUserProfile(String userAlias) {
        userService.getUserProfile(Cache.getInstance().getCurrUserAuthToken(), userAlias, new UserServiceObserver());
        //Toast.makeText(getContext(), "Getting user's profile...", Toast.LENGTH_LONG).show();
    }

    private class FollowServiceObserver extends PagedServiceObserver implements FollowService.FolloweesObserver {}

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
