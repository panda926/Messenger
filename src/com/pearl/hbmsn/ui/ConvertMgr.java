package com.pearl.hbmsn.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;

import com.pearl.hbmsn.R;

public class ConvertMgr {

	public static int[] _ResId= {
		R.drawable.face_1, R.drawable.face_2, R.drawable.face_3, R.drawable.face_4,
		R.drawable.face_5, R.drawable.face_6, R.drawable.face_7, R.drawable.face_8,
		R.drawable.face_9, R.drawable.face_10,R.drawable.face_11,R.drawable.face_12,
		R.drawable.face_13,R.drawable.face_14,R.drawable.face_15,R.drawable.face_16,
		R.drawable.face_17,R.drawable.face_18,R.drawable.face_19,R.drawable.face_20,
		R.drawable.face_21,R.drawable.face_22,R.drawable.face_23,R.drawable.face_24,
		R.drawable.face_25,R.drawable.face_26,R.drawable.face_27,R.drawable.face_28,
		R.drawable.face_29,R.drawable.face_30,R.drawable.face_31,R.drawable.face_32,
		R.drawable.face_33,R.drawable.face_34,R.drawable.face_35,R.drawable.face_36,
		R.drawable.face_37,R.drawable.face_38,R.drawable.face_39,R.drawable.face_40,
		R.drawable.face_41,R.drawable.face_42,R.drawable.face_43,R.drawable.face_44,
		R.drawable.face_45,R.drawable.face_46,R.drawable.face_47,R.drawable.face_48,
		R.drawable.face_49,R.drawable.face_50,R.drawable.face_51,R.drawable.face_52,
		R.drawable.face_53,R.drawable.face_54,R.drawable.face_55,R.drawable.face_56,
		R.drawable.face_57,R.drawable.face_58,R.drawable.face_59,R.drawable.face_60,
		R.drawable.face_default,R.drawable.face_defaultheadimage,R.drawable.face_defaultheadimage
		};
	
	public static String[]  _IconString= {
		"image/face/1.gif", "image/face/2.gif", "image/face/3.gif", "image/face/4.gif",
		"image/face/5.gif", "image/face/6.gif", "image/face/7.gif", "image/face/8.gif",
		"image/face/9.gif", "image/face/10.gif","image/face/11.gif","image/face/12.gif",
		"image/face/13.gif","image/face/14.gif","image/face/15.gif","image/face/16.gif",
		"image/face/17.gif","image/face/18.gif","image/face/19.gif","image/face/20.gif",
		"image/face/21.gif","image/face/22.gif","image/face/23.gif","image/face/24.gif",
		"image/face/25.gif","image/face/26.gif","image/face/27.gif","image/face/28.gif",
		"image/face/29.gif","image/face/30.gif","image/face/31.gif","image/face/32.gif",
		"image/face/33.gif","image/face/34.gif","image/face/35.gif","image/face/36.gif",		
		"image/face/37.gif","image/face/38.gif","image/face/39.gif","image/face/40.gif",
		"image/face/41.gif","image/face/42.gif","image/face/43.gif","image/face/44.gif",
		"image/face/45.gif","image/face/46.gif","image/face/47.gif","image/face/48.gif",
		"image/face/49.gif","image/face/50.gif","image/face/51.gif","image/face/52.gif",
		"image/face/53.gif","image/face/54.gif","image/face/55.gif","image/face/56.gif",
		"image/face/57.gif","image/face/58.gif","image/face/59.gif","image/face/60.gif",		
		"image/face/default.gif","image/face/defaultheadimage.gif","image/face/noimage.gif",
	};
	
	public static int[] _UserStateResId = {
		R.string.online_state,
		R.string.offline_state,
		R.string.busy_state,
		R.string.goway_state
	};
	public static int GetResId(String iconString){
		
		int nResId = 0;
		for(int nId = 0; nId < _IconString.length; nId++){
			if(iconString.contentEquals(_IconString[nId])){
				nResId = _ResId[nId];
				break;
			}
		}
		if(nResId == 0)
			nResId = R.drawable.noimage;
		return nResId;
	}
	
	public static int GetUserStateStringId(int nUserState){
		if(nUserState == 22){
			return _UserStateResId[0];//online
		}
		else if(nUserState < 0 || nUserState > 3)
			return _UserStateResId[1];//off line
		else
			return _UserStateResId[nUserState];
	}
	
	@SuppressLint("SimpleDateFormat") public static String GetCurrentTime(Context context){
		String CurrentTime = "";
		Date now = new Date();
		SimpleDateFormat APFormat = new SimpleDateFormat("a", Locale.CHINA);
		String strAP = APFormat.format(now);
		SimpleDateFormat dataFormat = new SimpleDateFormat("h:m", Locale.CHINA);
		SimpleDateFormat monthFormat = new SimpleDateFormat("M:d", Locale.CHINA);
		CurrentTime = dataFormat.format(now);
		String strMonth = monthFormat.format(now);
		
		if(strAP.contentEquals("AM"))
			strAP = context.getString(R.string.AM);
		else
			strAP = context.getString(R.string.PM);
		
		return CurrentTime = strMonth + " " + strAP + " " + CurrentTime;
	}
	public static String GetDateTimeString(Date date){
		String strDateTime = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		strDateTime = dateFormat.format(date);
		return strDateTime;
	}
	
	public static String getEcodeUrl(Context context, String URL){
		
		if(URL.contentEquals("Temp.jpg")){
			return URL;
		}
    	
		String EncodedUrl = "";
    	String prefix = "http://98.126.54.218";
    	ArrayList<String> tokenArray = new ArrayList<String>();
    	
    	int pos = 0;
    	int offset = 0;
    	
    	try {
    		
	    	while((pos = URL.indexOf('/', offset)) != -1)
	    	{
	    		String token = URL.substring(offset, pos);
	    					
	        	tokenArray.add(URLEncoder.encode(token, "utf-8"));
				
	    		offset = pos + 1;
	    	}
	    	
	    	pos = URL.lastIndexOf('/');
	    	String endToken = URL.substring(pos + 1, URL.length());
			tokenArray.add(URLEncoder.encode(endToken, "utf-8"));
			
			for(int nId = 0; nId < tokenArray.size(); nId++){
				EncodedUrl = EncodedUrl + "/" + tokenArray.get(nId);
			}
			EncodedUrl = prefix + EncodedUrl;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	//후처리로서 아래의 코드는 출현하는 특수기호의 수량에 따라 차후 더 추가될수도 있다.
    	String returnString = EncodedUrl.replace(context.getString(R.string.from_symbol_space), 
    			context.getString(R.string.to_symbol_space));
    	EncodedUrl = returnString;
    	returnString = EncodedUrl.replace(context.getString(R.string.from_symbol_equal), 
    			context.getString(R.string.to_symbol_equal));
    	return returnString;
    	
    }
}
