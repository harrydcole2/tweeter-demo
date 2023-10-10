package edu.byu.cs.tweeter.client.presenter;

import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter {

    private static final int PAGE_SIZE = 10;

    private User lastFollower;

    private boolean hasMorePages;

    private boolean isLoading = false;

    public interface View {

        void setLoadingFooter(boolean value);

        void displayMessage(String message);

        void addMoreFollowers(List<User> followers);

        void startMainActivity(Message msg);
    }

    private View view;

    private FollowService followService;

    private UserService userService;

    public FollowersPresenter(FollowersPresenter.View view) {
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
            followService.loadMoreItemsForFollowers(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                    lastFollower, new FollowersServiceObserver());
        }
    }

    public void getUserProfile(String userAlias) {
        userService.getUserProfile(Cache.getInstance().getCurrUserAuthToken(), userAlias, new UserServiceObserver());
    }

    private class FollowersServiceObserver implements FollowService.FollowersObserver {

        @Override
        public void displayError(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage(message);
        }

        @Override
        public void displayException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get following because of exception: " + ex.getMessage());
        }

        @Override
        public void addMoreFollowers(List<User> followers, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FollowersPresenter.this.hasMorePages = hasMorePages;
            lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            view.addMoreFollowers(followers);
        }
    }

    private class UserServiceObserver implements UserService.UserObserver {

        @Override
        public void displayError(String message) {
            view.displayMessage(message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }

        @Override
        public void startActivity(Message msg) {
            view.startMainActivity(msg);
        }
    }
}
