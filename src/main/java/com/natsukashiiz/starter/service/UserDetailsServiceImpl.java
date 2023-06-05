package com.natsukashiiz.starter.service;

import com.natsukashiiz.starter.entity.User;
import com.natsukashiiz.starter.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = repo.findByUsername(username);
        if (!opt.isPresent()) {
            logger.error("UserDetailsServiceImpl-[loadUserByUsername](not found)");
            return null;
        }
        User user = opt.get();
        logger.info("UserDetailsServiceImpl-[loadUserByUsername]. user: {}", user);
        return UserDetailsImpl.build(user);
    }
}
