package com.example.potato.sic9.service;

import com.example.potato.sic9.adapter.UserAdapter;
import com.example.potato.sic9.entity.User;
import com.example.potato.sic9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    // 받은 Email을 통해 user가 실제로 존재하는지 알아보는 메소드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 을 DB에서 찾을 수 없습니다."));
        ;
        return new UserAdapter(user);
    }
}
