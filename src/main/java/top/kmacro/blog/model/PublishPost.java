package top.kmacro.blog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Zhangkh on 2017-09-27.
 */
@Entity
@Table(name = "mb_publish_post")
public class PublishPost extends BasePost{
    @Id
    @Column(nullable = false, length = 32)
    private String id;
    private Date verDate;
    private Date createDate;
    private Integer readNum;
    private Integer likeNum;
    private Boolean top;
    private Boolean display;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "mb_cate_post_pub", joinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "cate_id", referencedColumnName = "id") })
    private Set<Category> categorySet;
    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Comment> commentSet;

    public PublishPost() {
        readNum = 0;
        likeNum = 0;
        top = false;
        display = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getVerDate() {
        return verDate;
    }

    public void setVerDate(Date verDate) {
        this.verDate = verDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }
}
