package top.kmacro.blog.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.Post;

import java.util.Set;


/**
 * Created by Zhangkh on 2017-08-29.
 */
public interface PostDao extends CrudRepository<Post,String>,JpaSpecificationExecutor<Post> {
    Post findByIdAndEndTimeIsNull(String id);
    Set<Post> findAllByIdInAndEndTimeIsNull(String[] ids);
    Set<Post> findAllByCategorySetContains(Category category);
}
