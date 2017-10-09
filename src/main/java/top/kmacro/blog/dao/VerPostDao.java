package top.kmacro.blog.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.VersionPost;

import java.util.Set;

public interface VerPostDao extends CrudRepository<VersionPost,String>,JpaSpecificationExecutor<VersionPost> {
    Set<VersionPost> findAllByCategorySetContains(Category category);
}
