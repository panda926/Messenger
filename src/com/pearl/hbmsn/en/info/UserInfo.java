package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Date;


public class UserInfo extends BaseInfo {

	public String Id = "";
	public String Password = "";
	
	public String NickName = "";
	public String Icon = "";
	public String Sign = "";
	public int Year = 0;
	public int Month = 0;
	public int Day = 0;
	public String Address = "";
	
	public int Cash = 0;
	public int Point = 0;
	public Date RegisterTime;
	public String LoginTime = "";
	
	public int Kind = 0;
	static public String[] KindList = {"Kind1", "Kind2", "Kind3", "Kind4", "Kind5", "Kind6", "Kind7"};
	
	public String Friend = "";
	public String Recommender = "";
	public int MaxBuyers = 0;
	public int ChatPercent = 0;
	public int GamePercent = 0;
	public int Evaluation = 0;
	public int Visitors = 0;
	
	public int ChargeSum = 0;
	public int DischargeSum = 0;
	public int ChatSum = 0;
	public int GameSum = 0;
	public int SendSum = 0;
	public int ReceiveSum = 0;
	
	//relative to runtime
	public String RoomId = "";
	public String GameId = "";
	public int UdpPort = 0;
	
	public int userSeat;//user state
	
	//relativr to Server
	public Socket socket;
	public Date PingDate = new Date();
	public int State = 0;
	public int[] OpenPortes = new int[100];
	
	public Date EnterTime;
	public Date CashTime;
	public int ChatHistoryId = 0;
	public int PointHistoryId = 0;
	public int GameHistoryId = 0;
	public int Auto = 0;
	public int WaitSecond = 0;
	
	public String strAccountID = "";
	public String strAccountNumber = "";
	public String strAccountPass = "";
	
	public int nCashOrPointGame = 0;
	
	public String strPhoneNumber = "";
	
	public String strUrl = "";
	
	public int nUserState = 0;
	
	public String strOwnIP = "";
	
	public int nLoginCount = 0;
	
	
	public int nVIP = 0;
	
	public UserInfo() {
		_InfoType = InfoType.User;
	}
	
	public String KindString(){
		return KindList[Kind];
	}

