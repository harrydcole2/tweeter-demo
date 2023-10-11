package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.RegisterHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UserService {

    public interface UserObserver extends ServiceObserver {
        void startActivity(Bundle bundle);
    }

    public interface LoginObserver extends ServiceObserver {
        void loginToActivity(Bundle bundle);
    }

    public interface RegisterObserver extends ServiceObserver {
        void registerToActivity(Bundle bundle);
    }

    public interface LogoutObserver extends ServiceObserver {
        void logout();
    }

    public void getUserProfile(AuthToken currUserAuthToken, String userAlias, UserObserver observer) {
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken,
                userAlias, new GetUserHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }

    public void login(String username, String password, LoginObserver observer) {
        LoginTask loginTask = new LoginTask(username, password, new LoginHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64,
                         RegisterObserver observer) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName, alias, password,
                imageBytesBase64, new RegisterHandler(observer));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);
    }

    public void logout(AuthToken currUserAuthToken, LogoutObserver observer) {
        LogoutTask logoutTask = new LogoutTask(currUserAuthToken, new LogoutHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }
}
