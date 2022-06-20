package sk.tuke.gamestudio.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.server.entity.User;
import sk.tuke.gamestudio.server.repository.RegisterRepository;

public class RegisterServiceJPA implements RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    public User findUserByUsername(String username){
        return registerRepository.findByUsername(username);
    }
    @Override
    public void register(User user){
        if (findUserByUsername(user.getUsername()) != null)
            System.out.println("Customer with this username is already exists");
        else {
            registerRepository.registerUser(user.getUsername(), user.getPassword());
            System.out.println("New account has just been registered");
        }
    }
}
