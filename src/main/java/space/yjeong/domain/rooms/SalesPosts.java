package space.yjeong.domain.rooms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import space.yjeong.domain.BaseTimeEntity;
import space.yjeong.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class SalesPosts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer views;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus postStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Lease lease;

    @Column(nullable = false)
    private Integer leaseDeposit;

    @Column(nullable = true)
    private Integer leaseFee;

    @Column(nullable = false)
    private Integer maintenanceFee;

    @Column(nullable = false)
    private Integer leasePeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeriodUnit periodUnit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User salesUser;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    @OneToMany(mappedBy = "salesPosts")
    private List<MaintenanceOptions> maintenanceOptions;

    @OneToMany(mappedBy = "salesPosts")
    private List<HashTags> hashTags;
}
