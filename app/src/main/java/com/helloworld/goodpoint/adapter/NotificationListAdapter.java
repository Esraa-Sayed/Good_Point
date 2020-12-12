package com.helloworld.goodpoint.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View notificationItem = convertView;
        ViewHolder viewHolder;
        if(notificationItem == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            notificationItem = inflater.inflate(R.layout.notification_item, parent, false);
            viewHolder = new ViewHolder(notificationItem);
            notificationItem.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) notificationItem.getTag();

        if(!list.get(position).isRead()){
            viewHolder.getRead().setVisibility(View.VISIBLE);
            viewHolder.getLayout().setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            viewHolder.getArchive().setBackground(context.getResources().getDrawable(R.drawable.notification_button1));
            viewHolder.getRead().setBackground(context.getResources().getDrawable(R.drawable.notification_button1));

            viewHolder.getRead().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(position).setRead(true);
                    notifyDataSetChanged();
                }
            });
        }else {
            viewHolder.getRead().setVisibility(View.GONE);
            viewHolder.getLayout().setBackgroundColor(Color.WHITE);
            viewHolder.getArchive().setBackground(context.getResources().getDrawable(R.drawable.notification_button2));

        }

        viewHolder.getArchive().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        viewHolder.getTitle().setText(list.get(position).getTitle());
        //viewHolder.getDate().setText(list.get(position).getDate().toString());
        viewHolder.getDescription().setText(list.get(position).getDescription());
        if(list.get(position).getImage() != null)
            viewHolder.getImageView().setImageBitmap(list.get(position).getImage());



        return notificationItem;
    }

    private class ViewHolder{
        private View convertView;
        private TextView title, date, description;
        private Button archive, read;
        private CircleImageView imageView;
        private LinearLayout layout;

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

        public LinearLayout getLayout() {
            if(layout == null)
                layout = convertView.findViewById(R.id.notification_layout);
            return layout;
        }
    }
}
