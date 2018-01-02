package top.kmacro.blog.model.vo.comment;

import top.kmacro.blog.model.vo.post.PostVo;
import top.kmacro.blog.model.vo.user.UserVo;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CommentVo implements Comparable {
    private String id;
    private String content;
    private String verDate;
    private UserVo srcUser;
    private UserVo tarUser;
    private PostVo post;
    private CommentVo parComment;
    private List<CommentVo> chiCommentList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVerDate() {
        return verDate;
    }

    public void setVerDate(String verDate) {
        this.verDate = verDate;
    }

    public UserVo getSrcUser() {
        return srcUser;
    }

    public void setSrcUser(UserVo srcUser) {
        this.srcUser = srcUser;
    }

    public UserVo getTarUser() {
        return tarUser;
    }

    public void setTarUser(UserVo tarUser) {
        this.tarUser = tarUser;
    }

    public PostVo getPost() {
        return post;
    }

    public void setPost(PostVo post) {
        this.post = post;
    }

    public CommentVo getParComment() {
        return parComment;
    }

    public void setParComment(CommentVo parComment) {
        this.parComment = parComment;
    }

    public List<CommentVo> getChiCommentList() {
        return chiCommentList;
    }

    public void setChiCommentList(List<CommentVo> chiCommentList) {
        this.chiCommentList = chiCommentList;
    }

    @Override
    public int compareTo(Object o) {
        CommentVo commentVo = (CommentVo)o;
        return this.getVerDate().compareTo(commentVo.getVerDate());
    }
}
