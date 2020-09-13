package space.yjeong.web.dto.salespost;

import lombok.Builder;
import lombok.Getter;
import space.yjeong.domain.room.Option;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.salespost.HashTag;
import space.yjeong.domain.salespost.Image;
import space.yjeong.domain.salespost.MaintenanceOption;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.util.ViewHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DetailResponseDto {
    private Long roomId;
    private Long salesPostId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private String buildingName;
    private Double area;
    private String floor;
    private String roomType;
    private String heating;
    private List<String> options;
    private LocalDate writeDate;
    private String title;
    private String content;
    private String lease;
    private String leasePeriod;
    private String leaseDeposit;
    private String leaseFee;
    private String maintenanceFee;
    private List<String> maintenanceOptions;
    private List<String> hashTags;
    private List<String> images;

    @Builder
    public DetailResponseDto(Long roomId, Long salesPostId, BigDecimal latitude, BigDecimal longitude,
                             String roadAddress, String jibunAddress, String detailAddress, String buildingName,
                             Double area, String floor, String roomType, String heating, List<String> options,
                             LocalDate writeDate, String title, String content, String lease, String leasePeriod,
                             String leaseDeposit, String leaseFee, String maintenanceFee, List<String> maintenanceOptions,
                             List<String> hashTags, List<String> images) {
        this.roomId = roomId;
        this.salesPostId = salesPostId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.detailAddress = detailAddress;
        this.buildingName = buildingName;
        this.area = area;
        this.floor = floor;
        this.roomType = roomType;
        this.heating = heating;
        this.options = options;
        this.writeDate = writeDate;
        this.title = title;
        this.content = content;
        this.lease = lease;
        this.leasePeriod = leasePeriod;
        this.leaseDeposit = leaseDeposit;
        this.leaseFee = leaseFee;
        this.maintenanceFee = maintenanceFee;
        this.maintenanceOptions = maintenanceOptions;
        this.hashTags = hashTags;
        this.images = images;
    }

    public static DetailResponseDto of(SalesPost salesPost) {
        Room room = salesPost.getRoom();

        int floorInt = room.getFloor();
        String floor;
        if (floorInt < 0) floor = "지하 " + Math.abs(floorInt) + "층";
        else floor = floorInt + "층";

        List<String> options = new ArrayList<>();
        for (Option op : room.getOptions()) {
            options.add(op.getTitle());
        }

        List<String> maintenanceOptions = new ArrayList<>();
        for (MaintenanceOption mop : salesPost.getMaintenanceOptions()) {
            maintenanceOptions.add(mop.getTitle());
        }

        List<String> hashTags = new ArrayList<>();
        for (HashTag hashTag : salesPost.getHashTags()) {
            hashTags.add(hashTag.getTag());
        }

        List<String> images = new ArrayList<>();
        for (Image image : salesPost.getImages()) {
            images.add(image.getFilename());
        }

        return DetailResponseDto.builder()
                .roomId(room.getId())
                .salesPostId(salesPost.getId())
                .latitude(room.getLatitude())
                .longitude(room.getLongitude())
                .roadAddress(room.getRoadAddress())
                .jibunAddress(room.getJibunAddress())
                .detailAddress(room.getDetailAddress())
                .buildingName(room.getBuildingName())
                .area(room.getArea())
                .floor(floor)
                .roomType(room.getRoomType().getTitle())
                .heating(room.getHeating().getTitle())
                .options(options)
                .writeDate(salesPost.getCreateDate().toLocalDate())
                .title(salesPost.getTitle())
                .content(salesPost.getContent())
                .lease(salesPost.getLease().getTitle())
                .leasePeriod(salesPost.getLeasePeriod() + salesPost.getPeriodUnit().getTitle())
                .leaseDeposit(ViewHelper.convertMoneyToString(salesPost.getLeaseDeposit()))
                .leaseFee(ViewHelper.convertMoneyToString(salesPost.getLeaseFee()))
                .maintenanceFee(ViewHelper.convertMoneyToString(salesPost.getMaintenanceFee()))
                .maintenanceOptions(maintenanceOptions)
                .hashTags(hashTags)
                .images(images)
                .build();
    }

    public static List<DetailResponseDto> listOf(List<SalesPost> salesPosts) {
        return salesPosts.stream()
                .map(DetailResponseDto::of)
                .collect(Collectors.toList());
    }
}
