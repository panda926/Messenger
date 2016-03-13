package com.pearl.hbmsn.en.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.pearl.hbmsn.en.network.HBConstant;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


public class GetApkVersionAsync extends AsyncTask<String, String, ArrayList<String>> {

	private Context m_Context;
	private ProgressDialog m_ProgressDlg;
	public GetApkVersionAsync(Context context) {
		m_Context = context;
		
	}

		@Override
	protected ArrayList<String> doInBackground(String... params) {

		ArrayList<String> versionInfo = null;
		Connection DBConnection = null;
		
		try{
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			String connectionUrl = "jdbc:jtds:sqlserver://" + HBConstant._DBServerIP + "/" + HBConstant._DBName;
			DBConnection = DriverManager.getConnection(connectionUrl, HBConstant._DBAccount, HBConstant._DBPassword);
			Statement state = DBConnection.createStatement();
			String query = "select * from " + HBConstant._DBTableName;
			ResultSet resultSet = state.executeQuery(query);
			resultSet.next();
			String version = resultSet.getString("version");
			String downloadUrl = resultSet.getString("downloadUrl");
			versionInfo = new ArrayList<String>();
			versionInfo.add(version);
			versionInfo.add(downloadUrl);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		

		// ������ ������ �����ϴ� ���� ������ ����� onProgressUpdate �� �Ķ���Ͱ� �ȴ�
		return versionInfo;
	}

	
	@Override
	protected void onPostExecute(ArrayList<String> versionInfo) {
//		if(UpdateMgr.instance != null){
//			m_ProgressDlg.dismiss();
//			UpdateMgr.instance.SetServerVersion(versionInfo.get(0), versionInfo.get(1));
//		}
	}

	@Override
	protected void onPreExecute() {
		m_ProgressDlg = new ProgressDialog(m_Context);
		m_ProgressDlg.show();
		super.onPreExecute();
	}
}