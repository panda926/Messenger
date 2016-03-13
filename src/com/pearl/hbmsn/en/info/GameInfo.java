package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class GameInfo extends BaseInfo {

	public String GameId = "";         // 20
    public String GameName = "";       // 20

    public int Width = 0;              // 4
    public int Height = 0;             // 4

    public String Icon = "";           // 50
    public String Source = "";         // 50

    public int UserCount = 0;          // 4
    public int Bank = 0;

    public int Commission = 0;

    // 2013-12-18: GreenRose
    public int nCashOrPointGame = -1;    // 캐쉬게임인가 포인트게임인가를 결정한다. 0이면 캐쉬 1이면 포인트게임.

    public String Downloadfolder = "";
    public String RunFile = "";
    
	public GameInfo() {
		_InfoType = InfoType.Game;
	}

	@Override
	public int GetSize() {
		
		int size = super.GetSize();

        size += EncodeCount( GameId );
        size += EncodeCount(GameName);
        size += EncodeCount(Width);
        size += EncodeCount( Height );
        size += EncodeCount(Icon);
        size += EncodeCount(Source);
        size += EncodeCount( UserCount);

        // added by usc at 2014/04/10
        size += EncodeCount(Bank);
        size += EncodeCount(Commission);

        size += EncodeCount(nCashOrPointGame);
        size += EncodeCount(Downloadfolder);
        size += EncodeCount(RunFile);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeString(bo, GameId );
            EncodeString(bo, GameName );
            EncodeInteger(bo, Width);
            EncodeInteger(bo, Height);
            EncodeString(bo, Icon );
            EncodeString(bo, Source );
            EncodeInteger(bo, UserCount);
            
            // added newly
            EncodeInteger(bo, Bank);
            EncodeInteger(bo, Commission);

            EncodeInteger(bo, nCashOrPointGame);
            EncodeString(bo, Downloadfolder);
            EncodeString(bo, RunFile);
        }
        catch (Exception ex)
        {
            ex.printStackTrace(); 
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		
		super.FromBytes(is);

        GameId = DecodeString(is);
        GameName = DecodeString(is);
        Width = DecodeInteger(is);
        Height = DecodeInteger(is);
        Icon = DecodeString(is);
        Source = DecodeString(is);
        UserCount = DecodeInteger(is);

        // added newly
        Bank = DecodeInteger(is);
        Commission = DecodeInteger(is);

        nCashOrPointGame = DecodeInteger(is);
        Downloadfolder = DecodeString(is);
        RunFile = DecodeString(is);
	}
	
	

}
