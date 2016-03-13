package com.pearl.hbmsn.ui.photo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.pearl.hbmsn.R;
import com.pearl.hbmsn.en.network.HBConstant;
import com.pearl.hbmsn.ui.dialog.CustomDialogYes;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;

public class DownloadTorrentAsync extends AsyncTask<String, String, String> {

	private ProgressDialog mDlg;
	private Context mContext;
	private CustomDialogYes warringDialog;
	
	public DownloadTorrentAsync(Context context) {
		mContext = context;
	}

	@Override
	protected void onPreExecute() {
		mDlg = new ProgressDialog(mContext);
		//mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mDlg.setMessage(mContext.getString(R.string.msg_downloading));
		mDlg.show();

		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {

		int count = 0;
		File file = null;
		String filePath = null;
		try {
			String sdCard = Environment.getExternalStorageState();
			
			if(!sdCard.equals(Environment.MEDIA_MOUNTED)){
				//SD카드가 마운트되여있지 않음
				file = Environment.getRootDirectory();
			}
			else{
				file = Environment.getExternalStorageDirectory();
			}
			String dir = file.getAbsolutePath() + "/BB";
			file = new File(dir);
			if(!file.exists()){
				file.mkdirs();
			}
			
			URL url = new URL(params[0].toString());
			URLConnection conexion = url.openConnection();
			conexion.connect();

			int lenghtOfFile = conexion.getContentLength();
			Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);

			Uri uri = Uri.parse(params[0].toString());
			List<String> pathSegmentList = uri.getPathSegments();
			
			
			InputStream input = new BufferedInputStream(url.openStream());
			filePath = dir + "/" + pathSegmentList.get(pathSegmentList.size() - 1);
			OutputStream output = new FileOutputStream(filePath);

			byte data[] = new byte[1024];

			while ((count = input.read(data)) != -1) {
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 수행이 끝나고 리턴하는 값은 다음에 수행될 onProgressUpdate 의 파라미터가 된다
		return filePath;
	}

	
	@Override
	protected void onPostExecute(String filePath) {
		mDlg.dismiss();
		playTorrent(filePath);
	}
	
	private void playTorrent(String filePath){
		
		Intent intent1= new Intent();
    	intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent1.setAction(android.content.Intent.ACTION_VIEW);
    	intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	
		//String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
    	String extension = "torrent";
    	String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    	
    	File file = new File(filePath);
    	intent1.setDataAndType(Uri.fromFile(file), mimeType);
    	
    	View.OnClickListener yesClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				warringDialog.dismiss();
				GoURL();
				
			}
		};
		View.OnClickListener noClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				warringDialog.dismiss();
				
			}
		};
    	try{
    		mContext.startActivity(intent1);
    	}
    	catch(ActivityNotFoundException e){
    		String msg = mContext.getString(R.string.msg_torrent_install);
    		String title = mContext.getString(R.string.msg_notification);
    		warringDialog = new CustomDialogYes(mContext, title, 
    				msg,
    				yesClickListener, noClickListener);
    		warringDialog.show();
    		e.printStackTrace();
    	}
	}
	private void GoURL(){
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(HBConstant._TorrentPlayerUrl));
		mContext.startActivity(intent);
	}
}
