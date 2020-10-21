package space.yjeong.web.contract;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.contract.Contract;
import space.yjeong.service.contract.ContractImageService;
import space.yjeong.service.contract.ContractService;
import space.yjeong.web.dto.MessageResponseDto;
import space.yjeong.web.dto.contract.ContractImageRequestDto;
import space.yjeong.web.dto.contract.ContractImageResponseDto;
import space.yjeong.web.dto.contract.ContractRequestDto;
import space.yjeong.web.dto.salespost.DetailResponseDto;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contracts")
public class ContractApiController {

    private final ContractService contractService;
    private final ContractImageService contractImageService;
    private final HttpSession httpSession;

    @ApiOperation("계약서 작성")
    @PostMapping(value = "/write")
    public ResponseEntity contractWrite(@RequestBody ContractImageRequestDto requestDto) {
        Map<String, String> roomInfo = new HashMap<>();

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] today = format.format(date).split("-");
        roomInfo.put("todayYear", today[0]);
        roomInfo.put("todayMonth", today[1]);
        roomInfo.put("todayDay", today[2]);

        DetailResponseDto detailResponseDto = contractService.readDetailRoom(requestDto.getRoomId());

        String address = detailResponseDto.getRoadAddress();
        String area = detailResponseDto.getArea() + "";
        String maintenanceFee = detailResponseDto.getMaintenanceFee() + "";
        String maintenanceOptions = detailResponseDto.getMaintenanceOptions().toString();
        String options = detailResponseDto.getOptions().toString();

        roomInfo.put("address", address);
        roomInfo.put("jeondaeArea", area);
        roomInfo.put("maintenanceFee", maintenanceFee);
        roomInfo.put("maintenanceOptions", maintenanceOptions);
        roomInfo.put("options", options);

        ContractImageResponseDto response = ContractImageResponseDto.of(requestDto, roomInfo);

        httpSession.setAttribute("contract", response);

        return ResponseEntity.ok().build();
    }

    @ApiOperation("계약서 등록")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity contractWrite(@RequestParam MultipartFile contractForm,
                                        @RequestParam Long roomId,
                                        @RequestParam Long sublessor,
                                        @RequestParam Long sublessee,
                                        @RequestParam Integer leaseDeposit,
                                        @RequestParam Integer leaseFee,
                                        @RequestParam String lease,
                                        @RequestParam String stayFrom,
                                        @RequestParam String stayTo) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        ContractRequestDto contractRequestDto = new ContractRequestDto(roomId, sublessor, sublessee,
                leaseDeposit, leaseFee, lease, stayFrom, stayTo);

        Contract contract = contractService.saveContract(contractRequestDto, user);

        String contractImage = contractImageService.saveContractImage(contractForm, contract.getId(), sublessor, sublessee);

        contract.updateContractForm(contractImage);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDto("등록이 완료되었습니다.", contract.getId()));
    }
}
