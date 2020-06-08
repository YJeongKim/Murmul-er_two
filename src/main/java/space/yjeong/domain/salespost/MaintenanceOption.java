package space.yjeong.domain.salespost;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaintenanceOption {
    GAS_TAX("MAINTENANCE_GAS_TAX", "가스세"),
    WATER_TAX("MAINTENANCE_WATER_TAX", "수도세"),
    ELECTRIC_TAX("MAINTENANCE_ELECTRIC_TAX", "전기세"),
    INTERNET_CHARGE("MAINTENANCE_INTERNET_CHARGE", "인터넷요금"),
    TV_LICENSE_FEE("MAINTENANCE_TV_LICENSE_FEE", "TV수신료"),
    CLEANING_COST("MAINTENANCE_CLEANING_COST", "청소비");

    private final String key;
    private final String title;
}
