package top.kmacro.blog.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mb_version_post")
@IdClass(PostPK.class)
public class VersionPost extends BasePost {
    @Id
    @Column(nullable = false, length = 32)
    private String id;
    @Id
    @Column(nullable = false)
    private Date verDate;
    private Date createDate;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "mb_cate_post_ver", joinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id"),@JoinColumn(name = "ver_date", referencedColumnName = "verDate") }, inverseJoinColumns = { @JoinColumn(name = "cate_id", referencedColumnName = "id") })
    private Set<Category> categorySet;

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

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }
}
