package com.pearl.hbmsn.ui;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.NotifyType;
import com.pearl.hbmsn.en.info.UserInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.en.network.NetworkModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class FMyInfoView extends Fragment{
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View v = inflater.inflate(R.layout.myinfo_view, container, false);
        Button btnExit = (Button)v.findViewById(R.id.exit);
        try{
        	initData(v);
            btnExit.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View view) {
    				GoLogout();
    			}
            	
            });
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        return v;
    }
    private void initData(View v){
    	
    	UserInfo myInfo = CurrentInfo._UserInfo;
    	ImageView myIcon = (ImageView)v.findViewById(R.id.my_user_photo);
    	int nResId = ConvertMgr.GetResId(myInfo.Icon);
    	myIcon.setImageResource(nResId);//������ ���� ����
    	
    	TextView tvAccount = (TextView)v.findViewById(R.id.my_account);
    	tvAccount.setText(myInfo.Id);//�������̵� ����
    	
    	TextView tvNickName = (TextView)v.findViewById(R.id.my_nick_name);
    	tvNickName.setText(myInfo.NickName);//���� �г��Ӽ���
    	
    	TextView tvBirthday = (TextView)v.findViewById(R.id.my_bithday);
    	String strBuff = "";
    	if(myInfo.Year > 1900){
    		strBuff += String.valueOf(myInfo.Year) + ".";
    		if(myInfo.Month > 0){
    			strBuff += String.valueOf(myInfo.Month) + ".";
    			if(myInfo.Day > 0){
    				strBuff += String.valueOf(myInfo.Day);
    				}
    			}
    		}
    	
    	tvBirthday.setText(strBuff);//������� ����
    	
    	TextView tvEMail = (TextView)v.findViewById(R.id.my_email);
    	tvEMail.setText(myInfo.Address);//�����ּҼ���
    	
    	TextView tvSing = (TextView)v.findViewById(R.id.my_sign);
    	tvSing.setText(myInfo.Sign);//���μ���
    }
    //Ż��ܹ�ư�� Ŭ���ϸ� ������ �ݰ� �α���ȭ������ ����.
    private void GoLogout(){
    	
    	try {
			
    		NetworkModule.GetInstance().sendInfo(NotifyType.Request_Logout, CurrentInfo._UserInfo);
    		NetworkModule.GetInstance().ConnectClose();
    		NetworkModule.GetInstance().ReconnectTaskTerminate();
    		NetworkModule._loginState = false;
			System.gc();
	    	getActivity().finish();
	    	Intent intent = new Intent();
	    	intent.setClass(getActivity(), ALoginView.class);
	    	startActivity(intent);
			
		} 
    	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
   
}

