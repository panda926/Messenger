package com.pearl.hbmsn.ui.photo;

import android.graphics.Bitmap;

/*
 * �������������� �����ϴ� Ŭ��
 * */
public class FolderInfo {
	Bitmap	_Image;//������ �����ִ� �̸���
	String	_Name;//������
	String	_Time;//������ â���� �ð�
	String	_Kind;//������ ����
	int		_Count;//������ ���Եǿ� �ִ� ������ ����
	String  _ImageUrl;//�̹��� URL
	
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
