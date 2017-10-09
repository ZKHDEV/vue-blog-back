package top.kmacro.blog.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Zhangkh on 2017-09-14.
 */
@Entity
@Table(name = "mb_message")
public class Message implements Serializable{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    private String subject;
    private String content;
    private Date createTime;
    @ManyToOne
    @JoinColumn(name="src_user_id")
    private User srcUser;
    @ManyToOne
    @JoinColumn(name="tar_user_id")
    private User tarUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}
