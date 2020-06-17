package space.yjeong.exception;

public class UnauthorizedException extends ExpectedException {

    private static final String MESSAGE = "권한이 없는 사용자입니다.";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
