package com.pearl.hbmsn.ui.connector;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pearl.hbmsn.R;
import com.pearl.hbmsn.volley.AppController;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author manish.s
 *
 */
public class PictureAdapter extends ArrayAdapter<PictureItem> {
	Context context;
	int layoutResourceId;
	ArrayList<PictureItem> data = new ArrayList<PictureItem>();
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public PictureAdapter(Context context, int layoutResourceId,
			ArrayList<PictureItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.picture_title);
			holder.imageItem = (NetworkImageView) row.findViewById(R.id.picture_image);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		PictureItem item = data.get(position);
		holder.txtTitle.setText(item.getTitle());
		holder.imageItem.setImageUrl(item.getImageUrl(), imageLoader);
		//holder.imageItem.setImageBitmap(item.getImage());
		return row;

	}

	static class RecordHolder {
		TextView txtTitle;
		NetworkImageView imageItem;

	}
}