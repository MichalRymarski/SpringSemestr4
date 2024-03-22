package org.example;

import java.io.IOException;
import java.util.List;

public interface UserRepo {
    public User getUser(int ID);

    public List<User> getAllUsers() ;

    public void loadUsers() throws IOException;


    }

