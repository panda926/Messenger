package com.pearl.hbmsn.ui.connector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class ChatMessage extends LinearLayout {
	
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	
	private Context _Context = null;
	private LinearLayout _LinearLayout = null;
	private TextView _Message = null;
	
	private ImageView _UserPicture = null;
	
	public ChatMessage(Context context, Bitmap userPicture, String text, int direction, int image) {
		super(context);
		
		_Context = context;
		
		LinearLayout.LayoutParams paramsMatchParent = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				0.0F
		);

		setOrientation(LinearLayout.VERTICAL);
		setLayoutParams(paramsMatchParent);
		
		LinearLayout.LayoutParams paramsWrapContent = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				0.0F
		);
		
		if (RIGHT == direction) 
			paramsWrapContent.gravity = Gravity.RIGHT;
		else
			paramsWrapContent.gravity = Gravity.LEFT;
		
		_LinearLayout = new LinearLayout(context);
		_LinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		_LinearLayout.setLayoutParams(paramsWrapContent);
		
		
		this.addView(_LinearLayout);
		
		paramsWrapContent.leftMargin = 5;
		paramsWrapContent.topMargin = 10;
		paramsWrapContent.rightMargin = 5;
		
		_UserPicture = new ImageView(_Context);
		_UserPicture.setImageBitmap(userPicture);
		_UserPicture.setLayoutParams(paramsWrapContent);
		
		_Message = new TextView(_Context);
		_Message.setText(text);
		_Message.setBackgroundResource(image);
		_Message.setLayoutParams(paramsWrapContent);
		_Message.setMaxWidth(500);

		if (LEFT == direction) {
			_LinearLayout.addView(_UserPicture);
			_LinearLayout.addView(_Message);
		}
		else {
			_LinearLayout.addView(_Message);
			_LinearLayout.addView(_UserPicture);
		}
	}
	
	public String getText() {
		if (null != _Message) return _Message.getText().toString();
		else return "";
	}

}
