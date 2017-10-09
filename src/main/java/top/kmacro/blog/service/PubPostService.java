package top.kmacro.blog.service;

import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.PageSearchVo;
import top.kmacro.blog.model.vo.post.PostVo;

public interface PubPostService {
    PageVo getPage(PageSearchVo pageSearchVo);
    PostVo getPost(String id);
}
