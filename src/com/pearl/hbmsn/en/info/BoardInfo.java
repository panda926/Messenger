package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Date;

public class BoardInfo extends BaseInfo {

	public int Id = 0;
    public int Kind = BoardKind.Notice.ordinal();

    public String Title = "";
    public String Content = "";
    public String Source = "";
    public Date WriteTime;
    public String UserId = "";
    public int UserKind = 0;
    public int Readed = 0;
    public String SendId = "";
    
	public BoardInfo() {
		_InfoType = InfoType.Board;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount(Id);
        size += EncodeCount(Title);
        size += EncodeCount(Content);
        size += EncodeCount(Source);
        size += EncodeCount(ConvDateToLongString(WriteTime));
        size += EncodeCount(UserId);
        size += EncodeCount(UserKind);
        size += EncodeCount(Readed);
        size += EncodeCount(SendId);
        
        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
			super.GetBytes(bo);

            EncodeInteger(bo, Id);
            EncodeString( bo, Title );
            EncodeString(bo, Content );
            EncodeString(bo, Source);
            EncodeString(bo, ConvDateToString(WriteTime));
            EncodeString(bo, UserId);
            EncodeInteger(bo, UserKind);
            EncodeInteger(bo, Readed);
            EncodeString(bo, SendId);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        };
		
	}

	@Override
	public void FromBytes(InputStream is) {
		
		super.FromBytes(is);
		
        Id = DecodeInteger(is);
        Title = DecodeString( is);
        Content = DecodeString( is );
        Source = DecodeString( is );
        WriteTime = ToDateTime(DecodeString(is).toString());
        UserId = DecodeString(is);
        UserKind = DecodeInteger(is);
        Readed = DecodeInteger(is);
        SendId = DecodeString(is);
	}

	
}
