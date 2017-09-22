package top.kmacro.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Zhangkh on 2017-09-14.
 */
@Entity
@Table(name = "mb_code")
public class Code {
    @Id
    @Column(nullable = false)
    private Integer val;
    @Column(nullable = false)
    private String txt;

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
