package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFeedHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetStoryHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PostStatusHandler;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends BaseService {

    public interface FeedObserver extends PagedObserver<Status> {}

    public interface StoryObserver extends PagedObserver<Status> {}

    public interface PostStatusObserver extends ServiceObserver {
        void displaySuccess(String message);
    }

    public void loadMoreItemsForFeed(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus,
                                     FeedObserver observer) {
        GetFeedTask getFeedTask = new GetFeedTask(currUserAuthToken,
                user, pageSize, lastStatus, new GetFeedHandler(observer));
        executeTask(getFeedTask);
    }

    public void loadMoreItemsForStory(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus,
                                      StoryObserver observer) {
        GetStoryTask getStoryTask = new GetStoryTask(currUserAuthToken,
                user, pageSize, lastStatus, new GetStoryHandler(observer));
        executeTask(getStoryTask);
    }

    public void postStatus(AuthToken currUserAuthToken, Status newStatus, PostStatusObserver observer) {
        PostStatusTask statusTask = new PostStatusTask(currUserAuthToken,
                newStatus, new PostStatusHandler(observer));
        executeTask(statusTask);
    }
}
