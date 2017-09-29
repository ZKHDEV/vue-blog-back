package top.kmacro.blog.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Zhangkh on 2017-09-13.
 */
@Entity
@Table(name = "mb_comment")
public class Comment implements Serializable{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    private String content;
    private Date verDate;
    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name="src_user_id")
    private User srcUser;
    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name="tar_user_id")
    private User tarUser;
    @ManyToOne(targetEntity = PublishPost.class,fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private PublishPost post;
    @ManyToOne(targetEntity = Comment.class,fetch = FetchType.LAZY)
    @JoinColumn(name="par_comment_id")
    private Comment parComment;
    @OneToMany(mappedBy = "parComment",fetch = FetchType.LAZY)
    private Set<Comment> commentSet;

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

    public Date getVerDate() {
        return verDate;
    }

    public void setVerDate(Date verDate) {
        this.verDate = verDate;
    }

    public User getSrcUser() {
        return srcUser;
    }

    public void setSrcUser(User srcUser) {
        this.srcUser = srcUser;
    }

    public User getTarUser() {
        return tarUser;
    }

    public void setTarUser(User tarUser) {
        this.tarUser = tarUser;
    }

    public PublishPost getPost() {
        return post;
    }

    public void setPost(PublishPost post) {
        this.post = post;
    }

    public Comment getParComment() {
        return parComment;
    }

    public void setParComment(Comment parComment) {
        this.parComment = parComment;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }
}
