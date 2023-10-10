package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.UnfollowHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    public interface FolloweesObserver {

        void displayError(String message);

        void displayException(Exception ex);

        void addMoreFollowees(List<User> followees, boolean hasMorePages);
    }

    public interface FollowersObserver {

        void displayError(String message);

        void displayException(Exception ex);

        void addMoreFollowers(List<User> followees, boolean hasMorePages);
    }

    public interface IsFollowerObserver {
        void displayError(String message);

        void displayException(Exception ex);

        void setupFollowButton(boolean isFollower);
    }

    public interface FollowObserver {

        void displayError(String message);

        void displayException(Exception ex);

        void updateSelectedUserFollowingAndFollowers();
    }

    public interface UnfollowObserver {

        void displayError(String message);

        void displayException(Exception ex);

        void updateSelectedUserFollowingAndFollowers();
    }

    public interface GetFollowersCountObserver {

        void displayError(String message);

        void displayException(Exception ex);

        void displayFollowersCount(int count);
    }

    public interface GetFollowingCountObserver {

        void displayError(String message);

        void displayException(Exception ex);

        void displayFollowingCount(int count);
    }
    public void loadMoreItemsForFollowing(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, FolloweesObserver observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowingHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }

    public void loadMoreItemsForFollowers(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, FollowersObserver observer) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken, //TODO: Send to shadow realm
                user, pageSize, lastFollower, new GetFollowersHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }

    public void isFollower(AuthToken currUserAuthToken, User currUser, User selectedUser, IsFollowerObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

    public void unfollow(User selectedUser, AuthToken currUserAuthToken, UnfollowObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                selectedUser, new UnfollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }

    public void follow(User selectedUser, AuthToken currUserAuthToken, FollowObserver observer) {
        FollowTask followTask = new FollowTask(currUserAuthToken,
                selectedUser, new FollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }

    public void countFollowingAndFollowers(User selectedUser, GetFollowersCountObserver followersObserver,
                                           GetFollowingCountObserver followingObserver) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowersCountHandler(followersObserver));
        executor.execute(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowingCountHandler(followingObserver));
        executor.execute(followingCountTask);
    }
}
