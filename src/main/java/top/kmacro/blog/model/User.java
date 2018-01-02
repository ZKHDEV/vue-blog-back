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
    private String nickName;
    private String avatar;
    private String code;
    private String token;
    @Column(nullable = false)
    private Boolean auth;
    private Date overTime;
    @Column(nullable = false)
    private Date signTime;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<SavePost> savePostSet;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<PublishPost> pubPostSet;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<VersionPost> verPostSet;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Category> categorySet;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "mb_fav_post", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") })
    private Set<PublishPost> favPostSet;
    @OneToMany(mappedBy = "srcUser",fetch = FetchType.LAZY)
    private Set<Message> sendMsgSet;
    @OneToMany(mappedBy = "tarUser",fetch = FetchType.LAZY)
    private Set<Message> receiveMsgSet;
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private Set<Notice> noticeSet;
    @ManyToMany(mappedBy = "likeUserSet",fetch = FetchType.LAZY)
    private Set<PublishPost> likePostSet;


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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Set<SavePost> getSavePostSet() {
        return savePostSet;
    }

    public void setSavePostSet(Set<SavePost> savePostSet) {
        this.savePostSet = savePostSet;
    }

    public Set<PublishPost> getPubPostSet() {
        return pubPostSet;
    }

    public void setPubPostSet(Set<PublishPost> pubPostSet) {
        this.pubPostSet = pubPostSet;
    }

    public Set<VersionPost> getVerPostSet() {
        return verPostSet;
    }

    public void setVerPostSet(Set<VersionPost> verPostSet) {
        this.verPostSet = verPostSet;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public Set<PublishPost> getFavPostSet() {
        return favPostSet;
    }

    public void setFavPostSet(Set<PublishPost> favPostSet) {
        this.favPostSet = favPostSet;
    }

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
}
