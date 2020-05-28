package space.yjeong.domain.rooms;

import lombok.Builder;
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

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<MaintenanceOption> maintenanceOptions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User salesUser;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    @OneToMany(mappedBy = "salesPosts")
    private List<HashTags> hashTags;

    @Builder
    public SalesPosts(String title, String content, Lease lease, Integer leaseDeposit, Integer leaseFee, Integer maintenanceFee, Integer leasePeriod, PeriodUnit periodUnit, List<MaintenanceOption> maintenanceOptions, User salesUser, Rooms room) {
        this.title = title;
        this.content = content;
        this.views = 0;
        this.postStatus = PostStatus.POSTING;
        this.lease = lease;
        this.leaseDeposit = leaseDeposit;
        this.leaseFee = leaseFee;
        this.maintenanceFee = maintenanceFee;
        this.leasePeriod = leasePeriod;
        this.periodUnit = periodUnit;
        this.maintenanceOptions = maintenanceOptions;
        this.salesUser = salesUser;
        this.room = room;
    }

    public void setHashTags(List<HashTags> hashTags) {
        this.hashTags = hashTags;
    }

    public void update(SalesPosts salesPost) {
        this.title = salesPost.getTitle();
        this.content = salesPost.getContent();
        this.lease = salesPost.getLease();
        this.leaseDeposit = salesPost.getLeaseDeposit();
        this.leaseFee = salesPost.getLeaseFee();
        this.maintenanceFee = salesPost.getMaintenanceFee();
        this.leasePeriod = salesPost.getLeasePeriod();
        this.periodUnit = salesPost.getPeriodUnit();
        this.maintenanceOptions = salesPost.getMaintenanceOptions();
    }

    public void updatePostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }
}
