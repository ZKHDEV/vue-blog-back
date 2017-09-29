package top.kmacro.blog.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mb_save_post")
public class SavePost extends BasePost{
    @Id
    @Column(nullable = false, length = 32)
    private String id;
    private Date verDate;
    private Date createDate;
    private Boolean recycle;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "mb_cate_post_save", joinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "cate_id", referencedColumnName = "id") })
    private Set<Category> categorySet;

    public SavePost() {
        super();
        recycle = false;
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

    public Boolean getRecycle() {
        return recycle;
    }

    public void setRecycle(Boolean recycle) {
        this.recycle = recycle;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }
}
