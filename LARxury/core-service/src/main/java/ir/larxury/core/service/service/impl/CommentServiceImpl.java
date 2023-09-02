package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.JWTVerificationService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.common.dto.comment.req.PostReplyReq;
import ir.larxury.core.service.database.model.Comment;
import ir.larxury.core.service.database.repository.CommentRepository;
import ir.larxury.core.service.provider.AsyncEngine;
import ir.larxury.core.service.provider.AuthServiceProvider;
import ir.larxury.core.service.service.CommentService;
import ir.larxury.core.service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AsyncEngine asyncEngine;

    @Autowired
    private JWTVerificationService jwtVerificationService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @Override
    public void save(Comment comment, String token) {
        var userId = jwtVerificationService.getUuid(token);
        var product = productService.findById(comment.getProduct().getId());
        var userInfo = authServiceProvider.getUserInfo(userId);

        comment.setCommenterUserId(userId);
        comment.setProduct(product);
        comment.setCommenterUsername(userInfo.getUsername());

        commentRepository.save(comment);

        asyncEngine.sendEmailInstantDeliveryMessage(
                userId,
                "شما کامنت جدید دارید",
                String.format("شما برای محصول {%s} کامنت جدید دارید", product.getName())
        );

        log.info("save comment with id {}", comment.getId());
    }

    @Override
    public void postReply(PostReplyReq reply, String token) {
        var shopOwnerId = jwtVerificationService.getUuid(token);
        var comment = findCommentForPostReply(reply.getCommentId(), shopOwnerId);
        comment.setAnswer(reply.getAnswer());
        comment.setAnswerDate(new Date());

        update(comment);

        asyncEngine.sendEmailInstantDeliveryMessage(
                comment.getCommenterUserId(),
                "پاسخ کامنت شما",
                comment.getAnswer()
        );
    }

    @Override
    public List<Comment> getUnansweredComment(String token) {
        var shopOwnerId = jwtVerificationService.getUuid(token);
        return commentRepository.findUnansweredComment(shopOwnerId);
    }

    @Override
    public List<Comment> findProductComments(Long productId) {
        return commentRepository.findAllByProductId(productId);
    }

    @Override
    public List<Comment> findShopComments(String token) {
        var shopOwnerId = jwtVerificationService.getUuid(token);
        return commentRepository.findShopCommentsByShopOwnerId(shopOwnerId);
    }

    public void update(Comment comment) {
        commentRepository.save(comment);
        log.info("comment with id {} updated", comment.getId());
    }

    private Comment findCommentForPostReply(Long commentId, String shopOwnerId) {
        return commentRepository.findCommentForPostReply(commentId, shopOwnerId).orElseThrow(() -> {
            log.error(ErrorCode.CORE_SERVICE_COMMENT_NOT_FOUND.getTechnicalMessage() +
                    " with comment id {} and shop owner id {}", commentId, shopOwnerId);
            return new CoreServiceException(ErrorCode.CORE_SERVICE_COMMENT_NOT_FOUND);
        });
    }
}
