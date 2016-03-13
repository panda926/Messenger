package com.pearl.hbmsn.ui.connector;

import java.util.ArrayList;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.network.CurrentInfo;
import com.pearl.hbmsn.ui.ConvertMgr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;


public class AConnectorPicture extends Activity {

	GridView m_gvPictureView;
	ArrayList<PictureItem> m_ItemArray;
	public	AConnectorPicture Instance;
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.connector_picture);
		
		try{
			init();//Gridview에 현시할 데이터를 가져온다.
			Instance = this;
			m_gvPictureView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position,
						long nRowId) {
				
					//showToast(position);
					PictureDetail(position);
				}
				
			});
			
			TextView tv_BackButton = (TextView)findViewById(R.id.back_detail);
			tv_BackButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					BackDetail();
				}
				
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}	

	private void PictureDetail(int position){
		
		Intent intent = new Intent();
		intent.putExtra("position", position);
		intent.setClass(this, AFullScreenPhoto.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	private void init(){

		m_gvPictureView = (GridView)findViewById(R.id.gv_picture);
		m_ItemArray = new ArrayList<PictureItem>();
//		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//		bmOptions.inSampleSize = 5;
//		bmOptions.inDither = false;

		for(int nId = 0; nId < CurrentInfo._selectUserDetail.Faces.size(); nId++){
			String imageUrl = CurrentInfo._selectUserDetail.Faces.get(nId).Icon.replace("\\", "/");
			CurrentInfo._selectUserDetail.Faces.get(nId).Icon = ConvertMgr.getEcodeUrl(this, imageUrl);
			m_ItemArray.add(new PictureItem(CurrentInfo._selectUserDetail.Faces.get(nId).Icon, ""));
		}
		
		PictureAdapter pictureAdapter = new PictureAdapter(this, R.layout.picture_grid, m_ItemArray);
		m_gvPictureView.setAdapter(pictureAdapter);
	}

	
	private void BackDetail(){
		this.onBackPressed();
	}
	
	
}
