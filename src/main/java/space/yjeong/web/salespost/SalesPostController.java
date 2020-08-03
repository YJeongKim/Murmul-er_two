package space.yjeong.web.salespost;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.room.Option;
import space.yjeong.util.EnumValue;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/salesposts")
public class SalesPostController {

    private final HttpSession httpSession;

    @ApiOperation("UI : 방 검색 페이지")
    @GetMapping
    public String salesPostsSearch(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getNickname());
            if (user.getPicture() != null) {
                model.addAttribute("userPicture", user.getPicture());
            }
        }

        List<EnumValue> enumValues = EnumValue.listOf(Option.class.getEnumConstants());

        model.addAttribute("options", enumValues);

        return "/salespost/room-search";
    }
}
