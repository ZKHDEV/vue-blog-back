package top.kmacro.blog.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.Category;

import java.util.List;
import java.util.Set;

/**
 * Created by Zhangkh on 2017-09-02.
 */
public interface CategoryDao extends CrudRepository<Category,String>,JpaSpecificationExecutor<Category> {
    Set<Category> findAllByIdIn(String[] cateIds);
    List<Category> findAllByUser_TokenOrderByCreateDateAsc(String token);
    Category findByLabelAndUser_Token(String label, String token);
    List<Category> findAllByUser_IdOrderByCreateDateAsc(String uid);
    List<Category> findAllByUser_TokenAndLabelLikeOrderByCreateDateAsc(String token, String label);
    Category findById(String id);
}
