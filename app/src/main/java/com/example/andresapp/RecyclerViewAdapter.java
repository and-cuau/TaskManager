package com.example.andresapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<taskManagerModel> taskManagerModels;
    //boolean[] selectedItems;
    //ArrayList<Boolean> selectedItems;
    boolean[] selectedItems;
    public RecyclerViewAdapter(Context context, ArrayList<taskManagerModel> taskManagerModels){
        this.context = context;
        this.taskManagerModels = taskManagerModels;
        // this.selectedItems = new ArrayList<>(taskManagerModels.size());

        this.selectedItems = new boolean[taskManagerModels.size()];
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(taskManagerModels.get(position).getTask());
        holder.tv2.setText(taskManagerModels.get(position).getw2());
        holder.tv3.setText(taskManagerModels.get(position).getw3());
        holder.imageView.setImageResource(taskManagerModels.get(position).getImg());



        taskManagerModel task = taskManagerModels.get(position);
        //holder.bind(task, position);



        // Set the checkbox state based on selection
        //  holder.checkbox.setChecked(selectedItems.get(position));
        holder.checkbox.setChecked(taskManagerModels.get(position).isSelected); //
        // Handle checkbox click to toggle selection
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //selectedItems.set(position, isChecked);
            //selectedItems[position] = isChecked;
            taskManagerModels.get(position).setSelected(isChecked);
        });




    }

    @Override
    public int getItemCount() {
        return taskManagerModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView tv, tv2, tv3;
        CheckBox checkbox;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tv = itemView.findViewById(R.id.textView);
            tv2 = itemView.findViewById(R.id.textView2);
            tv3 = itemView.findViewById(R.id.textView3);
            checkbox = itemView.findViewById(R.id.checkbox);
            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // Toggle the selection state of the item
            int position = getAdapterPosition();
            boolean isSelected = taskManagerModels.get(position).isSelected();
            taskManagerModels.get(position).setSelected(!isSelected);
            notifyItemChanged(position);
        }

//        void bind(taskManagerModel task, int position) {
//            tv.setText(taskManagerModel.getTask());
//            // Other bindings for task details
//        }



    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void removeItem(int position) {
        taskManagerModels.remove(position);
        notifyItemRemoved(position);
    }



}
