package top.kmacro.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kmacro.blog.dao.CategoryDao;
import top.kmacro.blog.dao.UserDao;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.User;
import top.kmacro.blog.model.vo.KValueVo;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.CategoryService;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Zhangkh on 2017-09-03.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public void save(String id, String label) {
        Category category = null;
        if(!StringUtils.isEmpty(id)){
            category = categoryDao.findOne(id);
        }

        if(category == null){
            category = new Category();
            category.setCreateTime(new Date());

            User user = userDao.findByToken(tokenManager.currentToken());
            category.setUser(user);
        }

        category.setLabel(label);

        categoryDao.save(category);
    }

    @Override
    public void delete(String id) {
        Category category = categoryDao.findOne(id);
        if(category != null){
            Set<Post> postList = postDao.findAllByCategorySetContains(category);
            for (Post post : postList){
                Set<Category> categorySet = post.getCategorySet();
                categorySet.remove(category);
                post.setCategorySet(categorySet);
                postDao.save(post);
            }

            categoryDao.delete(id);
        }
    }

    @Override
    public List<KValueVo> getSelect() {
        Set<Category> categorySet = categoryDao.findAllByUser_TokenOrderByCreateTimeAsc(tokenManager.currentToken());

        // 格式化查询结果
        List<KValueVo> resultList = new ArrayList<KValueVo>();
        for(Category category : categorySet){
            KValueVo kValueVo = new KValueVo(category.getId(),category.getLabel());
            resultList.add(kValueVo);
        }
        return resultList;
    }

    @Override
    public Boolean labelExisted(String id, String label){
        return categoryDao.count(new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                if(!StringUtils.isEmpty(id)){
                    predicates.add(criteriaBuilder.notEqual(root.get("id"), id));
                }
                predicates.add(criteriaBuilder.equal(root.get("label"), label));
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        }) > 0;
    }

    @Override
    public KValueVo getSelectOne(String label) {
        Category category = categoryDao.findByLabelAndUser_Token(label,tokenManager.currentToken());
        KValueVo kValueVo = null;
        if(category != null){
            kValueVo = new KValueVo();
            kValueVo.setTxt(category.getLabel());
            kValueVo.setVal(category.getId());
        }
        return kValueVo;
    }
}
