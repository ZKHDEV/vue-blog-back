package top.kmacro.blog.service;

import top.kmacro.blog.model.vo.KValueVo;
import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.PhoneSearchVo;
import top.kmacro.blog.model.vo.post.PostVo;

import java.util.List;

public interface PubPostService {
    PageVo getPage(PhoneSearchVo phoneSearchVo);
    PostVo getPost(String id);
    List<KValueVo> getNewKVListByPhone(String phone);
}
