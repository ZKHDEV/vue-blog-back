package top.kmacro.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kmacro.blog.dao.*;
import top.kmacro.blog.model.*;
import top.kmacro.blog.model.vo.KValueVo;
import top.kmacro.blog.model.vo.category.CateVo;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.CategoryService;
import top.kmacro.blog.utils.DateTimeUtils;

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
    private SavePostDao savePostDao;

    @Autowired
    private PubPostDao pubPostDao;

    @Autowired
    private VerPostDao verPostDao;

    @Autowired
    private TokenManager tokenManager;

    private CateVo parseCateToCateVo(Category category){
        CateVo cateVo = new CateVo();
        cateVo.setId(category.getId());
        cateVo.setCreateDate(DateTimeUtils.dateToString(DateTimeUtils.YMDHMS, category.getCreateDate()));
        cateVo.setLabel(category.getLabel());
        return cateVo;
    }

    @Override
    public void save(String id, String label) {
        Category category = null;
        if(!StringUtils.isEmpty(id)){
            category = categoryDao.findOne(id);
        }

        if(category == null){
            category = new Category();
            category.setCreateDate(new Date());

            User user = userDao.findByToken(tokenManager.currentToken());
            category.setUser(user);
        }

        category.setLabel(label);
        category.setVerDate(new Date());
        categoryDao.save(category);
    }

    @Override
    public void delete(String id) {
        Category category = categoryDao.findOne(id);
        if(category != null){
            if(category.getSavePostSet() != null){
                category.getSavePostSet().clear();
            }
            if(category.getPubPostSet() != null){
                category.getPubPostSet().clear();
            }
            if(category.getVerPostSet() != null){
                category.getVerPostSet().clear();
            }
            categoryDao.delete(id);
        }
    }

    @Override
    public List<KValueVo> getAllKVList() {
        List<Category> categorySet = categoryDao.findAllByUser_TokenOrderByCreateDateAsc(tokenManager.currentToken());

        // 格式化查询结果
        List<KValueVo> kValueVoList = new ArrayList<KValueVo>();
        for(Category category : categorySet){
            kValueVoList.add(new KValueVo(category.getId(),category.getLabel()));
        }
        return kValueVoList;
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
                predicates.add(criteriaBuilder.equal(root.join("user").get("token"),tokenManager.currentToken()));
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        }) > 0;
    }

    @Override
    public KValueVo getOneKVByLabel(String label) {
        Category category = categoryDao.findByLabelAndUser_Token(label,tokenManager.currentToken());
        if(category != null){
            KValueVo kValueVo = new KValueVo();
            kValueVo.setTxt(category.getLabel());
            kValueVo.setVal(category.getId());
            return kValueVo;
        }
        return null;
    }

    @Override
    public List<KValueVo> getKVListByPhone(String phone) {
        List<Category> categorySet = categoryDao.findAllByUser_PhoneOrderByCreateDateAsc(phone);

        // 格式化查询结果
        List<KValueVo> kValueVoList = new ArrayList<KValueVo>();
        for(Category category : categorySet){
            kValueVoList.add(new KValueVo(category.getId(),category.getLabel()));
        }
        return kValueVoList;
    }

    @Override
    public List<CateVo> search(String label) {
        label = label == null ? "" : label;
        List<Category> categoryList = categoryDao.findAllByUser_TokenAndLabelLikeOrderByCreateDateAsc( tokenManager.currentToken(), '%' + label + '%');
        List<CateVo> cateVos = null;
        if(categoryList != null && categoryList.size() > 0){
            cateVos = new ArrayList<>();
            for(Category category : categoryList){
                CateVo cateVo = parseCateToCateVo(category);
                if(category.getSavePostSet() != null){
                    cateVo.setTotal(category.getSavePostSet().size());
                }
                cateVos.add(cateVo);
            }
        }
        return  cateVos;
    }

    @Override
    public CateVo getCate(String id) {
        return parseCateToCateVo(categoryDao.findById(id));
    }
}
