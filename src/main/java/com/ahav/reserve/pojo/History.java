package com.ahav.reserve.pojo;

import com.github.pagehelper.PageInfo;

public class History {
    private PageInfo<MeetingDetails> page;  //分页记录
    private long total; //总记录数
    private int pages; //总页数
    private long deMeetingCount; //场次共计
    private String  deDateCount; //用时共计

    public PageInfo<MeetingDetails> getPage() {
        return page;
    }

    public void setPage(PageInfo<MeetingDetails> page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getDeMeetingCount() {
        return deMeetingCount;
    }

    public void setDeMeetingCount(long deMeetingCount) {
        this.deMeetingCount = deMeetingCount;
    }

    public String getDeDateCount() {
        return deDateCount;
    }

    public void setDeDateCount(String deDateCount) {
        this.deDateCount = deDateCount;
    }

    @Override
    public String toString() {
        return "History{" + "page=" + page + ", total=" + total + ", pages=" + pages + ", deMeetingCount=" + deMeetingCount + ", deDateCount=" + deDateCount + '}';
    }
}
