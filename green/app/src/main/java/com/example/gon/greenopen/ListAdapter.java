package com.example.gon.greenopen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gon.greenopen.db.Person;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 김장민 on 2017-01-20.
 */

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private List<Person> person;
    private Context context;
    public ListAdapter(Context context) {
        this.context = context;
    }
    public void setView(List<Person> person){
        this.person = person;
        notifyDataSetChanged();
    }
    public class ListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_output)
        TextView tv_output;
        public ListViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyler_output, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Person mperson = person.get(position);
        holder.tv_output.setText("Name = "+mperson.getName()+"\n"+"Age = "+mperson.getAge());
    }

    @Override
    public int getItemCount() {
        return person.size();
    }

}