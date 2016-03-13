package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class HeaderInfo extends BaseInfo {

	public final static String Marker = "QAZWSXEDCRFVTGBHNUJMIK<OL>";

    public String _Header = Marker;
    public int _BodySize = 0;
    
	public HeaderInfo() {
		_InfoType = InfoType.Header;
	}

	@Override
	public int GetSize() {
		
		return super.GetSize() + EncodeCount(_Header) + EncodeCount(_BodySize);
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		
		super.GetBytes(bo);
		EncodeString(bo, _Header);
		EncodeInteger(bo, _BodySize);
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);
		_Header = DecodeString(is);
		_BodySize = DecodeInteger(is);
	}

}
