package space.yjeong.config.auth.dto;

import lombok.Getter;
import space.yjeong.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String email;
    private String nickname;
    private String picture;

    public SessionUser(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.picture = user.getPicture();
    }
}
