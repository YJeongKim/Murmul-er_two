package space.yjeong.exception;

public class UnableSaveImageException extends ExpectedException {

    public static final String MESSAGE_EXTENSION = "사용할 수 없는 파일 확장자입니다.";
    public static final String MESSAGE_MIME_TYPE = "올바르지 않는 이미지 파일입니다.";
    public static final String MESSAGE_SIZE = "두 개 이상의 이미지를 업로드해야 합니다.";

    public UnableSaveImageException(String message) {
        super(message);
    }
}
