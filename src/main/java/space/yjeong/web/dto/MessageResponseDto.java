package space.yjeong.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponseDto {
    private String message;
    private String status;
    private String subMessage;

    @Builder
    public MessageResponseDto(String message) {
        this.message = message;
        this.status = "SUCCESS";
        this.subMessage = "";
    }

    @Builder
    public MessageResponseDto(String message, String subMessage) {
        this.message = message;
        this.status = "FAIL";
        this.subMessage = subMessage;
    }
}
