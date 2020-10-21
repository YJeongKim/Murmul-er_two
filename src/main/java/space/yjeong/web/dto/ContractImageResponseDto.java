package space.yjeong.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class ContractImageResponseDto {
    private Long roomId;
    private String address;
    private String jeondaeName;
    private String jeonchaName;
    private String buildingName;
    private String jeondaeArea;
    private String buildingArea;
    private String buildingStructure;
    private String roomType;
    private String jeondaeBubun;
    private String jeondaeUsage;
    private String lease;
    private String deposit;
    private String maintenanceFee;
    private String maintenanceOptions;
    private String options;
    private String contractPayment;
    private String middlePayment;
    private String mdPayYear;
    private String mdPayMonth;
    private String mdPayDay;
    private String remainderPayment;
    private String remainderYear;
    private String remainderMonth;
    private String remainderDay;
    private String monthlyCost;
    private String mcPayDayS;
    private String mcType;
    private String fromYearS;
    private String fromMonthS;
    private String fromDayS;
    private String toYearS;
    private String toMonthS;
    private String toDayS;
    private String depositL;
    private String monthlyCostL;
    private String lessorName;
    private String fromYearL;
    private String fromMonthL;
    private String fromDayL;
    private String toYearL;
    private String toMonthL;
    private String toDayL;
    private String todayYear;
    private String todayMonth;
    private String todayDay;

    @Builder
    public ContractImageResponseDto(Long roomId, String address, String jeondaeName, String jeonchaName, String buildingName,
                                    String jeondaeArea, String buildingArea, String buildingStructure, String roomType,
                                    String jeondaeBubun, String jeondaeUsage, String lease, String deposit,
                                    String maintenanceFee, String maintenanceOptions, String options,
                                    String contractPayment, String middlePayment, String mdPayYear, String mdPayMonth,
                                    String mdPayDay, String remainderPayment, String remainderYear,
                                    String remainderMonth, String remainderDay, String monthlyCost, String mcPayDayS,
                                    String mcType, String fromYearS, String fromMonthS, String fromDayS, String toYearS,
                                    String toMonthS, String toDayS, String depositL, String monthlyCostL,
                                    String lessorName, String fromYearL, String fromMonthL, String fromDayL,
                                    String toYearL, String toMonthL, String toDayL, String todayYear,
                                    String todayMonth, String todayDay) {
        this.roomId = roomId;
        this.address = address;
        this.jeondaeName = jeondaeName;
        this.jeonchaName = jeonchaName;
        this.buildingName = buildingName;
        this.jeondaeArea = jeondaeArea;
        this.buildingArea = buildingArea;
        this.buildingStructure = buildingStructure;
        this.roomType = roomType;
        this.jeondaeBubun = jeondaeBubun;
        this.jeondaeUsage = jeondaeUsage;
        this.lease = lease;
        this.deposit = deposit;
        this.maintenanceFee = maintenanceFee;
        this.maintenanceOptions = maintenanceOptions;
        this.options = options;
        this.contractPayment = contractPayment;
        this.middlePayment = middlePayment;
        this.mdPayYear = mdPayYear;
        this.mdPayMonth = mdPayMonth;
        this.mdPayDay = mdPayDay;
        this.remainderPayment = remainderPayment;
        this.remainderYear = remainderYear;
        this.remainderMonth = remainderMonth;
        this.remainderDay = remainderDay;
        this.monthlyCost = monthlyCost;
        this.mcPayDayS = mcPayDayS;
        this.mcType = mcType;
        this.fromYearS = fromYearS;
        this.fromMonthS = fromMonthS;
        this.fromDayS = fromDayS;
        this.toYearS = toYearS;
        this.toMonthS = toMonthS;
        this.toDayS = toDayS;
        this.depositL = depositL;
        this.monthlyCostL = monthlyCostL;
        this.lessorName = lessorName;
        this.fromYearL = fromYearL;
        this.fromMonthL = fromMonthL;
        this.fromDayL = fromDayL;
        this.toYearL = toYearL;
        this.toMonthL = toMonthL;
        this.toDayL = toDayL;
        this.todayYear = todayYear;
        this.todayMonth = todayMonth;
        this.todayDay = todayDay;
    }

    public static ContractImageResponseDto of(ContractRequestDto requestDto, Map<String, String> roomInfo) {
        return ContractImageResponseDto.builder()
                .roomId(requestDto.getRoomId())
                .address(roomInfo.get("address"))
                .jeondaeName(requestDto.getJeondaeName())
                .jeonchaName(requestDto.getJeonchaName())
                .buildingName(requestDto.getBuildingName())
                .buildingArea(requestDto.getBuildingName())
                .jeondaeArea(roomInfo.get("jeondaeArea"))
                .buildingArea(requestDto.getBuildingArea())
                .buildingStructure(requestDto.getBuildingStructure())
                .roomType(requestDto.getRoomType())
                .jeondaeBubun(requestDto.getJeondaeBubun())
                .jeondaeUsage(requestDto.getJeondaeUsage())
                .lease(requestDto.getLease())
                .deposit(requestDto.getDeposit())
                .maintenanceFee(roomInfo.get("maintenanceFee"))
                .maintenanceOptions(roomInfo.get("maintenanceOptions"))
                .options(roomInfo.get("options"))
                .contractPayment(requestDto.getContractPayment())
                .middlePayment(requestDto.getMiddlePayment())
                .mdPayYear(requestDto.getMdPayYear())
                .mdPayMonth(requestDto.getMdPayMonth())
                .mdPayDay(requestDto.getMdPayDay())
                .remainderPayment(requestDto.getRemainderPayment())
                .remainderYear(requestDto.getRemainderYear())
                .remainderMonth(requestDto.getRemainderMonth())
                .remainderDay(requestDto.getRemainderDay())
                .monthlyCost(requestDto.getMonthlyCost())
                .mcPayDayS(requestDto.getMcPayDayS())
                .mcType(requestDto.getMcType())
                .fromYearS(requestDto.getFromYearS())
                .fromMonthS(requestDto.getFromMonthS())
                .fromDayS(requestDto.getFromDayS())
                .toYearS(requestDto.getToYearS())
                .toMonthS(requestDto.getToMonthS())
                .toDayS(requestDto.getToDayS())
                .depositL(requestDto.getDepositL())
                .monthlyCostL(requestDto.getMonthlyCostL())
                .lessorName(requestDto.getLessorName())
                .fromYearL(requestDto.getFromYearL())
                .fromMonthL(requestDto.getFromMonthL())
                .fromDayL(requestDto.getFromDayL())
                .toYearL(requestDto.getToYearL())
                .toMonthL(requestDto.getToMonthL())
                .toDayL(requestDto.getToDayL())
                .todayYear(roomInfo.get("todayYear"))
                .todayMonth(roomInfo.get("todayMonth"))
                .todayDay(roomInfo.get("todayDay"))
                .build();
    }
}
