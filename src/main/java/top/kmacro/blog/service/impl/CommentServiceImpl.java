package top.kmacro.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kmacro.blog.dao.CommentDao;
import top.kmacro.blog.dao.PubPostDao;
import top.kmacro.blog.dao.UserDao;
import top.kmacro.blog.model.Comment;
import top.kmacro.blog.model.PublishPost;
import top.kmacro.blog.model.User;
import top.kmacro.blog.model.vo.comment.CommentVo;
import top.kmacro.blog.model.vo.comment.SaveVo;
import top.kmacro.blog.model.vo.comment.SearchVo;
import top.kmacro.blog.model.vo.post.PostVo;
import top.kmacro.blog.model.vo.user.UserVo;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.CommentService;
import top.kmacro.blog.utils.DateTimeUtils;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PubPostDao pubPostDao;

    @Autowired
    private TokenManager tokenManager;

    private UserVo parseUserToUserVo(User user){
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setPhone(user.getPhone());
        userVo.setNickName(user.getNickName());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }

    private PostVo parsePostToPostVo(PublishPost post){
        PostVo postVo = new PostVo();
        postVo.setId(post.getId());
        postVo.setTitle(post.getTitle());
        return postVo;
    }

    private CommentVo parseCommentToCommentVo(Comment comment){
        CommentVo commentVo = new CommentVo();
        commentVo.setId(comment.getId());
        commentVo.setContent(comment.getContent());
        commentVo.setVerDate(DateTimeUtils.dateToString(DateTimeUtils.YMDHMS, comment.getVerDate()));
        commentVo.setSrcUser(parseUserToUserVo(comment.getSrcUser()));
        commentVo.setTarUser(parseUserToUserVo(comment.getTarUser()));
        commentVo.setPost(parsePostToPostVo(comment.getPost()));
        Set<Comment> chiCommentSet = comment.getChiCommentSet();
        if(chiCommentSet != null && chiCommentSet.size() > 0){
            List<CommentVo> chiCommentList = new ArrayList<>(chiCommentSet.size());
            for(Comment chiComment : chiCommentSet){
                chiCommentList.add(parseCommentToCommentVo(chiComment));
            }
            Collections.sort(chiCommentList);
            commentVo.setChiCommentList(chiCommentList);
        }
        Comment parComment = comment.getParComment();
        if(parComment != null){
            CommentVo parCommentVo = new CommentVo();
            parCommentVo.setId(parComment.getId());
            parCommentVo.setContent(parComment.getContent());
            commentVo.setParComment(parCommentVo);
        }
        return commentVo;
    }

//    @Override
//    public List<CommentVo> search(SearchVo searchVo) {
//        List<Comment> commentList = commentDao.findAllByTarUser_IdOrderByVerDateDesc(tokenManager.currentUserId(),
//                new PageRequest(searchVo.getPageNum() - 1, searchVo.getPageSize()));
//        if(commentList == null || commentList.size() < 1){
//            return null;
//        }
//
//        List<CommentVo> commentVos = new ArrayList<>(commentList.size());
//        for (Comment comment : commentList){
//            commentVos.add(parseCommentToCommentVo(comment));
//        }
//        return commentVos;
//    }

    @Override
    public void delete(String... ids) {
        for(String id : ids){
            commentDao.delete(id);
        }
    }

    @Override
    public CommentVo save(SaveVo saveVo) {
        Comment comment = new Comment();
        comment.setContent(saveVo.getContent());
        comment.setVerDate(new Date());
        //评论者
        User srcUser = userDao.findByToken(tokenManager.currentToken());
        comment.setSrcUser(srcUser);
        //被评论者
        User tarUser = userDao.findOne(saveVo.getTarUserId());
        comment.setTarUser(tarUser);
        //评论文章
        PublishPost publishPost = pubPostDao.findOne(saveVo.getPostId());
        comment.setPost(publishPost);

        String parCommentId = saveVo.getParCommentId();
        Comment parComment = null;
        if(!StringUtils.isEmpty(parCommentId)){
            parComment = commentDao.findOne(parCommentId);
            if(parComment != null){
                comment.setParComment(parComment);
            }
        }

        commentDao.save(comment);

        return parseCommentToCommentVo(comment);
    }

    @Override
    public List<CommentVo> getParComments(String postId) {
        List<Comment> commentList = commentDao.findAllByPost_IdAndParCommentIsNullOrderByVerDate(postId);
        if(commentList == null || commentList.size() < 1){
            return null;
        }

        List<CommentVo> commentVos = new ArrayList<>(commentList.size());
        for (Comment comment : commentList){
            commentVos.add(parseCommentToCommentVo(comment));
        }
        return commentVos;
    }
}
