package top.kmacro.blog.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by ms-zk on 2017-08-20.
 */
@Entity
@Table(name = "mb_user")
public class User implements Serializable{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    @Column(nullable = false)
    private String phone;
    private String code;
    private String token;
    @Column(nullable = false)
    private Boolean auth;
    private Date overTime;
    @Column(nullable = false)
    private Date signTime;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Post> postSet;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Category> categorySet;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "mb_fav_post", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") })
    private Set<Post> favPostSet;
    @OneToMany(mappedBy = "srcUser",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Message> sendMsgSet;
    @OneToMany(mappedBy = "tarUser",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Message> receiveMsgSet;
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private Set<Notice> noticeSet;

    public Set<Message> getSendMsgSet() {
        return sendMsgSet;
    }

    public void setSendMsgSet(Set<Message> sendMsgSet) {
        this.sendMsgSet = sendMsgSet;
    }

    public Set<Message> getReceiveMsgSet() {
        return receiveMsgSet;
    }

    public void setReceiveMsgSet(Set<Message> receiveMsgSet) {
        this.receiveMsgSet = receiveMsgSet;
    }

//    public Set<Notice> getNoticeSet() {
//        return noticeSet;
//    }
//
//    public void setNoticeSet(Set<Notice> noticeSet) {
//        this.noticeSet = noticeSet;
//    }

    public Set<Post> getFavPostSet() {
        return favPostSet;
    }

    public void setFavPostSet(Set<Post> favPostSet) {
        this.favPostSet = favPostSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Set<Post> getPostSet() {
        return postSet;
    }

    public void setPostSet(Set<Post> postSet) {
        this.postSet = postSet;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }
}
