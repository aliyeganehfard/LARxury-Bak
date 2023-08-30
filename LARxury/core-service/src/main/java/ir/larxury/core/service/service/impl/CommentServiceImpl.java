package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.service.JWTVerificationService;
import ir.larxury.core.service.database.model.Comment;
import ir.larxury.core.service.database.repository.CommentRepository;
import ir.larxury.core.service.provider.AsyncEngine;
import ir.larxury.core.service.service.CommentService;
import ir.larxury.core.service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void save(Comment comment, String token) {
        var userId = jwtVerificationService.getUuid(token);
        var product = productService.findById(comment.getProduct().getId());

        comment.setUserId(userId);
        comment.setProduct(product);

        commentRepository.save(comment);

        asyncEngine.sendEmailInstantDeliveryMessage(
                userId,
                "شما کامنت جدید دارید",
                String.format("شما برای محصول {%s} کامنت جدید دارید",product.getName())
        );

        log.info("save comment with id {}", comment.getId());
    }
}
