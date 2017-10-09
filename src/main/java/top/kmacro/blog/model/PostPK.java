package top.kmacro.blog.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Zhangkh on 2017-09-22.
 */
public class PostPK implements Serializable {

    private String id;

    private Date verDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostPK postPK = (PostPK) o;

        if (id != null ? !id.equals(postPK.id) : postPK.id != null) return false;
        return verDate != null ? verDate.equals(postPK.verDate) : postPK.verDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (verDate != null ? verDate.hashCode() : 0);
        return result;
    }
}
