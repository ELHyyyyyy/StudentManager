package com.fengzheng.programmer.page;

/**
 * @author 风筝丶
 * @create 2020/05/30 15:50
 */
public class Page {
    private int page;//当前页面
    private int rows;//每页显示的数量
    private int offsize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffsize() {
        this.offsize = (page-1)*rows;
        return offsize;
    }

    public void setOffsize(int offsize) {
        this.offsize = (page-1)*rows;
    }
}
