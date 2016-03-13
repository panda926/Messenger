package com.pearl.hbmsn.ui.connector;


import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.pearl.hbmsn.R;
import com.pearl.hbmsn.volley.AppController;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FullScreenPhotoAdapter extends PagerAdapter {

	private Activity _activity;
	
	
	private ArrayList<String> _UrlArray;
	private LayoutInflater inflater;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public FullScreenPhotoAdapter(Activity activity, ArrayList<String> urlArray) {
		
		this._activity = activity;
		this._UrlArray = urlArray;
		
	}

	@Override
	public int getCount() {
		return this._UrlArray.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {

		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		TouchImageView imgDisplay;
		
		inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.fullscreen_image, container, false);
		imgDisplay = (TouchImageView)viewLayout.findViewById(R.id.imgDisplay);
		//imgDisplay.setDefaultImageResId(R.drawable.temp);
		
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//		options.inSampleSize = 3;
//		Bitmap bitmap = BitmapFactory.decodeResource(_activity.getResources(), _resIdArray[position], options);
		imgDisplay.setImageUrl(_UrlArray.get(position), imageLoader);
		
		
		((ViewPager)container).addView(viewLayout);
		
		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager)container).removeView((RelativeLayout)object);
	}

}
