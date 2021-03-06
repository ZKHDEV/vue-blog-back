package top.kmacro.blog.service;

import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.PublishVo;
import top.kmacro.blog.model.vo.post.SaveVo;
import top.kmacro.blog.model.vo.post.SearchVo;

public interface AdminPostService {
    PageVo search(SearchVo searchVo);
    Boolean titleExisted(String id, String title);
    SaveVo save(SaveVo post);
    SaveVo getPostSaveVo(String id);
    void delete(String... ids);
    void recycle(String... ids);
    void recover(String... ids);
    void publish(PublishVo publishVo);
    void unpublish(String id);
    PublishVo getPublishPost(String id);
    void top(PublishVo publishVo);
}
