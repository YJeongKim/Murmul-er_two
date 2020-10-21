package space.yjeong.service.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.contract.Contract;
import space.yjeong.domain.contract.ContractRepository;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.domain.user.User;
import space.yjeong.exception.UnauthorizedException;
import space.yjeong.service.salespost.SalesPostService;
import space.yjeong.service.user.UserService;
import space.yjeong.web.dto.contract.ContractRequestDto;
import space.yjeong.web.dto.contract.ContractResponseDto;
import space.yjeong.web.dto.salespost.DetailResponseDto;
import space.yjeong.web.dto.salespost.SummaryResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContractService {

    private final ContractRepository contractRepository;

    private final UserService userService;
    private final SalesPostService salesPostService;

    @Transactional(readOnly = true)
    public List<SummaryResponseDto> readPostingRooms(SessionUser sessionUser) {
        User user = userService.findUserBySessionUser(sessionUser);

        List<SalesPost> salesPosts = salesPostService.findPostingSalesPostsByUser(user.getId());

        List<SummaryResponseDto> summaryResponseDtoList = SummaryResponseDto.listOf(salesPosts);

        return summaryResponseDtoList;
    }

    @Transactional(readOnly = true)
    public ContractResponseDto readContractRoom(Long roomId) {
        SalesPost salesPost = salesPostService.findSalesPostByRoom(roomId);

        ContractResponseDto contractResponseDto = ContractResponseDto.of(salesPost);

        return contractResponseDto;
    }

    @Transactional(readOnly = true)
    public DetailResponseDto readDetailRoom(Long roomId) {
        SalesPost salesPost = salesPostService.findSalesPostByRoom(roomId);

        DetailResponseDto detailResponseDto = DetailResponseDto.of(salesPost);

        return detailResponseDto;
    }

    @Transactional
    public Contract saveContract(ContractRequestDto responseDto, SessionUser sessionUser) {
        User user = userService.findUserBySessionUser(sessionUser);
        SalesPost salesPost = salesPostService.findSalesPostByRoom(responseDto.getRoomId());
        Room room = salesPost.getRoom();

        if(!user.getId().equals(salesPost.getSalesUser().getId())) throw new UnauthorizedException();

        if(!user.getId().equals(responseDto.getSublessor())) throw new UnauthorizedException();

        User sublessee = userService.findUserById(responseDto.getSublessee());

        Contract contract = Contract.builder()
                .roomId(room.getId())
                .sublessorId(user.getId())
                .sublesseeId(sublessee.getId())
                .contractForm("temp")
                .leaseDeposit(responseDto.getLeaseDeposit())
                .leaseFee(responseDto.getLeaseFee())
                .stayFrom(responseDto.getStayFrom())
                .stayTo(responseDto.getStayTo())
                .address(room.getRoadAddress() + " " +room.getDetailAddress())
                .lease(responseDto.getLease())
                .build();

        return contractRepository.save(contract);
    }
}
