package space.yjeong.web.dto.rooms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.domain.rooms.*;
import space.yjeong.domain.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomsUpdateRequestDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String jibunAddress;
    private String roadAddress;
    private String detailAddress;
    private Double area;
    private Integer floor;
    private RoomType roomType;
    private Heating heating;
    private List<Option> options;
    private String title;
    private String content;
    private List<String> hashTags;
    private Lease lease;
    private Integer leaseDeposit;
    private Integer leaseFee;
    private Integer leasePeriod;
    private PeriodUnit periodUnit;
    private Integer maintenanceFee;
    private List<MaintenanceOption> maintenanceOptions;
    private List<MultipartFile> images;
    private List<String> imagesUrl;

    public Rooms toRoomsEntity() {
        return Rooms.builder()
                .latitude(latitude)
                .longitude(longitude)
                .jibunAddress(jibunAddress)
                .roadAddress(roadAddress)
                .detailAddress(detailAddress)
                .area(area)
                .floor(floor)
                .roomType(roomType)
                .heating(heating)
                .options(options)
                .build();
    }

    public List<Images> toImagesEntity(List<String> imagesSrc, Rooms room) {
        List<Images> images = new ArrayList<>();
        for (String src : imagesSrc) {
            Images image = Images.builder()
                    .src(src)
                    .room(room)
                    .build();
            images.add(image);
        }
        return images;
    }

    public SalesPosts toSalesPostsEntity() {
        return SalesPosts.builder()
                .title(title)
                .content(content)
                .lease(lease)
                .leaseDeposit(leaseDeposit)
                .leaseFee(leaseFee)
                .maintenanceFee(maintenanceFee)
                .leasePeriod(leasePeriod)
                .periodUnit(periodUnit)
                .maintenanceOptions(maintenanceOptions)
                .salesUser(null)
                .room(null)
                .build();
    }

    public List<HashTags> toHashTagsEntity(SalesPosts salesPosts) {
        List<HashTags> hashTags = new ArrayList<>();
        for(String tag : this.hashTags) {
            HashTags hashTag = HashTags.builder()
                    .tag(tag)
                    .salesPosts(salesPosts)
                    .build();
            hashTags.add(hashTag);
        }
        return hashTags;
    }
}