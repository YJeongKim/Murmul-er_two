package space.yjeong.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.user.Role;
import space.yjeong.domain.user.User;
import space.yjeong.domain.user.UserRepository;
import space.yjeong.exception.UnauthorizedException;
import space.yjeong.exception.UserNotFoundException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findUserBySessionUser(SessionUser sessionUser) {
        return userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(UserNotFoundException::new);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void checkUserAuthority(User user) {
        if (user.getRole().equals(Role.GUEST)) throw new UnauthorizedException();
    }

    public void checkSameUser(User user1, User user2) {
        if (!user1.getId().equals(user2.getId())) throw new UnauthorizedException();
    }
}
