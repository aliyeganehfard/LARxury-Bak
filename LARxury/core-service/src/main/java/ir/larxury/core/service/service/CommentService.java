package ir.larxury.core.service.service;

import ir.larxury.core.service.database.model.Comment;

public interface CommentService {
    void save(Comment comment, String token);
}
