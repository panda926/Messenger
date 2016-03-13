package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class ClassTypeListInfo extends BaseInfo {

	public ArrayList<ClassTypeInfo> _ClassType = new ArrayList<ClassTypeInfo>();
	
	public ClassTypeListInfo() {
		_InfoType = InfoType.ClassTypeList;
	}

	@Override
	public int GetSize() {
		
		int size = super.GetSize() + 4;

        for (int i = 0; i < _ClassType.size(); i++)
            size += _ClassType.get(i).GetSize();

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
        try
        {
            super.GetBytes(bo);

            EncodeInteger(bo, _ClassType.size());

            for (int i = 0; i < _ClassType.size(); i++)
                _ClassType.get(i).GetBytes(bo);
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
		
        int count = DecodeInteger(is);

        for (int i = 0; i < count; i++)
        {
            ClassTypeInfo classTypeInfo = new ClassTypeInfo();
            DecodeInteger(is);

            classTypeInfo.FromBytes(is);

            _ClassType.add(classTypeInfo);
        }
	}

}
