package pickup_shuttle.pickup.domain.board.dto.response;
import lombok.Builder;
import java.util.List;

@Builder
public record BoardList(List<ReadBoardListRp> content) {
}