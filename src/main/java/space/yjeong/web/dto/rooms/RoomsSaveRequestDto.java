package space.yjeong.web.dto.rooms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.domain.rooms.*;
import space.yjeong.domain.user.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomsSaveRequestDto {
    private RoomType roomType;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String jibunAddress;
    private String roadAddress;
    private String detailAddress;
    private Double area;
    private Integer floor;
    private Heating heating;
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
    private List<Option> options;
    private List<MultipartFile> images;

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

    public SalesPosts toSalesPostsEntity(User salesUser, Rooms room) {
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
                .salesUser(salesUser)
                .room(room)
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