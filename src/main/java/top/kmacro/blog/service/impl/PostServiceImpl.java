package top.kmacro.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kmacro.blog.dao.CategoryDao;
import top.kmacro.blog.dao.PostDao;
import top.kmacro.blog.dao.UserDao;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.Post;
import top.kmacro.blog.model.User;
import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.SaveVo;
import top.kmacro.blog.model.vo.post.SearchResultVo;
import top.kmacro.blog.model.vo.post.SearchVo;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.PostService;
import top.kmacro.blog.utils.CommonUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Zhangkh on 2017-09-01.
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public PageVo search(SearchVo searchVo) {
        Page<Post> postPage = postDao.findAll(new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                if(!StringUtils.isEmpty(searchVo.getTitle())){
                    predicates.add(criteriaBuilder.like(root.get("title"),'%'+searchVo.getTitle()+'%'));
                }
                if(!StringUtils.isEmpty(searchVo.getTags())){
                    predicates.add(criteriaBuilder.like(root.get("tags"),'%'+searchVo.getTags()+'%'));
                }
                if(!StringUtils.isEmpty(searchVo.getCateId())){
                    predicates.add(criteriaBuilder.equal(root.get("categorySet").get("id"),searchVo.getCateId()));
                }
                if(!StringUtils.isEmpty(searchVo.getTop())){
                    predicates.add(criteriaBuilder.equal(root.get("top"),searchVo.getTop()));
                }
                if(!StringUtils.isEmpty(searchVo.getStart()) && !StringUtils.isEmpty(searchVo.getEnd())){
                    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
                    Date start = null;
                    Date end = null;
                    try {
                        start = ft.parse(searchVo.getStart().trim());
                        end = ft.parse(searchVo.getEnd().trim());
                        end.setTime(end.getTime() + 24 * 60 * 60 * 1000);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicates.add(criteriaBuilder.between(root.get("startTime"),start,end));
                }
                predicates.add(criteriaBuilder.isNull(root.get("endTime")));
                predicates.add(criteriaBuilder.equal(root.get("user").get("token"),tokenManager.currentToken()));

                if(StringUtils.isEmpty(searchVo.getOrderCol())){
                    searchVo.setOrderCol("startTime");
                }

                Order order = searchVo.getAsc()
                        ? criteriaBuilder.asc(root.get(searchVo.getOrderCol()))
                        : criteriaBuilder.desc(root.get(searchVo.getOrderCol()));

                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])))
                        .orderBy(order);
                return null;
            }
        }, new PageRequest(searchVo.getPageNum() - 1, searchVo.getPageSize()));

        // 格式化查询结果
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SearchResultVo> resultList = new ArrayList<SearchResultVo>();
        List<Post> postList = postPage.getContent();
        for(Post post : postList){
            SearchResultVo resultVo = new SearchResultVo();
            BeanUtils.copyProperties(post,resultVo);
            resultVo.setCommentNum(post.getCommentSet().size());

            Set<Category> categorySet = post.getCategorySet();
            List<String> cateNameList = new ArrayList<String>();
            for(Category category : categorySet){
                cateNameList.add(category.getLabel());
            }
            resultVo.setCategories(String.join(",",cateNameList));

            resultVo.setStartTime(sdf.format(post.getStartTime()));

            resultList.add(resultVo);
        }

        PageVo pageVo = new PageVo(postPage.getTotalElements(),resultList);
        return pageVo;
    }

    @Override
    public Boolean titleExisted(String id, String title) {
        return postDao.count(new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                if(!StringUtils.isEmpty(id)){
                    predicates.add(criteriaBuilder.notEqual(root.get("id"),id));
                }
                predicates.add(criteriaBuilder.equal(root.get("title"),title));
                predicates.add(criteriaBuilder.isNull(root.get("endTime")));
                predicates.add(criteriaBuilder.equal(root.get("user").get("token"), tokenManager.currentToken()));
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        }) > 0;
    }

    @Override
    public SaveVo save(SaveVo saveVo) {
        if(!StringUtils.isEmpty(saveVo.getId())){
            Post srcPost = postDao.findByIdAndEndTimeIsNull(saveVo.getId());
            if(srcPost != null){
                srcPost.setEndTime(new Date());
                srcPost.setCategorySet(null);
                postDao.save(srcPost);
            }else{
                saveVo.setId(CommonUtils.uuid());
            }
        }else{
            saveVo.setId(CommonUtils.uuid());
        }

        Post post = new Post();
        BeanUtils.copyProperties(saveVo, post);

        post.setWordNum(post.getWordNum() != null ? post.getWordNum() : 0);
        post.setStartTime(new Date());
        post.setReadNum(post.getReadNum() != null ? post.getReadNum() : 0);
        post.setLikeNum(post.getLikeNum() != null ? post.getLikeNum() : 0);
        post.setType(post.getType() != null ? post.getType() : (byte)0);
        post.setState(post.getState() != null ? post.getState() : (byte)0);
        post.setTop(post.getTop() != null ? post.getTop() : false);

        post.setTags(String.join(",", saveVo.getTagList()));

        User user = userDao.findByToken(tokenManager.currentToken());
        post.setUser(user);

        if(saveVo.getCateIds().length > 0){
            Set<Category> categories = categoryDao.findAllByIdIn(saveVo.getCateIds());
            if(categories != null && categories.size() > 0){
                post.setCategorySet(categories);
            }
        }
        postDao.save(post);

        BeanUtils.copyProperties(post,saveVo);
        return saveVo;
    }

    @Override
    public void delete(String id) {
        Post post = postDao.findByIdAndEndTimeIsNull(id);
        if(post != null){
            post.setEndTime(new Date());
            postDao.save(post);
        }
    }

    @Override
    public void deleteBatch(String[] ids) {
        Set<Post> postSet = postDao.findAllByIdInAndEndTimeIsNull(ids);
        for(Post post : postSet){
            post.setEndTime(new Date());
            postDao.save(post);
        }
    }

}
