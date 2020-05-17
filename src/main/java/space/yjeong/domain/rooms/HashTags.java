package space.yjeong.domain.rooms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class HashTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_posts_id")
    private SalesPosts salesPosts;

    @Builder
    public HashTags(String tag, SalesPosts salesPosts) {
        this.tag = tag;
        this.salesPosts = salesPosts;
    }
}
