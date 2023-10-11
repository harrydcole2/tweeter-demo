package edu.byu.cs.tweeter.client.model.service.observer;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface PagedObserver<T> extends ServiceObserver {
    void addMoreItems(List<T>  items, boolean hasMorePages);
}