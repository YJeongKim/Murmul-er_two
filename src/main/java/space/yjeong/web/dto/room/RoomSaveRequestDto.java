package space.yjeong.web.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.domain.salespost.HashTag;
import space.yjeong.domain.salespost.Image;
import space.yjeong.domain.room.*;
import space.yjeong.domain.salespost.Lease;
import space.yjeong.domain.salespost.MaintenanceOption;
import space.yjeong.domain.salespost.PeriodUnit;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.domain.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSaveRequestDto {
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

    public Room toRoomsEntity() {
        return Room.builder()
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

    public List<Image> toImagesEntity(List<String> imagesSrc, SalesPost salesPost) {
        List<Image> imageList = new ArrayList<>();
        for (String src : imagesSrc) {
            Image image = Image.builder()
                    .src(src)
                    .salesPost(salesPost)
                    .build();
            imageList.add(image);
        }
        return imageList;
    }

    public SalesPost toSalesPostEntity(User salesUser, Room room) {
        return SalesPost.builder()
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

    public List<HashTag> toHashTagEntity(SalesPost salesPost) {
        List<HashTag> hashTagList = new ArrayList<>();
        for (String tag : this.hashTags) {
            tag = tag.trim();
            if (!tag.equals("")) {
                HashTag hashTag = HashTag.builder()
                        .tag(tag)
                        .salesPost(salesPost)
                        .build();
                hashTagList.add(hashTag);
            }
        }
        return hashTagList;
    }
}
