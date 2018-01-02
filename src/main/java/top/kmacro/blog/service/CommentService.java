package top.kmacro.blog.service;

import top.kmacro.blog.model.vo.comment.CommentVo;
import top.kmacro.blog.model.vo.comment.SaveVo;
import top.kmacro.blog.model.vo.comment.SearchVo;

import java.util.List;

public interface CommentService {
//    List<CommentVo> search(SearchVo searchVo);
    void delete(String... ids);
    CommentVo save(SaveVo saveVo);
    List<CommentVo> getParComments(String postId);
}
