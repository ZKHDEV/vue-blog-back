package top.kmacro.blog.service;

import top.kmacro.blog.model.vo.KValueVo;
import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.LikeVo;
import top.kmacro.blog.model.vo.post.PageSearchVo;
import top.kmacro.blog.model.vo.post.PostVo;

import java.util.List;

public interface PubPostService {
    PageVo getPage(PageSearchVo phoneSearchVo);
    PostVo getPost(String id);
    List<KValueVo> getNewKVListByUID(String uid);
    void likePost(LikeVo likeVo);
    String getUserIdByPostId(String id);
}
