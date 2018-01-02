package top.kmacro.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.Comment;

import java.util.List;

public interface CommentDao extends CrudRepository<Comment,String>,JpaSpecificationExecutor<Comment> {
    List<Comment> findAllByTarUser_IdOrderByVerDateDesc(String id, Pageable pageable);
    List<Comment> findAllBySrcUser_IdOrderByVerDateDesc(String id, Pageable pageable);
    List<Comment> findAllByPost_IdAndParCommentIsNullOrderByVerDate(String postId);
}
