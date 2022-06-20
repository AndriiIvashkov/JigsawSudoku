package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.User;

public interface UserService {

    User login(String login, String password);

}
