package space.yjeong.web.dto.room;

import lombok.Builder;
import lombok.Getter;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.salespost.Image;
import space.yjeong.domain.salespost.SalesPost;

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

    @Builder
    public RoomResponseDto(Long roomId, Long salesPostId, String postStatus, LocalDate createDate,
                           LocalDate modifiedDate, Integer views, String title, String address, String lease,
                           String leasePeriod, String leaseDeposit, String leaseFee, String maintenanceFee, String image) {
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
                .leaseDeposit(convertMoneyToString(salesPost.getLeaseDeposit()))
                .leaseFee(convertMoneyToString(salesPost.getLeaseFee()))
                .maintenanceFee(convertMoneyToString(salesPost.getMaintenanceFee()))
                .image(image.getFilename())
                .build();
    }

    public static List<RoomResponseDto> listOf(List<SalesPost> salesPosts) {
        return salesPosts.stream()
                .map(RoomResponseDto::of)
                .collect(Collectors.toList());
    }

    private static String convertMoneyToString(int money) {
        int convertMoney = money / 10000;
        String result = "";

        if (convertMoney == 0) {
            result = "없음";
        } else if (convertMoney > 9999) {
            result += (convertMoney / 10000) + "억 ";
            if (convertMoney % 10000 != 0) {
                result += (convertMoney % 10000) + "만 원";
            }
        } else {
            result += convertMoney + "만 원";
        }
        return result;
    }
}
