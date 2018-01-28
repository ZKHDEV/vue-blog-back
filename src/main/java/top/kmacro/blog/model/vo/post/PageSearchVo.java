package top.kmacro.blog.model.vo.post;

public class PageSearchVo {
    private String uid;
    private String cateId;
    private Integer pageSize;
    private Integer pageNum;

    public PageSearchVo() {
        pageSize = 6;
        pageNum = 1;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
