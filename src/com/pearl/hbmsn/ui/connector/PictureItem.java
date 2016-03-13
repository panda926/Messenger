package com.pearl.hbmsn.ui.connector;

/**
 * 
 * @author manish.s
 *
 */

public class PictureItem {
	String imageUrl;
	String title;
	
	public PictureItem(String imageUrl, String title) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImage(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

}
