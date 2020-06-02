package space.yjeong.web.dto.rooms;

import lombok.Builder;
import lombok.Getter;
import space.yjeong.domain.rooms.PostStatus;

import java.time.LocalDateTime;

@Getter
public class RoomsReadResponseDto {
    private Long roomId;
    private Long salesPostId;
    private PostStatus postStatus;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Integer views;
    private String title;
    private String address;
    private String lease;
    private String leasePeriod;
    private Integer leaseDeposit;
    private Integer leaseFee;
    private Integer maintenanceFee;
    private String image;

    @Builder
    public RoomsReadResponseDto(Long roomId, Long salesPostId, PostStatus postStatus, LocalDateTime createDate,
                                LocalDateTime modifiedDate, Integer views, String title, String address, String lease,
                                String leasePeriod, Integer leaseDeposit, Integer leaseFee, Integer maintenanceFee, String image) {
        this.roomId = roomId;
        this.salesPostId = salesPostId;
        this.postStatus = postStatus;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.views = views;
        this.title = title;
        this.address = address;
        this.lease = lease;
        this.leasePeriod = leasePeriod;
        this.leaseDeposit = leaseDeposit;
        this.leaseFee = leaseFee;
        this.maintenanceFee = maintenanceFee;
        this.image = image;
    }
}
