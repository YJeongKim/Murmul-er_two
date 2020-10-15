package space.yjeong.web.contract;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.yjeong.service.contract.ContractService;
import space.yjeong.web.dto.ContractImageResponseDto;
import space.yjeong.web.dto.ContractRequestDto;
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
    private final HttpSession httpSession;

    @ApiOperation("계약서 작성")
    @PostMapping(value = "/write")
    public ResponseEntity contractWrite(@RequestBody ContractRequestDto requestDto) {
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
}
