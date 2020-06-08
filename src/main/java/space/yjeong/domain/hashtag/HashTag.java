package space.yjeong.domain.hashtag;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.yjeong.domain.salespost.SalesPost;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "hash_tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_post_id")
    private SalesPost salesPost;

    @Builder
    public HashTag(String tag, SalesPost salesPost) {
        this.tag = tag;
        this.salesPost = salesPost;
    }
}
