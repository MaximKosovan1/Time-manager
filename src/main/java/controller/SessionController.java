package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionController {
    static private boolean _isLoggedIn;
    static private User _currentUser;

    @Autowired
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
