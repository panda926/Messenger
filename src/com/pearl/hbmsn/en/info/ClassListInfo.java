package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class ClassListInfo extends BaseInfo {

	public ArrayList<ClassInfo> _Classes = new ArrayList<ClassInfo>();
	
	public ClassListInfo() {
		_InfoType = InfoType.ClassList;
	}

	@Override
	public int GetSize() {
		 int size = super.GetSize() + 4;

         for (int i = 0; i < _Classes.size(); i++)
             size += _Classes.get(i).GetSize();

         return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeInteger(bo, _Classes.size());

            for (int i = 0; i < _Classes.size(); i++)
                _Classes.get(i).GetBytes(bo);
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
			Thread.sleep(1000);//KGU 2014-09-04
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        int count = DecodeInteger(is);

        for (int i = 0; i < count; i++)
        {
            ClassInfo classInfo = new ClassInfo();
            DecodeInteger(is);

            classInfo.FromBytes(is);

            _Classes.add(classInfo);
        }
	}

}
