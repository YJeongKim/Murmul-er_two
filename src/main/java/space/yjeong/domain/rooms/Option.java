package space.yjeong.domain.rooms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Option {
    REFRIGERATOR("OPTION_REFRIGERATOR", "냉장고"),
    AIR_CONDITIONER("OPTION_AIR_CONDITIONER", "에어컨"),
    WASHING_MACHINE("OPTION_WASHING_MACHINE", "세탁기"),
    TELEVISION("OPTION_TELEVISION", "TV"),
    GAS_STOVE("OPTION_GAS_STOVE", "가스레인지"),
    MICROWAVE("OPTION_MICROWAVE", "전자레인지"),
    INDUCTION("OPTION_INDUCTION", "인덕션"),
    CLOSET("OPTION_CLOSET", "옷장"),
    DESK("OPTION_DESK", "책상"),
    BED("OPTION_BED", "침대"),
    BIDET("OPTION_BIDET", "비데"),
    SHOE_CUPBOARD("OPTION_SHOE_CUPBOARD", "신발장"),
    SAFETY_DEVICE("OPTION_SAFETY_DEVICE", "현관문안전장치"),
    DIGITAL_DOOR_LOCK("OPTION_DIGITAL_DOOR_LOCK", "디지털도어락"),
    PARKING_LOT("OPTION_PARKING_LOT", "주차장"),
    ELEVATOR("OPTION_ELEVATOR", "엘리베이터"),
    PET_ACCEPTANCE("OPTION_PET_ACCEPTANCE", "반려동물 수용");

    private final String key;
    private final String title;
}
