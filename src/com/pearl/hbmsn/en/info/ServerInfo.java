package com.pearl.hbmsn.en.info;

import java.io.BufferedOutputStream;
import java.io.InputStream;

public class ServerInfo extends BaseInfo {

	public String ServerAddress = "";
    public int ServerPort;
    public String DownloadAddress = "";
    
	public ServerInfo() {
		_InfoType = InfoType.Server;
	}

	@Override
	public int GetSize() {
		int size = super.GetSize();

        size += EncodeCount(ServerAddress);
        size += EncodeCount(ServerPort);
        size += EncodeCount(DownloadAddress);

        return size;
	}

	@Override
	public void GetBytes(BufferedOutputStream bo) {
        super.GetBytes(bo);

        EncodeString( bo, ServerAddress );
        EncodeInteger(bo, ServerPort);
        EncodeString(bo, DownloadAddress);
	}

	@Override
	public void FromBytes(InputStream is) {
        super.FromBytes(is);

        ServerAddress = DecodeString(is);
        ServerPort = DecodeInteger(is);
        DownloadAddress = DecodeString(is);
	}

}
