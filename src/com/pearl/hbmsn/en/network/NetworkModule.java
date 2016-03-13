package com.pearl.hbmsn.en.network;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.pearl.hbmsn.en.info.BaseInfo;
import com.pearl.hbmsn.en.info.HeaderInfo;
import com.pearl.hbmsn.en.info.NotifyType;

import android.os.Handler;
import android.os.Message;


public class NetworkModule{
	
	 private static NetworkModule Instance;
	 public static Socket cSocket = null;
	 public static final int RETURN_NORMAL = 0;
	 public static final int RETURN_ERROR = 1;
	 public static final int RETURN_RECONNECT = 2;
	 
	 public BufferedOutputStream streamOut = null;
	 public InputStream streamIn = null;
	 
	 public static NetworkThread cThread = null;
	 public static ReconnectThread reconnectThread = null;
	 public static boolean _loginState = false;
	 public Boolean reconnectLoopFlag;
	 private Handler m_handler;
	 public Boolean _ListentFlag = false;
	 public static Boolean _ReconnectState = false;
	 
	public static NetworkModule GetInstance(){
		if(Instance == null)
			Instance = new NetworkModule();
		return Instance;
	}
	public static void NullInstance(){
		Instance = null;
	}
	


	public void SetHandler(Handler hl){
		m_handler = hl;
	}
	public void ConnectClose(){
		
		try {
			if(cThread != null){
				cThread.flag = true;
				cThread.interrupt();
				cThread = null;
			}
			if(cSocket != null){
				cSocket.getInputStream().close();
				cSocket.close();
				cSocket = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void ReconnectTaskTerminate(){
		reconnectThread.taskLoop = false;
		reconnectThread.interrupt();//KGU 2014 -09 -04
		reconnectThread = null;
	}
	
	
	public int sendInfo(NotifyType notifyType, BaseInfo sendInfo) {
	    	
		int ErrorType = RETURN_NORMAL;
    	try {
    		
    		if(_ReconnectState){
				return RETURN_RECONNECT;//���� ���� ��ð��û���¶�� ���ۿ䱸�� ����Ѵ�.
			}
			//Packet = HeaderInfo + NotifyType + BaseInfo
    		int sendSize = 4 + sendInfo.GetSize();//NotifyType������ 4�� �����ش�
	    	HeaderInfo headerInfo = new HeaderInfo();
	    	headerInfo._BodySize = sendSize;
	    	headerInfo.GetBytes(streamOut);
	    	BaseInfo.EncodeInteger(streamOut, notifyType.ordinal());
	    	sendInfo.GetBytes(streamOut);
			streamOut.flush();
			_ListentFlag = true;
			ErrorType = RETURN_NORMAL;//���������� ����
				
		}
    	
    	catch (Exception e) {
			e.printStackTrace();
			//ConnectClose();
			ErrorType = RETURN_ERROR;//�����߻�
			
		}
    	
    	return ErrorType;
	 }
	 public void connect() throws IOException
	 {	
		 if(cThread == null)
		 {
			 try{
				 cThread = new NetworkThread();
				 cThread.start();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
	 }
	 public void ReconnectTaskStart(){
		 
		 CurrentInfo._EndPingTime = System.currentTimeMillis();
		 if(reconnectThread == null){
			 reconnectThread = new ReconnectThread();
			 reconnectThread.start();
		 }
		 
	 }
	 public void Reconnect(){
		 
//		 reconnectLoopFlag = true;
//		 
//		 while(reconnectLoopFlag){
			 try {
				 _ReconnectState = true;
				ConnectClose();
				connect();
//				Thread.sleep(10000);//10�ʿ� �ѹ��� �ð��û�� ������
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		 }
	 }
	 
	class ReconnectThread extends Thread{
		
		public boolean taskLoop = true;
		
		public void run(){
			
			long timeSharp = 3 * 60 * 1000;//3��
			
			while(taskLoop){
				
				try{
					if(_loginState == false){
						continue;
					}
					if(_ReconnectState){
						continue;//���� ���� ��ð���¶�� 
					}
					
					long currentTime = System.currentTimeMillis();
					
					if((currentTime - CurrentInfo._EndPingTime) > timeSharp){//�������� �ð��� �������ٰ� �Ǵ�
						Reconnect();
						CurrentInfo._EndPingTime = System.currentTimeMillis();
					}
					sleep(5 * 1000);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	class NetworkThread extends Thread
    {
		public boolean flag = false; // ������ ����(����)�� �÷���
		
		public void run() 
		{
		
			try 
			{
				
				cSocket = new Socket(HBConstant._ServerIP, HBConstant._ServerPort);
				
				reconnectLoopFlag = false;
				
			    streamOut = new BufferedOutputStream(cSocket.getOutputStream());      // ��¿� ��Ʈ��
			    streamIn = cSocket.getInputStream();  // �Է¿� ��Ʈ��
			   
			    if(_ReconnectState){
					_ReconnectState = false;
					sendInfo(NotifyType.Request_Login, CurrentInfo._UserInfo);
				}
				    
				while (!flag) 
				{ 
					if(_ListentFlag)
					{
						HeaderInfo headerInfo = (HeaderInfo)BaseInfo.CreateInstance(streamIn);
						if(!headerInfo._Header.contentEquals(HeaderInfo.Marker))
							continue;
						NotifyType notifyType = NotifyType.values()[BaseInfo.DecodeInteger(streamIn)];
						BaseInfo baseInfo = BaseInfo.CreateInstance(streamIn);
						
						Message msg = Message.obtain();
						msg.what = notifyType.ordinal();
						msg.obj = baseInfo;						
						m_handler.sendMessage(msg);
					}
				}  
			}
			catch(Exception e) 
			{//������ �׾����Ƿ� �ð��� ���´�.
				e.printStackTrace();
			}
		}
		
	}

	
	    
}
