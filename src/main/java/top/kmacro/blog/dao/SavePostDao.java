package top.kmacro.blog.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.SavePost;

import java.util.Set;

public interface SavePostDao extends CrudRepository<SavePost,String>,JpaSpecificationExecutor<SavePost> {
    Long countByIdAndTitleAndUser_Token(String id, String title, String userToken);
    Long countByTitleAndUser_Token(String title, String userToken);
    SavePost findByRecycleAndId(Boolean recycle, String id);
    Set<SavePost> findAllByRecycleAndIdIn(Boolean recycle, String... ids);
    Set<SavePost> findAllByCategorySetContains(Category category);
}
