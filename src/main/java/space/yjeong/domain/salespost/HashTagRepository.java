package space.yjeong.domain.salespost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    boolean existsBySalesPostId(Long salesPostId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from HashTag h where h.salesPost.id = :salesPostId")
    void deleteAllBySalesPost(@Param("salesPostId") Long salesPostId);
}
