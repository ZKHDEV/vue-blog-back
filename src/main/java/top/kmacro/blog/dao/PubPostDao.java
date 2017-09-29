package top.kmacro.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.PublishPost;

import java.util.Set;

public interface PubPostDao extends CrudRepository<PublishPost,String>,JpaSpecificationExecutor<PublishPost> {
    Set<PublishPost> findAllByShowAndIdIn(Boolean show, String... ids);
    PublishPost findByIdAndShow(String id,Boolean show);
    Page<PublishPost> findAllByUser_PhoneAndShow(String userPhone, Boolean show, Pageable pageable);
}
