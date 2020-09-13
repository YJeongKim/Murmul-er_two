package space.yjeong.domain.room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.yjeong.domain.salespost.SalesPost;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Entity
@Table(name = "rooms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
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

    @Column
    private String buildingName;

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

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private SalesPost salesPost;

    @Builder
    public Room(BigDecimal latitude, BigDecimal longitude, String jibunAddress, String roadAddress, String detailAddress, String buildingName, Double area, Integer floor, RoomType roomType, Heating heating, List<Option> options) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.jibunAddress = jibunAddress;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.buildingName = buildingName;
        this.area = area;
        this.floor = floor;
        this.roomType = roomType;
        this.heating = heating;
        this.options = options;
    }

    public void setSalesPost(SalesPost salesPost) {
        this.salesPost = salesPost;
    }

    public void update(Room room) {
        this.latitude = room.getLatitude();
        this.longitude = room.getLongitude();
        this.jibunAddress = room.getJibunAddress();
        this.roadAddress = room.getRoadAddress();
        this.detailAddress = room.getRoadAddress();
        this.buildingName = room.getBuildingName();
        this.area = room.getArea();
        this.floor = room.getFloor();
        this.roomType = room.getRoomType();
        this.heating = room.getHeating();
        this.options = room.getOptions();
    }
}
