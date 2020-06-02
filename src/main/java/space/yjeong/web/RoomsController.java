package space.yjeong.web;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.yjeong.service.RoomsService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rooms")
public class RoomsController {
    private final RoomsService roomsService;
    private final HttpSession httpSession;

    @ApiOperation("방 등록 페이지")
    @GetMapping("/post")
    public String roomsPost() {
        return "/rooms/rooms-save";
    }

    @ApiOperation("방 관리 페이지")
    @GetMapping("/manage")
    public String roomsManage() {
        return "/rooms/rooms-manage";
    }
}
