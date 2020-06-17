package space.yjeong.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.user.User;
import space.yjeong.domain.user.UserRepository;
import space.yjeong.exception.UserNotFoundException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findUserBySessionUser(SessionUser sessionUser) {
        return userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(UserNotFoundException::new);
    }
}
