package top.kmacro.blog.service;

import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.PostVo;

public interface PubPostService {
    PageVo getPagePostByUserPhone(String userPhone, Integer pageSize, Integer pageNum);
    PostVo getPost(String id);
}
