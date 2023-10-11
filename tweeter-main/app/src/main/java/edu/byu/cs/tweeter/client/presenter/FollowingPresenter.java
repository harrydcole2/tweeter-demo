package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter {

    private static final int PAGE_SIZE = 10;

    private User lastFollowee;

    private boolean hasMorePages;

    private boolean isLoading = false;

    public interface View {

        void setLoadingFooter(boolean value);

        void displayMessage(String message);

        void addMoreFollowees(List<User> followees);

        void startMainActivity(Bundle bundle);
    }

    private View view;

    private FollowService followService;
    private UserService userService;

    public FollowingPresenter(View view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            followService.loadMoreItemsForFollowing(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastFollowee, new FollowServiceObserver());
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
            lastFollowee = (items.size() > 0) ? items.get(items.size() - 1) : null;
            view.addMoreFollowees(items);
        }
    }

    private class UserServiceObserver implements UserService.UserObserver {

        @Override
        public void handleError(String message) {
            view.displayMessage(message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }

        @Override
        public void startActivity(Bundle bundle) {
            view.startMainActivity(bundle);
        }
    }
}
