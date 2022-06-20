package sk.tuke.gamestudio.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.server.entity.User;

import sk.tuke.gamestudio.server.repository.UserRepository;


import javax.transaction.Transactional;

@Transactional
public class UserServiceJPA implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User login(String username, String password) {
        return userRepository.findUser(username, password);
    }
}
