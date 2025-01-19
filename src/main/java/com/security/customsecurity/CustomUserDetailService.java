package com.security.customsecurity;

import com.security.entity.User;
import com.security.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService
        implements UserDetailsService
{

    private final UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo) {this.userRepo = userRepo;}

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        User user = userRepo.findByEmail(username);
        if(user==null) throw new UsernameNotFoundException(String.format("Email:- %s NOT FOUND.",username));

        return new CustomUser(user);
    }
}
