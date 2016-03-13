package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class ResultInfo extends BaseInfo {

	private ErrorType _ErrorType;
	
	public ResultInfo() {
		_InfoType = InfoType.Result;
	}

	public ErrorType GetErrorType(){
		return _ErrorType;
	}
	public void SetErrorType(ErrorType errorType){
		_ErrorType = errorType;
	}
	@Override
	public int GetSize() {
		return super.GetSize();
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		
		try{
		
			super.GetBytes(bo);
			EncodeInteger(bo, _ErrorType.ordinal());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	@Override
	public void FromBytes(InputStream is) {
		
		super.FromBytes(is);
		_ErrorType = ErrorType.values()[DecodeInteger(is)];
	}

}
