package pickup_shuttle.pickup.domain.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pickup_shuttle.pickup._core.utils.ApiUtils;
import pickup_shuttle.pickup._core.utils.CustomPage;
import pickup_shuttle.pickup.config.Login;
import pickup_shuttle.pickup.domain.board.dto.request.AcceptBoardRq;
import pickup_shuttle.pickup.domain.board.dto.request.CreateBoardRq;
import pickup_shuttle.pickup.domain.board.dto.request.UpdateBoardRq;
import pickup_shuttle.pickup.domain.board.dto.response.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("articles")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<CustomPage<ReadBoardListRp>>> getBoardList(
            @RequestParam(value = "offset",required = false) Long lastBoardId,
            @RequestParam(value = "limit",defaultValue = "10") int size) {
        Slice<ReadBoardListRp> responseDTOSlice = boardService.boardList(lastBoardId, size);
        return ResponseEntity.ok(ApiUtils.success(new CustomPage<>(responseDTOSlice)));
    }

    @PostMapping("/write")
    public ResponseEntity<ApiUtils.ApiResult<CreateBoardRp>> write(@RequestBody @Valid CreateBoardRq requestDTO,
                                                                   @Login Long userId){
        CreateBoardRp responseDTO = boardService.write(requestDTO, userId);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiUtils.ApiResult<CheckBeforeAfter>> boardDetail(@PathVariable("boardId") Long boardId,
                                                                            @Login Long userId){
        CheckBeforeAfter responseDTO = boardService.checkBeforeAfter(boardId, userId);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/agree/{boardId}")
    public ResponseEntity<ApiUtils.ApiResult<AcceptBoardRp>> pickupAgree(@PathVariable("boardId") Long boardId,
                                                                         @RequestBody @Valid AcceptBoardRq requestDTO,
                                                                         @Login Long userId) {
        AcceptBoardRp responseDTO = boardService.boardAgree(requestDTO,boardId,userId);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<ApiUtils.ApiResult<DeleteBoardRp>> delete(@PathVariable("boardId") Long boardId,
                                                                    @Login Long userId){
        DeleteBoardRp responseDTO = boardService.boardDelete(boardId, userId);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    @PutMapping("/modify/{boardId}")
    public ResponseEntity<ApiUtils.ApiResult<UpdateBoardRp>> update(@PathVariable("boardId") Long boardId,
                                                                    @RequestBody @Valid UpdateBoardRq requestDTO,
                                                                    @Login Long userId){
        boardService.checkListEmpty(requestDTO.beverages());
        UpdateBoardRp responseDTO = boardService.update(requestDTO, boardId, userId);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    @GetMapping("/latest")
    public ResponseEntity<ApiUtils.ApiResult<BoardList>> getLatestBoard
            (@RequestParam(value = "offset",defaultValue = "3") int limit){
        BoardList responseDTO = BoardList.builder().content(boardService.getLatestBoard(limit)).build();
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}