package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class ClassPictureDetailInfo extends BaseInfo {

	public ArrayList<ClassTypeInfo> ClassType = new ArrayList<ClassTypeInfo>();
	
	public ClassPictureDetailInfo() {
		_InfoType = InfoType.ClassPictureDetail;
	}

	@Override
	public int GetSize() {
		
		int size = super.GetSize() + 4;

        for (int i = 0; i < ClassType.size(); i++)
        {
            size += ClassType.get(i).GetSize();
        }

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeInteger(bo, ClassType.size());

            for (int i = 0; i < ClassType.size(); i++)
                ClassType.get(i).GetBytes(bo);
           
        }
        catch (Exception ex)
        {
            ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        int pictureCount = DecodeInteger(is);
        
        for (int i = 0; i < pictureCount; i++)
        {
            ClassTypeInfo classTypeInfo = (ClassTypeInfo)BaseInfo.CreateInstance(is);
            ClassType.add(classTypeInfo);
        }
	}

}
