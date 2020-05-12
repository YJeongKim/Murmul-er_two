package space.yjeong.domain.rooms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Heating {
    DISTRICT("HEATING_DISTRICT", "지역난방"),
    INDIVIDUAL("HEATING_INDIVIDUAL", "개별난방"),
    CENTRAL("HEATING_CENTRAL", "중앙난방");

    private final String key;
    private final String title;
}
