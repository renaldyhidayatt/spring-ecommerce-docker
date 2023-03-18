package com.sanedge.myecommerce.auth.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanedge.myecommerce.repository.ApplicationUserRepository;

@Service
public class ApplicationUserService implements UserDetailsService {
    private final ApplicationUserRepository repo;

    public ApplicationUserService(ApplicationUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return this.repo
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("Username %s not found", username)));
    }
}
