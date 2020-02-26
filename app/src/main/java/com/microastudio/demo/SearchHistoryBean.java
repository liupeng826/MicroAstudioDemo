package com.microastudio.demo;

import java.util.Date;


public class SearchHistoryBean {
	private String searchTitle;//搜索的标题
	private Date searchDate;//搜索的时间（如果重新搜索了的话，只需要更新搜索时间即可，不需要添加）

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
}
