package top.kmacro.blog.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.VersionPost;

public interface VerPostDao extends CrudRepository<VersionPost,String>,JpaSpecificationExecutor<VersionPost> {
}
