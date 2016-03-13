package com.pearl.hbmsn.ui;



public  class PhotoInfo{
	 public String m_ClassName;
	 public String m_Kind;//사진,또는 영화의 종류
	 public int m_Count;//사진 또는 영화의 수량
	 public int m_FolderCount;//폴더당수량
	 
	 public PhotoInfo(String className, String photoType, int photoCount, int folderCount){
		 m_ClassName = className;
		 m_Kind = photoType;
		 m_Count = photoCount;
		 m_FolderCount = folderCount;
	 }

	public void setConent(String className, String photoType, int photoCount, int folderCount) {
		m_ClassName = className;
		m_Kind = photoType;
		m_Count = photoCount;
		m_FolderCount = folderCount;
	}
}