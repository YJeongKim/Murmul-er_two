package space.yjeong.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllBySalesPostId(Long salesPostId);
    Image findFirstBySalesPostId(Long salesPostId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from Image i where i.salesPost.id = :salesPostId")
    void deleteAllBySalesPostId(@Param("salesPostId") Long salesPostId);
}
