package mogi.gashfara.com.gsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class MessageRecordsAdapter extends ArrayAdapter<MessageRecord> {
    private ImageLoader mImageLoader;

    public MessageRecordsAdapter(Context context) {
        super(context, R.layout.message_item);

        mImageLoader = new ImageLoader(VolleyApplication.getInstance().getRequestQueue(), new BitmapLruCache());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
        }

        // NOTE: You would normally use the ViewHolder pattern here
        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.image1);
        TextView textView = (TextView) convertView.findViewById(R.id.text1);

        MessageRecord imageRecord = getItem(position);

        imageView.setImageUrl(imageRecord.getImageUrl(), mImageLoader);
        textView.setText(imageRecord.getComment());

        return convertView;
    }

    public void setMessageRecords(List<MessageRecord> objects) {
        clear();

        for(MessageRecord object : objects) {
            add(object);
        }

        notifyDataSetChanged();
    }
}
