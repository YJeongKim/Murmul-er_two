package space.yjeong.web.contract;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.exception.ExpectedException;
import space.yjeong.service.room.RoomService;
import space.yjeong.service.user.UserService;
import space.yjeong.web.dto.salespost.SummaryResponseDto;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/contracts")
public class ContractController {

    private final UserService userService;
    private final RoomService roomService;
    private final HttpSession httpSession;

    @ApiOperation("UI : 계약서 선택 페이지")
    @GetMapping("/select")
    public String contractSelect(Model model,
                                 @RequestParam Long contractor,
                                 @RequestParam String type) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        try {
            Long contractUserId = userService.findUserById(contractor).getId();

            List<SummaryResponseDto> responses = roomService.readPostingRooms(user);

            model.addAttribute("rooms", responses);
            model.addAttribute("contractUser", contractUserId);
        } catch (ExpectedException e) {
            model.addAttribute("error", e.getMessage());
        }
        if (type == null) {
            return "redirect:/";
        } else if (type.equals("write")) {
            return "/contract/contract-select-writer";
        } else if (type.equals("register")) {
            return "/contract/contract-select-register";
        } else {
            return "redirect:/";
        }
    }
}
