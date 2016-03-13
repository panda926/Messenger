package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class UserDetailInfo extends BaseInfo {

	public ArrayList<IconInfo> Faces = new ArrayList<IconInfo>();
    public ArrayList<RoomInfo> Rooms = new ArrayList<RoomInfo>();

    public ArrayList<UserInfo> Partners = new ArrayList<UserInfo>();
    public ArrayList<EvalHistoryInfo> EvalHistories = new ArrayList<EvalHistoryInfo>();
    
    public ArrayList<ChatHistoryInfo> ChatHistories = new ArrayList<ChatHistoryInfo>();
    public ArrayList<ChargeHistoryInfo> ChargeHistories = new ArrayList<ChargeHistoryInfo>();
    public ArrayList<GameHistoryInfo> GameHistories = new ArrayList<GameHistoryInfo>();
    public ArrayList<PresentHistoryInfo> PresentHistories = new ArrayList<PresentHistoryInfo>();

    public String _strDownUrl = "";
    
	public UserDetailInfo() {
		_InfoType = InfoType.UserDetail;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize() + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 4;

        for (int i = 0; i < Faces.size(); i++)
            size += Faces.get(i).GetSize();

        for( int i = 0; i < Rooms.size(); i++ )
            size += Rooms.get(i).GetSize();

        for (int i = 0; i < Partners.size(); i++)
            size += Partners.get(i).GetSize();

        for (int i = 0; i < EvalHistories.size(); i++)
            size += EvalHistories.get(i).GetSize();

        for (int i = 0; i < ChatHistories.size(); i++)
            size += ChatHistories.get(i).GetSize();

        for (int i = 0; i < ChargeHistories.size(); i++)
            size += ChargeHistories.get(i).GetSize();

        for (int i = 0; i < GameHistories.size(); i++)
            size += GameHistories.get(i).GetSize();

        for (int i = 0; i < PresentHistories.size(); i++)
            size += PresentHistories.get(i).GetSize();

        size += EncodeCount(_strDownUrl);
        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
		try
        {
            super.GetBytes(bo);

            EncodeInteger(bo, Faces.size());
            EncodeInteger(bo, Rooms.size());
            EncodeInteger(bo, Partners.size());
            EncodeInteger(bo, EvalHistories.size());
            EncodeInteger(bo, ChatHistories.size());
            EncodeInteger(bo, ChargeHistories.size());
            EncodeInteger(bo, GameHistories.size());
            EncodeInteger(bo, PresentHistories.size());
            
            

            for (int i = 0; i < Faces.size(); i++)
                Faces.get(i).GetBytes(bo);

            for( int i = 0; i < Rooms.size(); i++ )
                Rooms.get(i).GetBytes(bo);

            for (int i = 0; i < Partners.size(); i++)
                Partners.get(i).GetBytes(bo);

            for (int i = 0; i < EvalHistories.size(); i++)
                EvalHistories.get(i).GetBytes(bo);

            for (int i = 0; i < ChatHistories.size(); i++)
                ChatHistories.get(i).GetBytes(bo);

            for (int i = 0; i < ChargeHistories.size(); i++)
                ChargeHistories.get(i).GetBytes(bo);

            for (int i = 0; i < GameHistories.size(); i++)
                GameHistories.get(i).GetBytes(bo);

            for (int i = 0; i < PresentHistories.size(); i++)
                PresentHistories.get(i).GetBytes(bo);


            EncodeString(bo, _strDownUrl);

        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
	}

	@Override
	public void FromBytes(InputStream is) {
		super.FromBytes(is);

        int faceCount = DecodeInteger(is);
        int roomCount = DecodeInteger(is);
        int partnerCount = DecodeInteger(is);
        int evalHistoryCount = DecodeInteger(is);
        int chatHistoryCount = DecodeInteger(is);
        int chargeHistoryCount = DecodeInteger(is);
        int gameHistoryCount = DecodeInteger(is);
        int presentHistoryCount = DecodeInteger(is);



        for (int i = 0; i < faceCount; i++)
        {
            IconInfo faceInfo = (IconInfo)BaseInfo.CreateInstance(is);
            Faces.add(faceInfo);
        }

        for (int i = 0; i < roomCount; i++)
        {
            RoomInfo roomInfo = (RoomInfo)BaseInfo.CreateInstance(is);
            Rooms.add(roomInfo);
        }

        for (int i = 0; i < partnerCount; i++)
        {
            UserInfo partnerInfo = (UserInfo)BaseInfo.CreateInstance(is);
            Partners.add(partnerInfo);
        }

        for (int i = 0; i < evalHistoryCount; i++)
        {
            EvalHistoryInfo evalHistoryInfo = (EvalHistoryInfo)BaseInfo.CreateInstance(is);
            EvalHistories.add(evalHistoryInfo);
        }

        for (int i = 0; i < chatHistoryCount; i++)
        {
            ChatHistoryInfo chatHistoryInfo = (ChatHistoryInfo)BaseInfo.CreateInstance(is);
            ChatHistories.add(chatHistoryInfo);
        }

        for (int i = 0; i < chargeHistoryCount; i++)
        {
            ChargeHistoryInfo chargeHistoryInfo = (ChargeHistoryInfo)BaseInfo.CreateInstance(is);
            ChargeHistories.add(chargeHistoryInfo);
        }

        for (int i = 0; i < gameHistoryCount; i++)
        {
            GameHistoryInfo gameHistoryInfo = (GameHistoryInfo)BaseInfo.CreateInstance(is);
            GameHistories.add(gameHistoryInfo);
        }

        for (int i = 0; i < presentHistoryCount; i++)
        {
            PresentHistoryInfo presentHistoryInfo = (PresentHistoryInfo)BaseInfo.CreateInstance(is);
            PresentHistories.add(presentHistoryInfo);
        }



        _strDownUrl = DecodeString(is);
	}

}
