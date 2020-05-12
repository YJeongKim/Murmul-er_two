package space.yjeong.domain.rooms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    POSTING("POSTSTATUS_POSTING", "게시중"),
    POSTING_END("POSTSTATUS_POSTING_END", "게시종료"),
    POSTING_BAN("POSTSTATUS_POSTING_BAN", "게시금지"),
    DEAL_COMPLETED("POSTSTATUS_DEAL_COMPLETED", "거래완료");

    private final String key;
    private final String title;
}
