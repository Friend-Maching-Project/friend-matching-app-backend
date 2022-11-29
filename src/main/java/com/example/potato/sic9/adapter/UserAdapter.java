package com.example.potato.sic9.adapter;

import com.example.potato.sic9.entity.User;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserAdapter(User user) {
        super(user.getNickname(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getAuthority().toString())));
        this.user = user;
    }
}
