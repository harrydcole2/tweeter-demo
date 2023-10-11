package edu.byu.cs.tweeter.client.model.service;

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
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends BaseService {

    public interface FolloweesObserver extends PagedObserver<User> {}

    public interface FollowersObserver extends PagedObserver<User> {}

    public interface IsFollowerObserver extends ServiceObserver {
        void setupFollowButton(boolean isFollower);
    }

    public interface ChangeFollowObserver extends ServiceObserver {
        void updateSelectedUserFollowingAndFollowers();
    }

    public interface UnfollowObserver extends ServiceObserver {
        void updateSelectedUserFollowingAndFollowers();
    }

    public interface GetFollowersCountObserver extends ServiceObserver {
        void displayFollowersCount(int count);
    }

    public interface GetFollowingCountObserver extends ServiceObserver {
        void displayFollowingCount(int count);
    }
    public void loadMoreItemsForFollowing(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, FolloweesObserver observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowingHandler(observer));
        executeTask(getFollowingTask);
    }

    public void loadMoreItemsForFollowers(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, FollowersObserver observer) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollower, new GetFollowersHandler(observer));
        executeTask(getFollowersTask);
    }

    public void isFollower(AuthToken currUserAuthToken, User currUser, User selectedUser, IsFollowerObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(currUserAuthToken,
                currUser, selectedUser, new IsFollowerHandler(observer));
        executeTask(isFollowerTask);
    }

    public void unfollow(User selectedUser, AuthToken currUserAuthToken, UnfollowObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                selectedUser, new UnfollowHandler(observer));
        executeTask(unfollowTask);
    }

    public void follow(User selectedUser, AuthToken currUserAuthToken, ChangeFollowObserver observer) {
        FollowTask followTask = new FollowTask(currUserAuthToken,
                selectedUser, new FollowHandler(observer));
        executeTask(followTask);
    }

    public void countFollowingAndFollowers(User selectedUser, GetFollowersCountObserver followersObserver,
                                           GetFollowingCountObserver followingObserver) {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowersCountHandler(followersObserver));
        executeTask(followersCountTask);

        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new GetFollowingCountHandler(followingObserver));
        executeTask(followingCountTask);
    }
}
