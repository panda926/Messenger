package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;

public class ClassTypeInfo extends BaseInfo {

	public int Class_File_Id = 0;    // 20
    public String Class_File_Name = "";
    public Date Class_File_Date;
    public int Class_File_Type = 0;
    public int Class_File_Area = 0;
    public String Class_Video_Title = "";
    public String Class_Img_Folder = "";
    public int Class_File_Count = 0;
    public int Class_Row_Number = 0;
    
	public ClassTypeInfo() {
		_InfoType = InfoType.ClassTypeInfo;
	}

	@Override
	public int GetSize() {
		return super.GetSize() + EncodeCount(Class_File_Id) + EncodeCount(Class_File_Name) + EncodeCount(ConvDateToString(Class_File_Date)) + 
                EncodeCount(Class_File_Type) + EncodeCount(Class_File_Area) +
                EncodeCount(Class_Video_Title) + EncodeCount(Class_Img_Folder) + EncodeCount(Class_File_Count) + EncodeCount(Class_Row_Number);
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeInteger(bo, Class_File_Id);
            EncodeString(bo, Class_File_Name);
            EncodeString(bo, ConvDateToString(Class_File_Date));
            EncodeInteger(bo, Class_File_Type);
            EncodeInteger(bo, Class_File_Area);
            EncodeString(bo, Class_Video_Title);
            EncodeString(bo, Class_Img_Folder);
            EncodeInteger(bo, Class_File_Count);
            EncodeInteger(bo, Class_Row_Number);
          
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);
		
        Class_File_Id = DecodeInteger(is);
        Class_File_Name = DecodeString(is);
        Class_File_Date = ToDateTime(DecodeString(is));
        Class_File_Type = DecodeInteger(is);
        Class_File_Area = DecodeInteger(is);
        Class_Video_Title = DecodeString(is);
        Class_Img_Folder = DecodeString(is);
        Class_File_Count = DecodeInteger(is);
        Class_Row_Number = DecodeInteger(is);
	}

}
