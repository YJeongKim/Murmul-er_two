package space.yjeong.exception;

public class UserNotFoundException extends ExpectedException {

    private static final String MESSAGE = "존재하지 않는 사용자입니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
