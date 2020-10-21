package space.yjeong.web.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequestDto {
    private Long roomId;
    private Long sublessor;
    private Long sublessee;
    private Integer leaseDeposit;
    private Integer leaseFee;
    private String lease;
    private String stayFrom;
    private String stayTo;
}
