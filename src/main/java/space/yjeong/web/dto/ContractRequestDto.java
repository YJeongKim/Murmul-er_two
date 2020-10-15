package space.yjeong.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequestDto {
    private Long roomId;
    private String jeondaeName;
    private String jeonchaName;
    private String buildingName;
    private String buildingArea;
    private String buildingStructure;
    private String roomType;
    private String jeondaeBubun;
    private String jeondaeUsage;
    private String lease;
    private String deposit;
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
}
