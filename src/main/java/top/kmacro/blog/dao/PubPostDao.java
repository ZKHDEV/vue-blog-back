package top.kmacro.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.PublishPost;
import top.kmacro.blog.model.User;

import java.util.List;
import java.util.Set;

public interface PubPostDao extends CrudRepository<PublishPost,String>,JpaSpecificationExecutor<PublishPost> {
    Set<PublishPost> findAllByDisplayAndIdIn(Boolean display, String... ids);
    PublishPost findByIdAndDisplay(String id,Boolean display);
    Page<PublishPost> findAllByUser_PhoneAndDisplay(String userPhone, Boolean display, Pageable pageable);
    Set<PublishPost> findAllByCategorySetContains(Category category);
    Long countByIdAndDisplay(String id, Boolean display);
    List<PublishPost> findTop6ByUser_PhoneAndDisplayOrderByCreateDateDesc(String phone, Boolean display);
    Long countByIdAndLikeUserSetContains(String id, User user);
}
