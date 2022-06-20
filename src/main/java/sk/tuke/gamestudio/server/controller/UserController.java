package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.server.entity.User;
import sk.tuke.gamestudio.server.service.RegisterServiceJPA;
import sk.tuke.gamestudio.server.service.UserServiceJPA;

@Controller
public class UserController {

    private User loginUser;

    @Autowired
    private UserServiceJPA userService;
    @Autowired
    private RegisterServiceJPA registerService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("login_user", new User());
        return "login_page";
    }
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("login_user", new User());
        return "register_page";
    }

    @GetMapping("/game")
    public String getGamePage(Model model) {
        model.addAttribute("login_user", new User());
        return "game";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute(value = "login_user") User user, Model model) {
        System.out.println("register request: " + user);

        if (!user.getPassword().equals(user.getVerifiedPassword())) {
            model.addAttribute("verifiedPassError", "error");
            return "register_page";
        } else if (user.getPassword().length() < 5) {
            model.addAttribute("lengthPassError", "error");
            return "register_page";
        }
        else if (registerService.findUserByUsername(user.getUsername()) != null) {
            model.addAttribute("usernameError", "error");
            return "register_page";
        }
        registerService.register(user);
        return "redirect:/login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute(value = "login_user") User user, Model model) {

        System.out.println("register request: " + user);
        loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser == null) {
            model.addAttribute("usernameLoginError", "error");
            return "login_page";
        } else {
            model.addAttribute("login_user", loginUser.getUsername());
            return "redirect:/game";
        }
    }
    public User getLoggedUser() {
        return loginUser;
    }

    public boolean isLogged() {
        return loginUser != null;
    }

    @RequestMapping("/logout")
    public String logout() {
        loginUser = null;
        return "redirect:/login";
    }

}