package space.yjeong.web.room;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.service.room.RoomService;
import space.yjeong.web.dto.room.RoomResponseDto;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final HttpSession httpSession;

    @ApiOperation("UI : 방 관리 페이지")
    @GetMapping
    public String roomsManage(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getNickname());
            if (user.getPicture() != null) {
                model.addAttribute("userPicture", user.getPicture());
            }
            List<RoomResponseDto> responses = roomService.readRooms(user);
            model.addAttribute("rooms", responses);

            return "/room/room-manage";
        } else return "redirect:/";
    }

    @ApiOperation("UI : 방 등록 페이지")
    @GetMapping("/register")
    public String roomRegister(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getNickname());
            if (user.getPicture() != null) {
                model.addAttribute("userPicture", user.getPicture());
            }
            return "/room/room-save";
        } else return "redirect:/";
    }

    @ApiOperation("UI : 방 수정 페이지")
    @GetMapping("/update/{roomId}")
    public String roomUpdate(Model model, @PathVariable Long roomId) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getNickname());
            if (user.getPicture() != null) {
                model.addAttribute("userPicture", user.getPicture());
            }
            return "/room/room-update";
        } else return "redirect:/";
    }
}
