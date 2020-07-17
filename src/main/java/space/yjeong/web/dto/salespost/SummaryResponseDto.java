package space.yjeong.web.dto.salespost;

import lombok.Builder;
import lombok.Getter;
import space.yjeong.domain.room.Option;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.salespost.Image;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.util.ViewHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SummaryResponseDto {
    private Long roomId;
    private Long salesPostId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private String roomType;
    private List<String> options;
    private String title;
    private String lease;
    private String leasePeriod;
    private String leaseDeposit;
    private String leaseFee;
    private String image;

    @Builder
    public SummaryResponseDto(Long roomId, Long salesPostId, BigDecimal latitude, BigDecimal longitude,
                              String address, String roomType, List<String> options, String title,
                              String lease, String leasePeriod, String leaseDeposit, String leaseFee,
                              String image) {
        this.roomId = roomId;
        this.salesPostId = salesPostId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.roomType = roomType;
        this.options = options;
        this.title = title;
        this.lease = lease;
        this.leasePeriod = leasePeriod;
        this.leaseDeposit = leaseDeposit;
        this.leaseFee = leaseFee;
        this.image = image;
    }

    public static SummaryResponseDto of(SalesPost salesPost) {
        Room room = salesPost.getRoom();

        List<String> options = new ArrayList<>();

        for (Option op : room.getOptions()) {
            options.add(op.getTitle());
        }

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

        return SummaryResponseDto.builder()
                .roomId(room.getId())
                .salesPostId(salesPost.getId())
                .latitude(room.getLatitude())
                .longitude(room.getLongitude())
                .address(room.getRoadAddress())
                .roomType(room.getRoomType().getTitle())
                .options(options)
                .title(salesPost.getTitle())
                .lease(salesPost.getLease().getTitle())
                .leasePeriod(salesPost.getLeasePeriod() + salesPost.getPeriodUnit().getTitle())
                .leaseDeposit(ViewHelper.convertMoneyToString(salesPost.getLeaseDeposit()))
                .leaseFee(ViewHelper.convertMoneyToString(salesPost.getLeaseFee()))
                .image(image.getFilename())
                .build();
    }

    public static List<SummaryResponseDto> listOf(List<SalesPost> salesPosts) {
        return salesPosts.stream()
                .map(SummaryResponseDto::of)
                .collect(Collectors.toList());
    }
}
