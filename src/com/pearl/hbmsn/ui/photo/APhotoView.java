package com.pearl.hbmsn.ui.photo;



import java.util.ArrayList;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.info.ClassPictureDetailInfo;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.ui.ConvertMgr;
import com.pearl.hbmsn.ui.connector.FullScreenPhotoAdapter;




import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

public class APhotoView extends Activity {

	
	private FullScreenPhotoAdapter fullScreenImageAdapter;
	private ViewPager pager;
	public	APhotoView Instance;
	private  int m_TotalCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen_view);
		pager = (ViewPager)findViewById(R.id.pager);
		Instance = this;
		
		ArrayList<String> urlArray = getImageUrlArray(CurrentInfo._ClassPictureDetailInfo);
		m_TotalCount = urlArray.size();
		ViewEnable(urlArray);
		
	}
	private ArrayList<String> getImageUrlArray(ClassPictureDetailInfo pictureDetailInfo){
		
		ArrayList<String> imagUrlArray = new ArrayList<String>();
		
		for(int nId = 0; nId < pictureDetailInfo.ClassType.size(); nId++){
			
			imagUrlArray.add(ConvertMgr.getEcodeUrl(this, pictureDetailInfo.ClassType.get(nId).Class_File_Name));
		}
		return imagUrlArray;
		
	}
	
	
	public void SetPageNumber(int nPageNumber){
		 String strPageNumber = String.valueOf(nPageNumber) + "/" + String.valueOf(m_TotalCount) + 
				 getString(R.string.photo_unit);
		 TextView tvPageNumber = (TextView)findViewById(R.id.tv_page_number);
		 tvPageNumber.setText(strPageNumber);
	}
	
	private void ViewEnable(ArrayList<String> urlArray){
		fullScreenImageAdapter = new FullScreenPhotoAdapter(this, urlArray);
        pager.setAdapter(fullScreenImageAdapter);
        pager.setCurrentItem(0);
        SetPageNumber(1);
        pager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}

			@Override
			public void onPageSelected(int position) {
				SetPageNumber(position + 1);
			}
        	
        });
	}
	
	

	

}
