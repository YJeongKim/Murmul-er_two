package space.yjeong.domain.rooms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;

    @Column(nullable = false)
    private String jibunAddress;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private Integer floor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Heating heating;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Option> options;

    @OneToMany(mappedBy = "room")
    private List<Images> images;

    @OneToOne(mappedBy = "room")
    private SalesPosts salesPosts;

    @Builder
    public Rooms(BigDecimal latitude, BigDecimal longitude, String jibunAddress, String roadAddress, String detailAddress, Double area, Integer floor, RoomType roomType, Heating heating, List<Option> options) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.jibunAddress = jibunAddress;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.area = area;
        this.floor = floor;
        this.roomType = roomType;
        this.heating = heating;
        this.options = options;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public void setSalesPosts(SalesPosts salesPosts) {
        this.salesPosts = salesPosts;
    }
}
