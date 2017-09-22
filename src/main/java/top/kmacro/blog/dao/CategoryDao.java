package top.kmacro.blog.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.Category;

import java.util.Set;

/**
 * Created by Zhangkh on 2017-09-02.
 */
public interface CategoryDao extends CrudRepository<Category,String>,JpaSpecificationExecutor<Category> {
    Set<Category> findAllByIdIn(String[] cateIds);
    Set<Category> findAllByUser_TokenOrderByCreateTimeAsc(String token);
    Category findByLabelAndUser_Token(String label, String token);
}
