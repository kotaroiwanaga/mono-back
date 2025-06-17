package jp.co.monocrea.dto;

import jp.co.monocrea.entity.User;
import java.util.List;

import jakarta.json.bind.annotation.JsonbNillable;

@JsonbNillable
public class SearchUserResult {
    private List<User> userList;
    private Integer searchId;
    private String searchUserName;
    private String sortItemName;
    private String order;
    private int limit;
    private int page;
    private long maxPage;
    private long totalCnt;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public String getSearchUserName() {
        return searchUserName;
    }

    public void setSearchUserName(String searchUserName) {
        this.searchUserName = searchUserName;
    }

    public String getSortItemName() {
        return sortItemName;
    }

    public void setSortItemName(String sortItemName) {
        this.sortItemName = sortItemName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(long maxPage) {
        this.maxPage = maxPage;
    }

    public long getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(long totalCnt) {
        this.totalCnt = totalCnt;
    }
}