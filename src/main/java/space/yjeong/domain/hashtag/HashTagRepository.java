package space.yjeong.domain.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import space.yjeong.domain.salespost.SalesPost;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    boolean existsBySalesPostId(Long salesPostId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from HashTag h where h.salesPost = :salesPost")
    void deleteAllBySalesPost(@Param("salesPost") SalesPost salesPost);
}
