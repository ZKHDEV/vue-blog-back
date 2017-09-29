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
}
