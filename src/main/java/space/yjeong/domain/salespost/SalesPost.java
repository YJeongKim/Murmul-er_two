package space.yjeong.domain.salespost;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import space.yjeong.domain.BaseTimeEntity;
import space.yjeong.domain.hashtag.HashTag;
import space.yjeong.domain.image.Image;
import space.yjeong.domain.room.*;
import space.yjeong.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "sales_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesPost extends BaseTimeEntity {
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
    private Room room;

    @OneToMany(mappedBy = "salesPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTag> hashTags;

    @OneToMany(mappedBy = "salesPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @Builder
    public SalesPost(String title, String content, Lease lease, Integer leaseDeposit, Integer leaseFee, Integer maintenanceFee, Integer leasePeriod, PeriodUnit periodUnit, List<MaintenanceOption> maintenanceOptions, User salesUser, Room room) {
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

    public void setHashTags(List<HashTag> hashTags) {
        this.hashTags = hashTags;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void update(SalesPost salesPost) {
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
