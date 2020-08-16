package space.yjeong.web.dto.room;

import lombok.Builder;
import lombok.Getter;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.salespost.Image;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.util.ViewHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Getter
public class RoomResponseDto {
    private Long roomId;
    private Long salesPostId;
    private String postStatus;
    private LocalDate createDate;
    private LocalDate modifiedDate;
    private Integer views;
    private String title;
    private String address;
    private String lease;
    private String leasePeriod;
    private String leaseDeposit;
    private String leaseFee;
    private String maintenanceFee;
    private String image;
    private Integer index;
    private Boolean isLease;

    @Builder
    public RoomResponseDto(Long roomId, Long salesPostId, String postStatus, LocalDate createDate,
                           LocalDate modifiedDate, Integer views, String title, String address, String lease,
                           String leasePeriod, String leaseDeposit, String leaseFee, String maintenanceFee, String image,
                           Integer index, Boolean isLease) {
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
        this.index = index;
        this.isLease = isLease;
    }

    public static RoomResponseDto of(SalesPost salesPost) {
        Room room = salesPost.getRoom();
        Image image;
        if (salesPost.getImages().size() == 0) {
            image = Image.builder()
                    .filename("")
                    .src("")
                    .salesPost(salesPost)
                    .build();
        } else {
            image = salesPost.getImages().get(0);
        }
        boolean isLease = salesPost.getLease().getTitle().equals("전세") ? true : false;

        return RoomResponseDto.builder()
                .roomId(room.getId())
                .salesPostId(salesPost.getId())
                .postStatus(salesPost.getPostStatus().getTitle())
                .createDate(salesPost.getCreateDate().toLocalDate())
                .modifiedDate(salesPost.getModifiedDate().toLocalDate())
                .views(salesPost.getViews())
                .title(salesPost.getTitle())
                .address(room.getRoadAddress())
                .lease(salesPost.getLease().getTitle())
                .leasePeriod(salesPost.getLeasePeriod() + salesPost.getPeriodUnit().getTitle())
                .leaseDeposit(ViewHelper.convertMoneyToString(salesPost.getLeaseDeposit()))
                .leaseFee(ViewHelper.convertMoneyToString(salesPost.getLeaseFee()))
                .maintenanceFee(ViewHelper.convertMoneyToString(salesPost.getMaintenanceFee()))
                .image(image.getFilename())
                .index(++ViewHelper.count)
                .isLease(isLease)
                .build();
    }

    public static List<RoomResponseDto> listOf(List<SalesPost> salesPosts) {
        List<RoomResponseDto> responseDtos = salesPosts.stream()
                .map(RoomResponseDto::of)
                .collect(Collectors.toList());
        ViewHelper.count = 0;
        return responseDtos;
    }
}
