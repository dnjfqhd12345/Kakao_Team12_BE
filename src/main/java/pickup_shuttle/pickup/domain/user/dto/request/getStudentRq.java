package pickup_shuttle.pickup.domain.user.dto.request;

import lombok.Builder;

@Builder
public record getStudentRq(
        String nickname
) {
}
