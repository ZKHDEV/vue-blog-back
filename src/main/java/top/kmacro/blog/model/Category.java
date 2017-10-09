package top.kmacro.blog.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Zhangkh on 2017-08-27.
 */
@Entity
@Table(name = "mb_cate")
public class Category implements Serializable{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    private String label;
    private Date createDate;
    private Date verDate;
    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    private User user;
    @ManyToMany(mappedBy = "categorySet",fetch = FetchType.LAZY)
    private Set<SavePost> savePostSet;
    @ManyToMany(mappedBy = "categorySet",fetch = FetchType.LAZY)
    private Set<PublishPost> pubPostSet;
    @ManyToMany(mappedBy = "categorySet",fetch = FetchType.LAZY)
    private Set<VersionPost> verPostSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getVerDate() {
        return verDate;
    }

    public void setVerDate(Date verDate) {
        this.verDate = verDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
