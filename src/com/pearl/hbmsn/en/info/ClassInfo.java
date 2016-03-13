package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class ClassInfo extends BaseInfo {

	 public int Class_Type_Id = 0;    // 20
     public String Class_Name = "";
     public String Class_Type_Name = "";
     public String Class_Img_Uri = "";
     public int ToIndex = 0;    // 20
     public int FromIndex = 0;    // 20
     public int ClassInfo_Id = 0;    // 20
     public int ClassCount = 0;    // 20

	public ClassInfo() {
		_InfoType = InfoType.ClassInfo;
	}

	@Override
	public int GetSize() {
		 return super.GetSize() + EncodeCount(Class_Type_Id) + EncodeCount(Class_Name) + EncodeCount(Class_Type_Name) +
	                EncodeCount(Class_Img_Uri) + EncodeCount(ToIndex) + EncodeCount(FromIndex) + EncodeCount(ClassInfo_Id) + EncodeCount(ClassCount);
	        
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		 try
         {
             super.GetBytes(bo);

             EncodeInteger(bo, Class_Type_Id);
             EncodeString(bo, Class_Name);
             EncodeString(bo, Class_Type_Name);
             EncodeString(bo, Class_Img_Uri);
             EncodeInteger(bo, ToIndex);
             EncodeInteger(bo, FromIndex);
             EncodeInteger(bo, ClassInfo_Id);
             EncodeInteger(bo, ClassCount);
           
         }
         catch (Exception ex)
         {
             ex.printStackTrace();
         }
	}

	@Override
	public void FromBytes(InputStream is) {
		 super.FromBytes(is);

         Class_Type_Id = DecodeInteger(is);
         Class_Name = DecodeString(is);
         Class_Type_Name = DecodeString(is);
         Class_Img_Uri = DecodeString(is);
         ToIndex = DecodeInteger(is);
         FromIndex = DecodeInteger(is);
         ClassInfo_Id = DecodeInteger(is);
         ClassCount = DecodeInteger(is);
	}

}
