package com.pearl.hbmsn.ui.connector;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ChatView extends ScrollView {

	private Context _Context = null;
	private LinearLayout _LinearLayout = null;
	
	public ChatView(Context context) {
		super(context);

		_Context = context;
	
		_LinearLayout = new LinearLayout(context);
		_LinearLayout.setOrientation(LinearLayout.VERTICAL);
		_LinearLayout.setLayoutParams(
        		new LinearLayout.LayoutParams(
        				ViewGroup.LayoutParams.MATCH_PARENT,
        				ViewGroup.LayoutParams.MATCH_PARENT,
        				0.0F
        		)
        );
		
		this.addView(_LinearLayout);
	}
	
	public void addLeftChatMessage(Bitmap userPicture, String text) {
		ChatMessage chatMessage = new ChatMessage(_Context, userPicture, text, ChatMessage.LEFT, _LeftBackgroundImage);
		_LinearLayout.addView(chatMessage);
		
		
		post(new Runnable(){
		    public void run() {
		        fullScroll(ScrollView.FOCUS_DOWN); 
		    }
		});
	}

	public void addRightChatMessage(Bitmap userPicture, String text) {
		ChatMessage chatMessage = new ChatMessage(_Context, userPicture, text, ChatMessage.RIGHT, _RightBackgroundImage);
		_LinearLayout.addView(chatMessage);
		
		post(new Runnable(){
		    public void run() {
		        fullScroll(ScrollView.FOCUS_DOWN); 
		    }
		});
	}
	
	private int _LeftBackgroundImage = 0;

	public int getLeftBackgroundImage() {
		return _LeftBackgroundImage;
	}

	public void setLeftBackgroundImage(int value) {
		_LeftBackgroundImage = value;
	}

	private int _RightBackgroundImage = 0;

	public int getRightBackgroundImage() {
		return _RightBackgroundImage;
	}

	public void setRightBackgroundImage(int value) {
		this._RightBackgroundImage = value;
	}

}
