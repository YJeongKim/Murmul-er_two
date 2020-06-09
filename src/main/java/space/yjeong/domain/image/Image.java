package space.yjeong.domain.image;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.yjeong.domain.salespost.SalesPost;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String src;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_post_id")
    private SalesPost salesPost;

    @Builder
    public Image(String src, SalesPost salesPost) {
        this.src = src;
        this.salesPost = salesPost;
    }
}
