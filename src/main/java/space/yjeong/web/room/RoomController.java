package space.yjeong.web.room;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.yjeong.service.room.RoomService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final HttpSession httpSession;

    @ApiOperation("UI : 방 관리 페이지")
    @GetMapping
    public String roomsManage() {
        return "/room/room-manage";
    }

    @ApiOperation("UI : 방 등록 페이지")
    @GetMapping("/register")
    public String roomRegister() {
        return "/room/room-save";
    }
}
