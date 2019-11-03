package com.pavliuchenko.jwtauthservice.service.user;

import com.pavliuchenko.jwtauthservice.domain.User;
import com.pavliuchenko.jwtauthservice.repository.UserRepository;
import com.pavliuchenko.jwtauthservice.service.dto.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Void> create(String username, String password, String fullName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        return userRepository.insert(user).then();
    }

    @Override
    public Mono<UserInfo> getUserInfo(String id) {
        return userRepository.findById(id)
                .map(user -> new UserInfo(user.getId(), user.getFullName(), user.getRole().name()));
    }
}
