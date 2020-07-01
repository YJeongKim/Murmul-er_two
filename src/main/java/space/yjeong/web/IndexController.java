package space.yjeong.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.service.PostsService;
import space.yjeong.web.dto.posts.PostsResponseDto;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getNickname());
            if (user.getPicture() != null) {
                model.addAttribute("userPicture", user.getPicture());
            }
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "/posts/posts-save";
}

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "/posts/posts-update";
    }
}
