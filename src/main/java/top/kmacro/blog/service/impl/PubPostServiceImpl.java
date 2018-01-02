package top.kmacro.blog.service.impl;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kmacro.blog.dao.PubPostDao;
import top.kmacro.blog.dao.UserDao;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.PublishPost;
import top.kmacro.blog.model.User;
import top.kmacro.blog.model.vo.KValueVo;
import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.LikeVo;
import top.kmacro.blog.model.vo.post.PhoneSearchVo;
import top.kmacro.blog.model.vo.post.PostVo;
import top.kmacro.blog.model.vo.post.PublishVo;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.PubPostService;
import top.kmacro.blog.utils.DateTimeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PubPostServiceImpl implements PubPostService {

    @Autowired
    private PubPostDao pubPostDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenManager tokenManager;

    private PostVo parsePubPostToPostVo(PublishPost publishPost){
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(publishPost,postVo);

        //文章类别信息处理
        Set<Category> categorySet = publishPost.getCategorySet();
        if(categorySet != null && categorySet.size() > 0){
            List<String> cateNameList = new ArrayList<String>();
            for(Category category : categorySet){
                cateNameList.add(category.getLabel());
            }
            postVo.setCategories(String.join(",",cateNameList));
        }

        //点赞信息
        postVo.setLikeNum(publishPost.getLikeUserSet().size());
        String currentToken = tokenManager.currentToken();
        if(!StringUtils.isEmpty(currentToken)){
            postVo.setLike(false);
            User user = userDao.findByToken(currentToken);
            if(user != null && pubPostDao.countByIdAndLikeUserSetContains(publishPost.getId(), user) > 0){
                postVo.setLike(true);
            }
        }

        //其它信息
        if(!StringUtils.isEmpty(publishPost.getTags())){
            postVo.setTags(publishPost.getTags().split(","));
        }
        postVo.setCreateDate(DateTimeUtils.dateToString(DateTimeUtils.YMDHMS,publishPost.getCreateDate()));

        return postVo;
    }

    @Override
    public PageVo getPage(PhoneSearchVo phoneSearchVo) {
        Sort.Order topOrder = new Sort.Order(Sort.Direction.DESC, "top");
        Sort.Order dateOrder = new Sort.Order(Sort.Direction.DESC, "createDate");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(topOrder);
        orderList.add(dateOrder);
        Sort sort = new Sort(orderList);
        Page<PublishPost> postPage = pubPostDao.findAllByUser_PhoneAndDisplay(phoneSearchVo.getPhone(),true,new PageRequest(phoneSearchVo.getPageNum() - 1, phoneSearchVo.getPageSize(), sort));

        // 格式化查询结果
        List<PostVo> resultList = new ArrayList<PostVo>();
        for(PublishPost publishPost : postPage.getContent()){
            resultList.add(parsePubPostToPostVo(publishPost));
        }

        PageVo<PostVo> pageVo = new PageVo(postPage.getTotalElements(),resultList);
        return pageVo;
    }

    @Override
    public List<KValueVo> getNewKVListByPhone(String phone) {
        List<PublishPost> postList = pubPostDao.findTop6ByUser_PhoneAndDisplayOrderByCreateDateDesc(phone,true);

        // 格式化查询结果
        List<KValueVo> kValueVoList = new ArrayList<KValueVo>();
        for(PublishPost publishPost : postList){
            kValueVoList.add(new KValueVo(publishPost.getId(),publishPost.getTitle()));
        }

        return kValueVoList;
    }

    @Override
    public PostVo getPost(String id) {
        PublishPost publishPost = pubPostDao.findByIdAndDisplay(id,true);
        if(publishPost != null){
            PostVo postVo = parsePubPostToPostVo(publishPost);
            postVo.setContent(top.kmacro.blog.utils.StringUtils.escapeMarkDownToHtml(postVo.getContent()));

            return postVo;
        }
        return null;
    }

    @Override
    public void likePost(LikeVo likeVo) {
        User user = userDao.findOne(tokenManager.currentUserId());
        PublishPost publishPost = pubPostDao.findOne(likeVo.getId());
        if(publishPost != null){
            if(likeVo.getLike().equals(publishPost.getLikeUserSet().contains(user))){
                return;
            }
            if(likeVo.getLike() == true){
                publishPost.getLikeUserSet().add(user);
            } else {
                publishPost.getLikeUserSet().remove(user);
            }
            pubPostDao.save(publishPost);
        }
    }

    @Override
    public String getUserIdByPostId(String id) {
        PublishPost publishPost = pubPostDao.findOne(id);
        return publishPost.getUser().getId();
    }
}
