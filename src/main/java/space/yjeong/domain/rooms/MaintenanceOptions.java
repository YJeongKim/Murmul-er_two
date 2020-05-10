package space.yjeong.domain.rooms;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MaintenanceOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "option", nullable = false)
    private MaintenanceOption maintenanceOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_Posts_id")
    private SalesPosts salesPosts;
}
