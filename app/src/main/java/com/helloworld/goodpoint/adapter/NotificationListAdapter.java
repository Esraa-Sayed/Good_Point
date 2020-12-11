package com.helloworld.goodpoint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.pojo.NotificationItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationListAdapter extends ArrayAdapter<NotificationItem> {

    Context context;
    List<NotificationItem> list;

    public NotificationListAdapter(@NonNull Context context, int resource, @NonNull List<NotificationItem> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View notificationItem = convertView;
        ViewHolder viewHolder;
        if(notificationItem == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            notificationItem = inflater.inflate(R.layout.notification_item, parent, false);
            viewHolder = new ViewHolder(notificationItem);
            notificationItem.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) notificationItem.getTag();

        viewHolder.getTitle().setText(list.get(position).getTitle());
        viewHolder.getDate().setText(list.get(position).getDate().toString());
        viewHolder.getDescription().setText(list.get(position).getDescription());
        viewHolder.getImageView().setImageBitmap(list.get(position).getImage());

        return notificationItem;
    }

    private class ViewHolder{
        private View convertView;
        private TextView title, date, description;
        private Button archive, read;
        private CircleImageView imageView;

        public ViewHolder(View view) {
            this.convertView = view;
        }

        public TextView getTitle() {
            if(title == null)
                title = convertView.findViewById(R.id.notification_title);
            return title;
        }

        public TextView getDate() {
            if(date == null)
                date = convertView.findViewById(R.id.notification_date);
            return date;
        }

        public TextView getDescription() {
            if(description == null)
                description = convertView.findViewById(R.id.notification_description);
            return description;
        }

        public Button getArchive() {
            if(archive == null)
                archive = convertView.findViewById(R.id.notification_archive);
            return archive;
        }

        public Button getRead() {
            if(read == null)
                read = convertView.findViewById(R.id.notification_read);
            return read;
        }

        public CircleImageView getImageView() {
            return imageView;
        }
    }
}
