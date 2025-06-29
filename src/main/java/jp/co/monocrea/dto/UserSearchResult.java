package jp.co.monocrea.dto;

import jp.co.monocrea.entity.User;
import java.util.List;

import jakarta.json.bind.annotation.JsonbNillable;

@JsonbNillable
public class UserSearchResult {
    private List<User> userList;
    private Integer searchId;
    private String searchUserName;
    private String sortItemName;
    private String orderType;
    private int limit;
    private int pageNo;
    private long maxPageNo;
    private long totalCnt;

    public List<User> getUserList() { return userList; }
    public Integer getSearchId() { return searchId; }
    public String getSearchUserName() { return searchUserName; }
    public String getSortItemName() { return sortItemName; }
    public String getOrderType() { return orderType; }
    public int getLimit() { return limit; }
    public int getPageNo() { return pageNo; }
    public long getMaxPageNo() { return maxPageNo; }
    public long getTotalCnt() { return totalCnt; }

    public void setUserList(List<User> userList) { this.userList = userList; }
    public void setSearchId(Integer searchId) { this.searchId = searchId; }
    public void setSearchUserName(String searchUserName) { this.searchUserName = searchUserName; }
    public void setSortItemName(String sortItemName) { this.sortItemName = sortItemName; }
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public void setLimit(int limit) { this.limit = limit; }
    public void setPageNo(int pageNo) { this.pageNo = pageNo; }
    public void setMaxPageNo(long maxPageNo) { this.maxPageNo = maxPageNo; }
    public void setTotalCnt(long totalCnt) { this.totalCnt = totalCnt; }
}