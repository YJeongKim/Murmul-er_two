package space.yjeong.domain.salespost;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String filename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_post_id")
    private SalesPost salesPost;

    @Builder
    public Image(String src, String filename, SalesPost salesPost) {
        this.src = src;
        this.filename = filename;
        this.salesPost = salesPost;
    }
}
