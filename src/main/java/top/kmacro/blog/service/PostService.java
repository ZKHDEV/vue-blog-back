package top.kmacro.blog.service;

import org.springframework.data.domain.Page;
import top.kmacro.blog.model.Post;
import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.SaveVo;
import top.kmacro.blog.model.vo.post.SearchResultVo;
import top.kmacro.blog.model.vo.post.SearchVo;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Zhangkh on 2017-09-01.
 */
public interface PostService {
    PageVo search(SearchVo searchVo);
    Boolean titleExisted(String id, String title);
    SaveVo save(SaveVo post);
    void delete(String id);
    void deleteBatch(String[] ids);
}
