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

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            followService.loadMoreItemsForFollowing(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                    lastItem, new FollowServiceObserver());
        }
    }

    public void getUserProfile(String userAlias) {
        userService.getUserProfile(Cache.getInstance().getCurrUserAuthToken(), userAlias, new UserServiceObserver());
        //Toast.makeText(getContext(), "Getting user's profile...", Toast.LENGTH_LONG).show();
    }

    private class FollowServiceObserver implements FollowService.FolloweesObserver {

        @Override
        public void handleError(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage(message);
        }

        @Override
        public void handleException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get following because of exception: " + ex.getMessage());
        }

        @Override
        public void addMoreItems(List<User> items, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FollowingPresenter.this.hasMorePages = hasMorePages;
            lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
            view.addMoreItems(items);
        }
    }

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
