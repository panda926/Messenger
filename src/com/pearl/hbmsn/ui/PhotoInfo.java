package com.pearl.hbmsn.ui;



public  class PhotoInfo{
	 public String m_ClassName;
	 public String m_Kind;//����,�Ǵ� ��ȭ�� ����
	 public int m_Count;//���� �Ǵ� ��ȭ�� ����
	 public int m_FolderCount;//���������
	 
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