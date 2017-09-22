package top.kmacro.blog.model.vo;

/**
 * Created by Zhangkh on 2017-09-14.
 */
public class PageVo {
    private Long total;
    private Object data;

    public PageVo() {
    }

    public PageVo(Long total, Object data) {
        this.total = total;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
