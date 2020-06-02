package space.yjeong.domain.rooms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Lease {
    JEONSE("LEASE_JEONSE", "전세"),
    MONTHLY_RENT("LEASE_MONTHLY_RENT", "월세"),
    SHORT_TERM("LEASE_SHORT_TERM", "단기");

    private final String key;
    private final String title;
}
