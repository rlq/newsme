package com.he.data.first;

import java.io.Serializable;

public class FirstNewsItem implements Serializable {

	private String id;
	private String favortNums;
	private String commentNums;

	public FirstNewsItem(){

	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFavortNums() {
		return favortNums;
	}

	public void setFavortNums(String favortNums) {
		this.favortNums = favortNums;
	}

	public String getCommentNums() {
		return commentNums;
	}

	public void setCommentNums(String commentNums) {
		this.commentNums = commentNums;
	}
}
