package top.kmacro.blog.model.vo;

/**
 * Created by Zhangkh on 2017-09-14.
 */
public class PageVo<T> {
    private Long total;
    private T data;

    public PageVo() {
    }

    public PageVo(Long total, T data) {
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

    public void setData(T data) {
        this.data = data;
    }
}
