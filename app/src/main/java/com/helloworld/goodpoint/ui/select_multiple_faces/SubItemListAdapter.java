package com.helloworld.goodpoint.ui.select_multiple_faces;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.helloworld.goodpoint.R;

import java.util.List;

public class SubItemListAdapter extends RecyclerView.Adapter<SubItemListAdapter.SubItemViewHolder> {

    private List<SubItemList> subItemList;
    private int lastSelectedPosition = -1;
    private Context context;

    SubItemListAdapter(List<SubItemList> subItemList,Context ctx) {
        this.subItemList = subItemList;
        context = ctx;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_list_items, viewGroup, false);

        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
        com.helloworld.goodpoint.ui.select_multiple_faces.SubItemList subItem = subItemList.get(i);
        subItemViewHolder.tvItemImage.setImageBitmap(subItem.getSubItemImage());
        subItemViewHolder.rb.setChecked(lastSelectedPosition == i);
        subItemViewHolder.rb.setClickable(false);
            //Toast.makeText(cxt, "size="+item.getSubItemList().size(),Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }


    class SubItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvItemImage;
        private RadioGroup rg;
        private RadioButton rb;

        SubItemViewHolder(View itemView) {
            super(itemView);
            tvItemImage = itemView.findViewById(R.id.img_view);
            rb = itemView.findViewById(R.id.radioButton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();


                }
            });
        }
    }
}


