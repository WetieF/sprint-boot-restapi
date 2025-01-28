package de.wetie.springboot.service.impl;

import de.wetie.springboot.entity.AppUser;
import de.wetie.springboot.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements UserDetailsService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username doesn't exist"));

        // get the values from AppUser and create a User Object
        String uname = appUser.getUsername();
        String password = appUser.getPassword();
        Set<String> authorities = appUser.getAuthorities();

        // Set of GrantedAuthority
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // java 8 map
        grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        // pass the properties
        return new User(uname, password, grantedAuthorities);
    }

    // add user in the DB
    public AppUser addUser(AppUser user) {
        return appUserRepository.save(user);
    }
}


/*// iterate through authorities object
        authorities.forEach((role) -> {
            GrantedAuthority auth = new SimpleGrantedAuthority(role);
            // add this object to grantedAuthorities
            grantedAuthorities.add(auth);
        });*/

        /*
        // java 8 map
         grantedAuthorities = authorities.stream()
         .map(role -> new SimpleGrantedAuthority(role))
         .collect(Collectors.toSet());
         */