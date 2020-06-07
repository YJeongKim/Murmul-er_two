package space.yjeong.web;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.yjeong.service.RoomService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final HttpSession httpSession;

    @ApiOperation("방 등록 페이지")
    @GetMapping("/post")
    public String roomPost() {
        return "/room/room-save";
    }

    @ApiOperation("방 관리 페이지")
    @GetMapping("/manage")
    public String roomsManage() {
        return "/room/room-manage";
    }
}
