package com.taskmanager.service;

import com.taskmanager.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    private User currentUser;

    public void login(User user) {
        this.currentUser = user;
    }

    public User getLoggedInUser() {
        return currentUser;
    }

    public void logout() {
        this.currentUser = null;
    }
}
