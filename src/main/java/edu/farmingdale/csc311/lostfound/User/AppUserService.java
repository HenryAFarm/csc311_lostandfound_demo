package edu.farmingdale.csc311.lostfound.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(String username, String rawPassword) {
        if (repository.existsByUsername(username)) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);
        repository.save(new AppUser(username, hashedPassword));
        return true;
    }
}
