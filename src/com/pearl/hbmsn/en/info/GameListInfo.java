package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class GameListInfo extends BaseInfo {

	private ArrayList<GameInfo> _Games = new ArrayList<GameInfo>();
	
	public GameListInfo() {
		_InfoType = InfoType.GameList;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize() + 4;

        for (int i = 0; i < _Games.size(); i++)
        {
            size += _Games.get(i).GetSize();
        }

        return size;
	}

	public ArrayList<GameInfo> GetGames(){
		return _Games;
	}
	public void SetGames(ArrayList<GameInfo> games){
		_Games = games;
	}
	@Override
	public void GetBytes(BufferedOutputStream bo) {
		
        try
        {
            super.GetBytes(bo);
            EncodeInteger(bo, _Games.size());

            for (int i = 0; i < _Games.size(); i++)
                _Games.get(i).GetBytes(bo);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace(); 
        }

	}

	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        int count = DecodeInteger(is);

        for (int i = 0; i < count; i++)
        {
            GameInfo gameInfo = new GameInfo();
            DecodeInteger(is);

            gameInfo.FromBytes(is);

            _Games.add(gameInfo);
        }
	}

}
