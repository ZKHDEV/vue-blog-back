package top.kmacro.blog.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Zhangkh on 2017-09-14.
 */
@Entity
@Table(name = "mb_notice")
public class Notice {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private Integer id;
//    private Boolean read;
//    private Byte type;
//    private Date createTime;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//    @ManyToOne
//    @JoinColumn(name = "comment_id")
//    private Comment comment;
//    @ManyToOne
//    @JoinColumn(name = "message_id")
//    private Message message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Boolean getRead() {
//        return read;
//    }
//
//    public void setRead(Boolean read) {
//        this.read = read;
//    }
//
//    public Byte getType() {
//        return type;
//    }
//
//    public void setType(Byte type) {
//        this.type = type;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Comment getComment() {
//        return comment;
//    }
//
//    public void setComment(Comment comment) {
//        this.comment = comment;
//    }
//
//    public Message getMessage() {
//        return message;
//    }
//
//    public void setMessage(Message message) {
//        this.message = message;
//    }
}
