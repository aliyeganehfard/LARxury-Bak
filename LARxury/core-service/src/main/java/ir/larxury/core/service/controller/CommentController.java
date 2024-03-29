package ir.larxury.core.service.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.common.utils.config.BaseModelMapper;
import ir.larxury.common.utils.service.JWTVerificationService;
import ir.larxury.core.service.common.dto.comment.req.CommentReq;
import ir.larxury.core.service.common.dto.comment.req.PostReplyReq;
import ir.larxury.core.service.common.dto.comment.res.CommentRes;
import ir.larxury.core.service.database.model.Comment;
import ir.larxury.core.service.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/comment/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private final BaseModelMapper mapper = new BaseModelMapper();

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("save")
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid CommentReq req,
                                                @RequestHeader("Authorization") String token) {
        var comment = mapper.toModel(req, Comment.class);
        commentService.save(comment, token);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SHOP_ADMIN')")
    @PostMapping("post/reply")
    public ResponseEntity<GeneralResponse> postReply(@RequestBody @Valid PostReplyReq req,
                                                     @RequestHeader("Authorization") String token) {
        commentService.postReply(req, token);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SHOP_ADMIN')")
    @GetMapping("get/shop/unanswered")
    public ResponseEntity<GeneralResponse> getUnansweredComment(@RequestHeader("Authorization") String token) {
        var comments = commentService.getUnansweredComment(token);
        var commentsDto = mapper.toDtoList(comments,CommentRes.class);
        var res = GeneralResponse.successfulResponse(commentsDto, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("get/product/comments/")
    public ResponseEntity<GeneralResponse> getProductComments(@RequestParam("productId") Long productId) {
        var comments = commentService.findProductComments(productId);
        var commentsDto = mapper.toDtoList(comments, CommentRes.class);
        var res = GeneralResponse.successfulResponse(commentsDto, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("get/shop/comments")
    public ResponseEntity<GeneralResponse> getShopComments(@RequestHeader("Authorization") String token) {
        var comments = commentService.findShopComments(token);
        var commentsDto = mapper.toDtoList(comments,CommentRes.class);
        var res = GeneralResponse.successfulResponse(commentsDto,ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
