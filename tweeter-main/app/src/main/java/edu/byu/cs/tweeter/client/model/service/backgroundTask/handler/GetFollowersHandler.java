package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for GetFollowersTask.
 */
public class GetFollowersHandler extends PagedHandler {
    public GetFollowersHandler(PagedObserver<User> observer) {
        super(observer);
    }
}
