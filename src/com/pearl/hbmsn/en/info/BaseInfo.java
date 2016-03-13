package com.pearl.hbmsn.en.info;


import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.util.EncodingUtils;

import com.pearl.hbmsn.en.network.HBConstant;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

public class BaseInfo {

	protected InfoType _InfoType;
	
	
	public static BaseInfo CreateInstance(InputStream bs){
		
		BaseInfo baseInfo = null;
		
		InfoType infoType = InfoType.values()[DecodeInteger(bs)];//from int to enum
		
		switch(infoType){
		
		case AVMsg:
			baseInfo = new AVMsg();
			break;
		case AddScore:
			baseInfo = new AddScoreInfo();
			break;
		case AskChat:
			baseInfo = new AskChatInfo();
			break;
		case Betting:
			baseInfo = new BettingInfo();
			break;
		case Board:
			baseInfo = new BoardInfo();
			break;
		case BumperCar:
			baseInfo = new BumpCarInfo();
			break;
		case ChargeHistory:
			baseInfo = new ChargeHistoryInfo();
			break;
		case ChatHistory:
			baseInfo = new ChatHistoryInfo();
			break;
		case ClassInfo:
			baseInfo = new ClassInfo();
			break;
		case ClassList:
			baseInfo = new ClassListInfo();
			break;
		case ClassPictureDetail:
			baseInfo = new ClassPictureDetailInfo();
			break;
		case ClassTypeInfo:
			baseInfo = new ClassTypeInfo();
			break;
		case ClassTypeList:
			baseInfo = new ClassTypeListInfo();
			break;
		case DWGameFile:
			baseInfo = new DWGameFileInfo();
			break;
		case Dice:
			baseInfo = new DiceInfo();
			break;
		case DzCard:
			baseInfo = new DzCardInfo();
			break;
		case EvalHistory:
			baseInfo = new EvalHistoryInfo();
			break;
		case Fish:
			break;
		case FishSend:
			break;
		case Game:
			baseInfo = new GameInfo();
			break;
		case GameDetail:
			baseInfo = new GameDetailInfo();
			break;
		case GameHistory:
			baseInfo = new GameHistoryInfo();
			break;
		case GameList:
			baseInfo = new GameListInfo();
			break;
		case Give:
			baseInfo = new GiveInfo();
			break;
		case Header:
			baseInfo = new HeaderInfo();
			break;
		case Home:
			baseInfo = new HomeInfo();
			break;
		case Horse:
			break;
		case Musice:
			baseInfo = new MusiceInfo();
			break;
		case MusiceState:
			baseInfo = new MusiceStateInfo();
			break;
		case Payment:
			baseInfo = new PaymentInfo();
			break;
		case PointHistory:
			baseInfo = new PointHistoryInfo();
			break;
		case Present:
			baseInfo = new IconInfo();
			break;
		case PresentHistory:
			baseInfo = new PresentHistoryInfo();
			break;
		case Result:
			baseInfo = new ResultInfo();
			break;
		case Room:
			baseInfo = new RoomInfo();
			break;
		case RoomDetail:
			baseInfo = new RoomDetailInfo();
			break;
		case RoomList:
			baseInfo = new RoomListInfo();
			break;
		case RoomValue:
			baseInfo = new RoomPrice();
			break;
		case SendCard:
			baseInfo = new SendCardInfo();
			break;
		case Server:
			baseInfo = new ServerInfo();
			break;
		case Sicbo:
			break;
		case String:
			baseInfo = new StringInfo();
			break;
		case Table:
			baseInfo = new TableInfo();
			break;
		case User:
			baseInfo = new UserInfo();
			break;
		case UserDetail:
			baseInfo = new UserDetailInfo();
			break;
		case UserList:
			baseInfo = new UserListInfo();
			break;
		case UserState:
			baseInfo = new UserState();
			break;
		case Video:
			baseInfo = new VideoInfo();
			break;
		case Voice:
			baseInfo = new VoiceInfo();
			break;
		default:
			break;
		}
		
		if(baseInfo != null){
			baseInfo.FromBytes(bs);
		}
		return baseInfo;
		
	}
	public static int EncodeCount(String s){
		int byteCount = 0;
		try {
			byteCount = s.getBytes("GB2312").length + 4;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteCount;
	}
	public static int EncodeCount(int value){
		return 4;
	}
	public static void EncodeString(BufferedOutputStream bo, String str){
		int byteCount = EncodeCount(str) - 4;
		EncodeInteger(bo, byteCount);
		try {
			//byte[] GB2312Byte = str.getBytes("GB2312");
			bo.write(str.getBytes("GB2312"), 0, byteCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static String DecodeString(InputStream bi){
		
		String dest = "";
		int byteCount = DecodeInteger(bi);
		if(byteCount <= 0)
			return dest;
		byte readByte[] = new byte[byteCount];
		
		try {
			bi.read(readByte, 0, byteCount);
			String source = EncodingUtils.getString( readByte, 0, byteCount, "GB2312") ;
			dest = source;
			int index = dest.indexOf('\0');
			if(index > -1){
				dest = source.substring(0, index + 1);
			}
			dest.trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}
	public static void EncodeInteger(BufferedOutputStream bo, int num){
		DataOutputStream dos = new DataOutputStream(bo);
		try {
			dos.writeInt(num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int DecodeInteger(InputStream is){
		
		DataInputStream dis = new DataInputStream(is);
		int num = 0;
		
		try {
			num = dis.readInt();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return num; 
	}
	
	public static void EncodeLong(BufferedOutputStream bo, long num){
	   DataOutputStream dos = new DataOutputStream(bo);
		try {
			dos.writeLong(num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static long DecodeLong(InputStream is){
		DataInputStream dis = new DataInputStream(is);
		long num = 0;
		try {
			num = dis.readLong();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	public static String ConvDateToString(Date date){
		String dateString = "";
		//2014-04-18 04:15:33
		dateString = DateFormat.format(HBConstant._TimeFormat, date).toString();
		return dateString;
	}
	public static String ConvDateToLongString(Date date){
		return DateFormat.format(HBConstant._TimeFormat, date).toString();
	}
	@SuppressLint("SimpleDateFormat") public static Date ToDateTime(String timeString){
		SimpleDateFormat format = new SimpleDateFormat(HBConstant._TimeFormat);
		Date dateTime = null;
		try {
			dateTime = format.parse(timeString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateTime;
		
	}
	public BaseInfo(){
		this._InfoType = InfoType.None;
	}
	
	public int GetSize(){
		return 4;
	}
	public void GetBytes(BufferedOutputStream bo){
		EncodeInteger(bo, _InfoType.ordinal());
	}
	public void FromBytes(InputStream is){
		
	}

}
