package space.yjeong.exception;

public class FileDownloadException extends ExpectedException {

    private static final String MESSAGE = "파일 다운로드에 실패하였습니다.";

    public FileDownloadException() {
        super(MESSAGE);
    }
}
