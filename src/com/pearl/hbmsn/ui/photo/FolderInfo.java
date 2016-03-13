package com.pearl.hbmsn.ui.photo;

import android.graphics.Bitmap;

/*
 * 사진폴더정보를 보관하는 클라스
 * */
public class FolderInfo {
	Bitmap	_Image;//폴더를 보여주는 이메지
	String	_Name;//폴더명
	String	_Time;//폴더가 창조된 시간
	String	_Kind;//폴더의 종류
	int		_Count;//폴더에 포함되여 있는 사진의 수량
	String  _ImageUrl;//이미지 URL
	
	public FolderInfo(Bitmap image, String name, String time, String kind, int count, String imageUrl){
		_Image = image;
		_Name = name;
		_Time = time;
		_Kind = kind;
		_Count = count;
		_ImageUrl = imageUrl;
	}
	public void setBitmap(Bitmap bm){
		_Image = bm;
	}
	public Bitmap getBitmap(){
		return _Image;
	}
	public void setImageSrc(String Url){
		_ImageUrl = Url;
	}
	public String getImageSrc(){
		return _ImageUrl;
	}
}
