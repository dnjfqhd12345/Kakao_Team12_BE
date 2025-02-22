package pickup_shuttle.pickup.domain.board.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import pickup_shuttle.pickup.domain.board.Board;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepositoryCustom {
    // 공고글 목록 조회(무한스크롤)
    Slice<Board> searchAllBySlice(Long lastBoardId, Pageable pageable);
    // picker가 수락한 공고글 조회(무한스크롤)
    Slice<Board> searchAllBySlice2(Long lastBoardId, Pageable pageable, Long userId);

    Optional<List<Board>> searchlastest(int limit);
}