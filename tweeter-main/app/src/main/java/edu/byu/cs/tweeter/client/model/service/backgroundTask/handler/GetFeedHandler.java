package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetFeedTask.
 */
public class GetFeedHandler extends PagedHandler {
    public GetFeedHandler(PagedObserver<Status> observer) {
        super(observer);
    }
}
