package de.wetie.springboot.controller;

import de.wetie.springboot.entity.AppUser;
import de.wetie.springboot.service.impl.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping()
public class AppUserController {


    private AppUserServiceImpl appUserService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAppUserService(AppUserServiceImpl appUserService) {
        this.appUserService = appUserService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/appUsers")
    public AppUser saveUser(@RequestBody AppUser appUser) {

        String password = appUser.getPassword();
        String newPassword = passwordEncoder.encode(password);
        String username = appUser.getUsername();
        Set<String> authorities = appUser.getAuthorities();

        AppUser newappUser = new AppUser(username, newPassword, authorities);

        return appUserService.addUser(newappUser);
    }
}
