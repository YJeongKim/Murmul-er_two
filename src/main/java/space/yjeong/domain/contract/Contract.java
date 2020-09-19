package space.yjeong.domain.contract;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.yjeong.domain.BaseTimeEntity;
import space.yjeong.domain.salespost.Lease;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "contracts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    /* 전대인(세를 주는 사람, 임차인) */
    @Column(nullable = false)
    private Long sublessorId;

    /* 전차인(세를 얻는 사람) */
    @Column(nullable = false)
    private Long sublesseeId;

    @Column(nullable = false)
    private String contractForm;

    @Column(nullable = false)
    private LocalDate contractDate;

    @Column(nullable = false)
    private Integer leaseDeposit;

    @Column(nullable = false)
    private Integer leaseFee;

    @Column(nullable = false)
    private String stayFrom;

    @Column(nullable = false)
    private String stayTo;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Lease lease;

    @Builder
    public Contract(Long roomId, Long sublessorId, Long sublesseeId, String contractForm, LocalDate contractDate,
                    Integer leaseDeposit, Integer leaseFee, String stayFrom, String stayTo, String address, Lease lease) {
        this.roomId = roomId;
        this.sublessorId = sublessorId;
        this.sublesseeId = sublesseeId;
        this.contractForm = contractForm;
        this.contractDate = contractDate;
        this.leaseDeposit = leaseDeposit;
        this.leaseFee = leaseFee;
        this.stayFrom = stayFrom;
        this.stayTo = stayTo;
        this.address = address;
        this.lease = lease;
    }
}
