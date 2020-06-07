package space.yjeong.domain.room;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PeriodUnit {
    YEAR("PERIOD_YEAR", "년"),
    MONTH("PERIOD_MONTH", "개월"),
    WEEK("PERIOD_WEEK", "주");

    private final String key;
    private final String title;
}
