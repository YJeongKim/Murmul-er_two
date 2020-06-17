package space.yjeong.web.dto.room;

import lombok.Builder;
import lombok.Getter;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.salespost.Image;
import space.yjeong.domain.salespost.PostStatus;
import space.yjeong.domain.salespost.SalesPost;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RoomResponseDto {
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
    public RoomResponseDto(Long roomId, Long salesPostId, PostStatus postStatus, LocalDateTime createDate,
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

    public static RoomResponseDto of(SalesPost salesPost) {
        Room room = salesPost.getRoom();
        Image image = salesPost.getImages().get(0);

        return RoomResponseDto.builder()
                .roomId(room.getId())
                .salesPostId(salesPost.getId())
                .postStatus(salesPost.getPostStatus())
                .createDate(salesPost.getCreateDate())
                .modifiedDate(salesPost.getModifiedDate())
                .views(salesPost.getViews())
                .title(salesPost.getTitle())
                .address(room.getRoadAddress())
                .lease(salesPost.getLease().getTitle())
                .leasePeriod(salesPost.getLeasePeriod() + salesPost.getPeriodUnit().getTitle())
                .leaseFee(salesPost.getLeaseFee())
                .maintenanceFee(salesPost.getMaintenanceFee())
                .image(image.getSrc())
                .build();
    }

    public static List<RoomResponseDto> listOf(List<SalesPost> salesPosts) {
        return salesPosts.stream()
                .map(RoomResponseDto::of)
                .collect(Collectors.toList());
    }
}
