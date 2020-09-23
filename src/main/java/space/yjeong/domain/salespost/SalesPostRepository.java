package space.yjeong.domain.salespost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SalesPostRepository extends JpaRepository<SalesPost, Long> {
    Optional<SalesPost> findByRoomId(Long roomId);
    List<SalesPost> findAllBySalesUserId(Long salesUserId);
    List<SalesPost> findAllBySalesUserIdAndPostStatus(Long salesUserId, PostStatus postStatus);

    @Query(nativeQuery = true, value = "select * from sales_posts s where s.user_id = :salesUserId order by s.id desc limit 1")
    SalesPost findBySalesUserIdOrderByIdDesc(Long salesUserId);
}
