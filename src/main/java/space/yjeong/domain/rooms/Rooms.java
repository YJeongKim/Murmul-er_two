package space.yjeong.domain.rooms;

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

    @OneToMany(mappedBy = "room")
    private List<Options> options;

    @OneToMany(mappedBy = "room")
    private List<Images> images;

    @OneToOne(mappedBy = "room")
    private SalesPosts salesPosts;
}
