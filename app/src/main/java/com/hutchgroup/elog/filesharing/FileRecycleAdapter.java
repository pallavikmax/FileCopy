package com.hutchgroup.elog.filesharing;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class FileRecycleAdapter extends RecyclerView.Adapter<FileRecycleAdapter.ViewHolder> {
    ArrayList<FileBean> data;
    public static IFile mlistner;

    public FileRecycleAdapter(ArrayList<FileBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final FileBean bean = data.get(position);

        viewHolder.tvFileName.setText(bean.getFileName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mlistner != null) {
                    mlistner.onClick(bean);
                }

            }
        });

    }


    public void updateList(ArrayList<FileBean> list){
        data = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvFileName;

        public ViewHolder(View convertView) {
            super(convertView);
            tvFileName = (TextView)convertView.findViewById(R.id.tvFileName);


        }
    }

    public interface IFile {
        void onClick(FileBean file);
    }
}
