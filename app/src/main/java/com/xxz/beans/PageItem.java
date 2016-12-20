package com.xxz.beans;

import java.util.List;

/**
 * 分页对象
 * Created with Android Studio
 * <p/>
 * Author:bin
 * <p/>
 * Date: 2015/10/13.
 */
public class PageItem<T> {
    public static final int FIRST_PAGE_INDEX = 1;

    public static final int FLUSH_TYPE_NORMAL = 2;//普通刷新方式
    public static final int FLUSH_TYPE_RECOMMAND = 1; //推荐刷新方式

    /**
     * status : 200
     * total : 3
     * pagecount : 1
     * pagesize : 20
     * pagenum : 1
     * rows : []
     */
    private int code;
    private int status;
    private int total;
    private int pagecount;
    private int pagesize;
    private int pagenum;
    private int hasnew;//是否有新数据（1:有  0：没有）  用于首页推荐频道 判断使用
    int subid; //订阅id
    int ismax; //订阅数是否已达最大数（1：是 0：否）
    int subtype; //订阅类型（1：标签 2：主播 3：游戏 4：游戏分类）
    int subscribe;//是否订阅（1：已订阅 0：未订阅）
    private int hassub;//是否有订阅数据（1：有 0:没有）
    private List<T> rows;
    private List<T> area;
    int flushtype;

    public void setArea(List<T> area) {
        this.area = area;
    }

    public int getHassub() {
        return hassub;
    }

    public void setHassub(int hassub) {
        this.hassub = hassub;
    }

    public int getSubtype() {
        return subtype;
    }

    public void setSubtype(int subtype) {
        this.subtype = subtype;
    }

    public int getIsmax() {
        return ismax;
    }

    public void setIsmax(int ismax) {
        this.ismax = ismax;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean hasNew() {
        return hasnew == 1;
    }

    public void setHasnew(int hasnew) {
        this.hasnew = hasnew;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public List<T> getRows() {
        return rows;
    }

    public List<T> getArea() {
        return area;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public boolean isFirstPage() {
        return pagenum == FIRST_PAGE_INDEX;
    }

    public int getFlushtype() {
        return flushtype;
    }

    public void setFlushtype(int flushtype) {
        this.flushtype = flushtype;
    }
}
