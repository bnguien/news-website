package model.bean;

public class ArticleSearchCriteria {
	private String searchValue;
    private String categoryIdStr;
    private String timeFilter;
    private int page;
    private int pageSize;
    
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getTimeFilter() {
		return timeFilter;
	}
	public void setTimeFilter(String timeFilter) {
		this.timeFilter = timeFilter;
	}
	public String getCategoryIdStr() {
		return categoryIdStr;
	}
	public void setCategoryIdStr(String categoryIdStr) {
		this.categoryIdStr = categoryIdStr;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
