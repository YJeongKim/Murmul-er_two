package space.yjeong.domain.rooms;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagsRepository extends JpaRepository<HashTags, Long> {
    boolean existsBySalesPostsId(Long salesPostsId);
    void deleteAllBySalesPostsId(Long salesPostsId);
}
