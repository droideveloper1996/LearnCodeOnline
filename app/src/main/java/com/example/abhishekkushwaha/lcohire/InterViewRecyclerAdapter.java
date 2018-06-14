package com.example.abhishekkushwaha.lcohire;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class InterViewRecyclerAdapter extends RecyclerView.Adapter<InterViewRecyclerAdapter.InterViewHolder> {

    private Context mCtx;
    ArrayList<InterView> interViews;

    public InterViewRecyclerAdapter(Context mCtx, ArrayList<InterView> interViews) {
        this.mCtx = mCtx;
        this.interViews = interViews;
    }

    @NonNull
    @Override
    public InterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InterViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.appearance, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InterViewHolder holder, int position) {
        holder.Answer.setText(interViews.get(position).getmAnswer().trim());
        holder.Question.setText(interViews.get(position).getmQuestion().trim());
        holder.serial.setText("Question "+ Integer.toString(position+1));
    }

    @Override
    public int getItemCount() {
        return interViews.size();
    }

    class InterViewHolder extends RecyclerView.ViewHolder {
        TextView Question;
        TextView Answer;
        TextView serial;

        public InterViewHolder(View itemView) {
            super(itemView);
            serial=itemView.findViewById(R.id.serial);
            Question = itemView.findViewById(R.id.question);
            Answer = itemView.findViewById(R.id.answer);
        }
    }
}
