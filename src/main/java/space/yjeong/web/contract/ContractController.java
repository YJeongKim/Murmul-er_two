package space.yjeong.web.contract;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.user.User;
import space.yjeong.exception.ExpectedException;
import space.yjeong.service.contract.ContractService;
import space.yjeong.service.user.UserService;
import space.yjeong.web.dto.contract.ContractImageResponseDto;
import space.yjeong.web.dto.contract.ContractResponseDto;
import space.yjeong.web.dto.salespost.SummaryResponseDto;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;
    private final UserService userService;
    private final HttpSession httpSession;

    @ApiOperation("UI : 계약서 선택 페이지")
    @GetMapping("/select")
    public String contractSelect(Model model,
                                 @RequestParam Long contractor,
                                 @RequestParam String type) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        try {
            Long contractUserId = userService.findUserById(contractor).getId();

            List<SummaryResponseDto> responses = contractService.readPostingRooms(user);

            model.addAttribute("rooms", responses);
            model.addAttribute("contractUser", contractUserId);
        } catch (ExpectedException e) {
            model.addAttribute("error", e.getMessage());
        }
        if (type == null) {
            return "redirect:/";
        } else if (type.equals("write")) {
            return "/contract/contract-select-write";
        } else if (type.equals("register")) {
            return "/contract/contract-select-register";
        } else {
            return "redirect:/";
        }
    }

    @ApiOperation("UI : 계약서 작성 페이지")
    @GetMapping("/write")
    public String contractWrite(Model model,
                                 @RequestParam Long contractor,
                                 @RequestParam Long roomId) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        User sublessor = userService.findUserBySessionUser(user);
        User sublessee = userService.findUserById(contractor);

        ContractResponseDto response = contractService.readContractRoom(roomId);

        model.addAttribute("sublessor", sublessor.getName());
        model.addAttribute("sublessee", sublessee.getName());
        model.addAttribute("room", response);

        return "/contract/contract-write";
    }

    @ApiOperation("UI : 계약서 등록 페이지")
    @GetMapping("/register")
    public String contractRegister(Model model,
                                 @RequestParam Long contractor,
                                 @RequestParam Long roomId) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        User sublessor = userService.findUserBySessionUser(user);
        User sublessee = userService.findUserById(contractor);

        ContractResponseDto response = contractService.readContractRoom(roomId);

        model.addAttribute("sublessor", sublessor.getId());
        model.addAttribute("sublessee", sublessee.getId());
        model.addAttribute("room", response);

        return "/contract/contract-register";
    }

    @ApiOperation("UI : 계약서 이미지 페이지")
    @GetMapping(value = "/show")
    public String contractImageShow(Model model) {
        ContractImageResponseDto contract = (ContractImageResponseDto) httpSession.getAttribute("contract");
        httpSession.removeAttribute("contract");

        model.addAttribute("contract", contract);

        return "/contract/contract-image";
    }
}
