package top.kmacro.blog.service;

import top.kmacro.blog.model.vo.KValueVo;
import top.kmacro.blog.model.vo.category.CateVo;
import top.kmacro.blog.model.vo.post.SearchVo;

import java.util.List;

/**
 * Created by Zhangkh on 2017-09-03.
 */
public interface CategoryService {
    void save(String id, String label);
    void delete(String id);
    List<KValueVo> getAllKVList();
    Boolean labelExisted(String id, String label);
    KValueVo getOneKVByLabel(String label);
    List<KValueVo> getKVListByUID(String uid);
    List<CateVo> search(String label);
    CateVo getCate(String id);
}