	@Override
	public int GetSize() {
		
		int size = super.GetSize();
		
		size += EncodeCount( Id );         
        size += EncodeCount( Password );

        size += EncodeCount(NickName);
        size += EncodeCount(Icon);
        size += EncodeCount(Sign);
        size += EncodeCount(Year);
        size += EncodeCount(Month);
        size += EncodeCount(Day);
        size += EncodeCount(Address);

        size += EncodeCount(Cash);
        size += EncodeCount(Point);
        //size += EncodeCount(ConvDateToString(RegistTime));
        size += EncodeCount(LoginTime);

        size += EncodeCount( Kind );

        size += EncodeCount(Friend);
        size += EncodeCount(Recommender);
        size += EncodeCount(MaxBuyers);
        size += EncodeCount(ChatPercent);
        size += EncodeCount(GamePercent);
        size += EncodeCount(Evaluation);
        size += EncodeCount(Visitors);

        size += EncodeCount( ChargeSum );
        size += EncodeCount( DischargeSum );
        size += EncodeCount( ChatSum );
        size += EncodeCount( GameSum );
        size += EncodeCount( SendSum );
        size += EncodeCount( ReceiveSum );

        size += EncodeCount( RoomId );
        size += EncodeCount( GameId );
        size += EncodeCount(UdpPort);
        
        size += EncodeCount(userSeat);

        size += EncodeCount(strAccountNumber);
        size += EncodeCount(strAccountID);
        size += EncodeCount(strAccountPass);

        size += EncodeCount(nCashOrPointGame);
        size += EncodeCount(strPhoneNumber);

        size += EncodeCount(strUrl);

        size += EncodeCount(nUserState);

        size += EncodeCount(strOwnIP);
        size += EncodeCount(nVIP);
        
        return size;
		
	}


	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);
		Id = DecodeString(is);
        Password = DecodeString(is);

        NickName = DecodeString(is);
        Icon = DecodeString(is);
        Sign = DecodeString(is);
        Year = DecodeInteger(is);
        Month = DecodeInteger(is);
        Day = DecodeInteger(is);
        Address = DecodeString(is);

        Cash = DecodeInteger(is);
        Point = DecodeInteger(is);
        //RegistTime = Convert.ToDateTime(DecodeString(br).ToString());
        LoginTime = DecodeString(is);

        Kind = DecodeInteger(is);

        Friend = DecodeString(is);
        Recommender = DecodeString(is);
        MaxBuyers = DecodeInteger(is);
        ChatPercent = DecodeInteger(is);
        GamePercent = DecodeInteger(is);
        Evaluation = DecodeInteger(is);
        Visitors = DecodeInteger(is);

        ChargeSum = DecodeInteger(is);
        DischargeSum = DecodeInteger(is);
        ChatSum = DecodeInteger(is);
        GameSum = DecodeInteger(is);
        SendSum = DecodeInteger(is);
        ReceiveSum = DecodeInteger(is);

        RoomId = DecodeString(is);
        GameId = DecodeString(is);
        UdpPort = DecodeInteger(is);

        userSeat = DecodeInteger(is);

        strAccountNumber = DecodeString(is);
        strAccountID = DecodeString(is);
        strAccountPass = DecodeString(is);

        nCashOrPointGame = DecodeInteger(is);

        strPhoneNumber = DecodeString(is);

        strUrl = DecodeString(is);

        nUserState = DecodeInteger(is);

        strOwnIP = DecodeString(is);
        nVIP = DecodeInteger(is);
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		super.GetBytes(bo);
		EncodeString(bo, Id);
        EncodeString(bo, Password);

        EncodeString(bo, NickName);
        EncodeString(bo, Icon);
        EncodeString(bo, Sign);
        EncodeInteger(bo, Year);
        EncodeInteger(bo, Month);
        EncodeInteger(bo, Day);
        EncodeString(bo, Address);

        EncodeInteger(bo, Cash);
        EncodeInteger(bo, Point);
        //EncodeString(bw, ConvDateToString(RegistTime));
        EncodeString(bo, LoginTime);

        EncodeInteger(bo, Kind);

        EncodeString(bo, Friend);
        EncodeString(bo, Recommender);
        EncodeInteger(bo, MaxBuyers);
        EncodeInteger(bo, ChatPercent);
        EncodeInteger(bo, GamePercent);
        EncodeInteger(bo, Evaluation);
        EncodeInteger(bo, Visitors);

        EncodeInteger(bo, ChargeSum);
        EncodeInteger(bo, DischargeSum);
        EncodeInteger(bo, ChatSum);
        EncodeInteger(bo, GameSum);
        EncodeInteger(bo, SendSum);
        EncodeInteger(bo, ReceiveSum);

        EncodeString(bo, RoomId);
        EncodeString(bo, GameId);
        EncodeInteger(bo, UdpPort);

        EncodeInteger(bo, userSeat);

        EncodeString(bo, strAccountNumber);
        EncodeString(bo, strAccountID);
        EncodeString(bo, strAccountPass);

        EncodeInteger(bo, nCashOrPointGame);

        EncodeString(bo, strPhoneNumber);

        EncodeString(bo, strUrl);

        EncodeInteger(bo, nUserState);

        EncodeString(bo, strOwnIP);

        EncodeInteger(bo, nVIP);
	}
	
	public int GetGameMoney(){
		if(nCashOrPointGame == 0){
			return this.Cash;
		}
		else
			return this.Point;
	}
	
	public boolean IsAuto(){
		if(this.Auto > 0)
			return true;
		return false;
	}
	
	

}
