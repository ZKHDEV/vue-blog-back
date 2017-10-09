package top.kmacro.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kmacro.blog.dao.PubPostDao;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.PublishPost;
import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.post.PageSearchVo;
import top.kmacro.blog.model.vo.post.PostVo;
import top.kmacro.blog.service.PubPostService;
import top.kmacro.blog.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PubPostServiceImpl implements PubPostService {

    @Autowired
    private PubPostDao pubPostDao;

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

        //其它信息
        if(!StringUtils.isEmpty(publishPost.getTags())){
            postVo.setTags(publishPost.getTags().split(","));
        }
        postVo.setCreateDate(DateTimeUtils.dateToString(DateTimeUtils.YMDHMS,publishPost.getCreateDate()));

        return postVo;
    }

    @Override
    public PageVo getPage(PageSearchVo pageSearchVo) {
        Sort.Order topOrder = new Sort.Order(Sort.Direction.DESC, "top");
        Sort.Order dateOrder = new Sort.Order(Sort.Direction.DESC, "createDate");
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        orderList.add(topOrder);
        orderList.add(dateOrder);
        Sort sort = new Sort(orderList);
        Page<PublishPost> postPage = pubPostDao.findAllByUser_PhoneAndDisplay(pageSearchVo.getPhone(),true,new PageRequest(pageSearchVo.getPageNum() - 1, pageSearchVo.getPageSize(), sort));

        // 格式化查询结果
        List<PostVo> resultList = new ArrayList<PostVo>();
        for(PublishPost publishPost : postPage.getContent()){
            resultList.add(parsePubPostToPostVo(publishPost));
        }

        PageVo<PostVo> pageVo = new PageVo(postPage.getTotalElements(),resultList);
        return pageVo;
    }

    @Override
    public PostVo getPost(String id) {
        PublishPost publishPost = pubPostDao.findByIdAndDisplay(id,true);
        if(publishPost != null){
            return parsePubPostToPostVo(publishPost);
        }
        return null;
    }
}
