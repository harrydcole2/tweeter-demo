package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter extends BasePresenter{
    public User getCurrUser() {
        return Cache.getInstance().getCurrUser();
    }

    public void clearCache() {
        Cache.getInstance().clearCache();
    }

    private View view;

    public interface View extends BaseView {
        void setupFollowButton(boolean isFollower);
        
        void enableFollowButton();

        void updateSelectedUserFollowingAndFollowers();

        void updateFollowButton(boolean b);

        void logoutUser();

        void setFollowerCount(int count);

        void setFollowingCount(int count);
    }

    public MainActivityPresenter(MainActivityPresenter.View view) {
        this.view = view;
    }

    public void isFollower(User selectedUser) {
        followService.isFollower(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerObserver());
    }

    public void followOrUnfollow(boolean isFollowing, User selectedUser) {
        if (isFollowing) {
            view.displayMessage("Removing " + selectedUser.getName());
            followService.unfollow(selectedUser, Cache.getInstance().getCurrUserAuthToken(), new UnfollowObserver());
        } else {
            view.displayMessage("Adding " + selectedUser.getName());
            followService.follow(selectedUser, Cache.getInstance().getCurrUserAuthToken(), new ChangeFollowObserver());
        }
    }

    public void logout() {
        userService.logout(Cache.getInstance().getCurrUserAuthToken(), new LogoutServiceObserver());
    }

    public void postStatus(String post) {
        Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), System.currentTimeMillis(), parseURLs(post), parseMentions(post)); //TODO sink task
        statusService.postStatus(Cache.getInstance().getCurrUserAuthToken(), newStatus, new PostStatusServiceObserver());
    }

    public String getFormattedDateTime() throws ParseException { //TODO destroy this one
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) { //TODO Presenter takes on this cake
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public List<String> parseMentions(String post) { //TODO Presenter desert
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    public int findUrlEndIndex(String word) { //TODO Presenter getting fat
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public void countFollowingAndFollowers(User selectedUser) {
        followService.countFollowingAndFollowers(selectedUser, new GetFollowersCountObserver(),
                new GetFollowingCountObserver());
    }


    private class IsFollowerObserver implements FollowService.IsFollowerObserver {

        @Override
        public void handleError(String message) {
            view.displayMessage(message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayMessage("Failed to determine following relationship because of exception: " + ex.getMessage());
        }
        @Override
        public void setupFollowButton(boolean isFollower) {
            // If logged in user if a follower of the selected user, display the follow button as "following"
            view.setupFollowButton(isFollower);
        }
    }

    private class ChangeFollowObserver implements FollowService.ChangeFollowObserver {

        @Override
        public void handleError(String message) {
            view.displayMessage(message);
            view.enableFollowButton();
        }

        @Override
        public void handleException(Exception ex) {
            view.displayMessage("Failed to follow because of exception: " + ex.getMessage());
            view.enableFollowButton();
        }
        @Override
        public void updateSelectedUserFollowingAndFollowers() {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(false);
            view.enableFollowButton();
        }
    }

    private class UnfollowObserver implements FollowService.UnfollowObserver {

        @Override
        public void handleError(String message) {
            view.displayMessage(message);
            view.enableFollowButton();
        }

        @Override
        public void handleException(Exception ex) {
            view.displayMessage("Failed to unfollow because of exception: " + ex.getMessage());
            view.enableFollowButton();
        }

        @Override
        public void updateSelectedUserFollowingAndFollowers() {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(true);
            view.enableFollowButton();
        }
    }

    private class LogoutServiceObserver extends BaseServiceObserver implements UserService.LogoutObserver {
        @Override
        public void logout() {
            view.logoutUser();
        }

        @Override
        protected String getTaskString() {
            return "logout";
        }
    }

    private class PostStatusServiceObserver extends BaseServiceObserver implements StatusService.PostStatusObserver {
        @Override
        public void displaySuccess(String message) {
            view.displayMessage(message);
        }

        @Override
        protected String getTaskString() {
            return "post status";
        }
    }

    private class GetFollowersCountObserver extends BaseServiceObserver implements FollowService.GetFollowersCountObserver {
        @Override
        public void displayFollowersCount(int count) {
            view.setFollowerCount(count);
        }

        @Override
        protected String getTaskString() {
            return "get followers count";
        }
    }

    private class GetFollowingCountObserver extends BaseServiceObserver implements FollowService.GetFollowingCountObserver {
        @Override
        public void displayFollowingCount(int count) {
            view.setFollowingCount(count);
        }

        @Override
        protected String getTaskString() {
            return "get following count";
        }
    }
}
