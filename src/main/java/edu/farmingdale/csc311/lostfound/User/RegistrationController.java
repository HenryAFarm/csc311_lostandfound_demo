package edu.farmingdale.csc311.lostfound.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    private final AppUserService appUserService;

    public RegistrationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            Model model) {

        String normalizedUsername = username == null ? "" : username.trim();

        if (normalizedUsername.length() < 3) {
            return showError(model, "Username must be at least 3 characters long.",
                    normalizedUsername);
        }
        if (password == null || password.length() < 6) {
            return showError(model, "Password must be at least 6 characters long.",
                    normalizedUsername);
        }
        if (!appUserService.register(normalizedUsername, password)) {
            return showError(model, "That username is already taken.",
                    normalizedUsername);
        }

        return "redirect:/login?registered";
    }

    private String showError(Model model, String error, String username) {
        model.addAttribute("error", error);
        model.addAttribute("username", username);
        return "register";
    }
}
