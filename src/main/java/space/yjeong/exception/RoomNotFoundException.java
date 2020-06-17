package space.yjeong.exception;

public class RoomNotFoundException extends ExpectedException {

    private static final String MESSAGE = "해당 방이 없습니다.";

    public RoomNotFoundException() {
        super(MESSAGE);
    }

    public RoomNotFoundException(Long roomId) {
        super(MESSAGE + "(roomId=" + roomId + ")");
    }
}
