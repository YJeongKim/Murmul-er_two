package space.yjeong.domain.room;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomType {
    ONEROOM("ROOMTYPE_ONEROOM", "원룸"),
    TWOROOM("ROOMTYPE_TWOROOM", "투룸"),
    HOUSING("ROOMTYPE_HOUSING", "주택"),
    VILLA("ROOMTYPE_VILLA", "빌라"),
    OFFICETEL("ROOMTYPE_OFFICETEL", "오피스텔"),
    APARTMENT("ROOMTYPE_APARTMENT", "아파트");

    private final String key;
    private final String title;
}
