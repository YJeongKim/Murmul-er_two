package space.yjeong.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponseDto {
    private String message;
    private String status;
    private String subMessage;
    private Long id;

    public MessageResponseDto(String message, Long id) {
        this.message = message;
        this.status = "SUCCESS";
        this.subMessage = "";
        this.id = id;
    }

    public MessageResponseDto(String message, String subMessage, Long id) {
        this.message = message;
        this.status = "SUCCESS";
        this.subMessage = subMessage;
        this.id = id;
    }

    @Builder
    public MessageResponseDto(String message, String subMessage) {
        this.message = message;
        this.status = "FAIL";
        this.subMessage = subMessage;
        this.id = 0L;
    }
}
