package space.yjeong.exception;

public class SalesPostNotFoundException extends ExpectedException {

    private static final String MESSAGE = "해당 글이 없습니다.";

    public SalesPostNotFoundException() {
        super(MESSAGE);
    }

    public SalesPostNotFoundException(Long salesPostId) {
        super(MESSAGE + "(salesPostId=" + salesPostId + ")");
    }
}
