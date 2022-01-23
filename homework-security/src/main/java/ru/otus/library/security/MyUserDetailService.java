package ru.otus.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.library.models.User;
import ru.otus.library.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        List<User> userList = userRepository.findByUsername(s);

        if (userList.size() > 0) {
            User user = userList.get(0);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getListOfUserAuthorities(user));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private List<GrantedAuthority> getListOfUserAuthorities(User user) {
        List<GrantedAuthority> resultList = new ArrayList<>();
        for (String userRole : user.getRoles()) {
            resultList.add(new SimpleGrantedAuthority(userRole));
        }
        return resultList;
    }
}
