package space.yjeong.service.salespost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.yjeong.domain.salespost.PostStatus;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.domain.salespost.SalesPostRepository;
import space.yjeong.exception.RoomNotFoundException;
import space.yjeong.exception.SalesPostNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SalesPostService {

    private final SalesPostRepository salesPostRepository;

    public List<SalesPost> findSalesPostsByUser(Long userId) {
        return salesPostRepository.findAllBySalesUserId(userId);
    }

    public SalesPost findSalesPostByRoom(Long roomId) {
        return salesPostRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    public SalesPost findSalesPostById(Long salesPostId) {
        return salesPostRepository.findById(salesPostId)
                .orElseThrow(() -> new SalesPostNotFoundException(salesPostId));
    }

    public SalesPost findLatestSalesPostByUser(Long userId) {
        return salesPostRepository.findBySalesUserIdOrderByIdDesc(userId);
    }

    public List<SalesPost> findPostingSalesPostsByUser(Long userId) {
        return salesPostRepository.findAllBySalesUserIdAndPostStatus(userId, PostStatus.POSTING);
    }

    public SalesPost saveSalesPost(SalesPost salesPost) {
        return salesPostRepository.save(salesPost);
    }

    public void deleteSalesPost(SalesPost salesPost) {
        salesPostRepository.delete(salesPost);
    }
}
