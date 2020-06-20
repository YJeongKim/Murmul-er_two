package space.yjeong.exception;

public class FileUploadException extends ExpectedException {

    private static final String MESSAGE = "파일 업로드에 실패하였습니다.";

    public FileUploadException() {
        super(MESSAGE);
    }
}
