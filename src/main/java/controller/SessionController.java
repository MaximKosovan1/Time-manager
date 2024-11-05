package controller;

import model.User;

public class SessionController {
    static private boolean _isLoggedIn;
    static private User _currentUser;

    public SessionController() {
        _isLoggedIn = false;
        _currentUser = null;
    }

    static public User GetUser()
    {
        return _currentUser;
    }

    static public void SetUser(User user)
    {
        _currentUser = user;
        if (_currentUser == null) _isLoggedIn = false;
        else _isLoggedIn = true;
    }
}
