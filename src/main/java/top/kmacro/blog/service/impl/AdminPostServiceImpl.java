package top.kmacro.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kmacro.blog.dao.*;
import top.kmacro.blog.model.*;
import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.PostVo;
import top.kmacro.blog.model.vo.post.SaveVo;
import top.kmacro.blog.model.vo.post.SearchResultVo;
import top.kmacro.blog.model.vo.post.SearchVo;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.AdminPostService;
import top.kmacro.blog.utils.CommonUtils;
import top.kmacro.blog.utils.DateTimeUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminPostServiceImpl implements AdminPostService {

    @Autowired
    private SavePostDao savePostDao;

    @Autowired
    private PubPostDao pubPostDao;

    @Autowired
    private VerPostDao verPostDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public PageVo search(SearchVo searchVo) {
        Page<SavePost> postPage = savePostDao.findAll(new Specification<SavePost>() {
            @Override
            public Predicate toPredicate(Root<SavePost> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                if(!StringUtils.isEmpty(searchVo.getTitle())){
                    predicates.add(criteriaBuilder.like(root.get("title"),'%'+searchVo.getTitle()+'%'));
                }
                if(!StringUtils.isEmpty(searchVo.getTags())){
                    predicates.add(criteriaBuilder.like(root.get("tags"),'%'+searchVo.getTags()+'%'));
                }
                if(!StringUtils.isEmpty(searchVo.getCateId())){
                    predicates.add(criteriaBuilder.equal(root.join("categorySet").get("id"),searchVo.getCateId()));
                }
                if(searchVo.getTop() != null){
                    predicates.add(criteriaBuilder.equal(root.get("top"),searchVo.getTop()));
                }
                if(!StringUtils.isEmpty(searchVo.getStart()) && !StringUtils.isEmpty(searchVo.getEnd())){
                    Date start = DateTimeUtils.stringToDate(DateTimeUtils.YMD,searchVo.getStart().trim());
                    Date end = DateTimeUtils.stringToDate(DateTimeUtils.YMD,searchVo.getStart().trim());
                    end.setTime(end.getTime() + 24 * 60 * 60 * 1000);
                    predicates.add(criteriaBuilder.between(root.get("createDate"),start,end));
                }
                predicates.add(criteriaBuilder.equal(root.join("user").get("token"),tokenManager.currentToken()));

                if(StringUtils.isEmpty(searchVo.getOrderCol())){
                    searchVo.setOrderCol("createTime");
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
        List<PostVo> resultList = new ArrayList<PostVo>();
        for(SavePost post : postPage.getContent()){
            PostVo postVo = new PostVo();
            BeanUtils.copyProperties(post,postVo);

            //查询文章发布信息
            PublishPost publishPost = pubPostDao.findOne(post.getId());
            if(publishPost != null){
                postVo.setReadNum(publishPost.getReadNum());
                postVo.setLikeNum(publishPost.getLikeNum());
                postVo.setCommentNum(publishPost.getCommentSet().size());
                postVo.setTop(publishPost.getTop());
            }

            //文章类别信息处理
            Set<Category> categorySet = post.getCategorySet();
            if(categorySet != null && categorySet.size() > 0){
                List<String> cateNameList = new ArrayList<String>();
                for(Category category : categorySet){
                    cateNameList.add(category.getLabel());
                }
                postVo.setCategories(String.join(",",cateNameList));
            }

            //其它信息
            if(!StringUtils.isEmpty(post.getTags())){
                postVo.setTags(post.getTags().split(","));
            }
            postVo.setCreateDate(DateTimeUtils.dateToString(DateTimeUtils.YMDHMS,post.getCreateDate()));

            resultList.add(postVo);
        }

        PageVo<PostVo> pageVo = new PageVo(postPage.getTotalElements(),resultList);
        return pageVo;
    }

    @Override
    public Boolean titleExisted(String id, String title) {
        if (StringUtils.isEmpty(id)){
            return savePostDao.countByTitleAndUser_Token(title,tokenManager.currentToken()) > 0;
        } else {
            return savePostDao.countByIdAndTitleAndUser_Token(id,title,tokenManager.currentToken()) > 0;
        }
    }

    @Override
    public SaveVo save(SaveVo saveVo) {
        SavePost savePost = null;
        if(!StringUtils.isEmpty(saveVo.getId())){
            savePost = savePostDao.findOne(saveVo.getId());
            if(savePost != null){
                VersionPost versionPost = new VersionPost();
                BeanUtils.copyProperties(savePost,versionPost);
                verPostDao.save(versionPost);
            }
        }

        if(savePost == null){
            savePost = new SavePost();
            savePost.setId(CommonUtils.uuid());
            savePost.setCreateDate(new Date());
        }

        BeanUtils.copyProperties(saveVo, savePost);
        savePost.setVerDate(new Date());
        savePost.setTags(String.join(",", saveVo.getTagList()));

        //文章用户信息
        User user = userDao.findByToken(tokenManager.currentToken());
        savePost.setUser(user);

        //文章分类信息
        if(saveVo.getCateIds() != null && saveVo.getCateIds().length > 0){
            Set<Category> categories = categoryDao.findAllByIdIn(saveVo.getCateIds());
            if(categories != null && categories.size() > 0){
                savePost.setCategorySet(categories);
            }
        }
        savePostDao.save(savePost);

        //格式化返回文章版本日期
        saveVo.setVerDate(DateTimeUtils.dateToString(DateTimeUtils.YMDHMS,savePost.getVerDate()));
        return saveVo;
    }

    @Override
    public SaveVo getPostSaveVo(String id){
        SavePost savePost = savePostDao.findByRecycleAndId(false,id);
        if(savePost != null){
            SaveVo saveVo = new SaveVo();
            BeanUtils.copyProperties(savePost,saveVo);

            //格式化文章类别信息
            Set<Category> categorySet = savePost.getCategorySet();
            if(categorySet != null && categorySet.size() > 0){
                Set<String> ids = new HashSet<String>();
                for(Category category : categorySet){
                    ids.add(category.getId());
                }
                saveVo.setCateIds(ids.toArray(new String[categorySet.size()]));
            }

            //格式化文章标签信息
            if(!StringUtils.isEmpty(savePost.getTags())){
                saveVo.setTagList(savePost.getTags().split(","));
            }

            saveVo.setVerDate(DateTimeUtils.dateToString(DateTimeUtils.YMDHMS,savePost.getVerDate()));
            return saveVo;
        }
        return null;
    }

    @Override
    public void delete(String... ids) {
        for(String id : ids){
            savePostDao.delete(id);
            pubPostDao.delete(id);
            verPostDao.delete(id);
        }
    }

    @Override
    public void recycle(String... ids) {
        Set<SavePost> savePosts = savePostDao.findAllByRecycleAndIdIn(false,ids);
        Set<PublishPost> publishPosts = pubPostDao.findAllByShowAndIdIn(true,ids);
        //设置文章为回收状态
        if(savePosts != null && savePosts.size() > 0){
            for (SavePost savePost : savePosts){
                savePost.setRecycle(true);
                savePostDao.save(savePost);
            }
        }
        //下架回收的已发布文章
        if(publishPosts != null && publishPosts.size() > 0){
            for (PublishPost publishPost : publishPosts){
                publishPost.setShow(false);
                pubPostDao.save(publishPost);
            }
        }
    }

    @Override
    public void recover(String... ids) {
        Set<SavePost> savePosts = savePostDao.findAllByRecycleAndIdIn(true,ids);
        if(savePosts != null && savePosts.size() > 0){
            for (SavePost savePost : savePosts){
                savePost.setRecycle(false);
                savePostDao.save(savePost);
            }
        }
    }

    @Override
    public void publish(String id) {
        SavePost savePost = savePostDao.findOne(id);
        if(savePost != null){
            PublishPost publishPost = pubPostDao.findOne(id);
            if(publishPost == null){
                publishPost = new PublishPost();
                BeanUtils.copyProperties(savePost,publishPost);

                publishPost.setCreateDate(new Date());
            } else {
                Date createDate = publishPost.getCreateDate();
                BeanUtils.copyProperties(savePost,publishPost);

                publishPost.setCreateDate(createDate);
                publishPost.setShow(true);
            }
            pubPostDao.save(publishPost);
        }
    }

    @Override
    public void unpublish(String id) {
        PublishPost publishPost = pubPostDao.findByIdAndShow(id,true);
        if(publishPost != null){
            publishPost.setShow(false);
            pubPostDao.save(publishPost);
        }
    }
}