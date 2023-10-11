package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticationPresenter extends BasePresenter {
    public interface AuthView extends BaseView {
        void startNewActivity(String name, User registeredUser);
        void setErrorViewText(String text);
    }
}