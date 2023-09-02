package ir.larxury.core.service.service;

import ir.larxury.core.service.common.dto.comment.req.PostReplyReq;
import ir.larxury.core.service.database.model.Comment;

import java.util.List;

public interface CommentService {

    void save(Comment comment, String token);

    void postReply(PostReplyReq reply, String token);

    List<Comment> getUnansweredComment(String token);

    List<Comment> findProductComments(Long productId);

    List<Comment> findShopComments(String token);
}
