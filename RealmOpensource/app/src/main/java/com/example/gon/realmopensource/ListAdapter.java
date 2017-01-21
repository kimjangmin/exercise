package com.example.gon.realmopensource;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.gon.realmopensource.Model.Table;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 김장민 on 2017-01-20.
 */


public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private Context context;
    private Realm realm;
    public ListAdapter(Context context, Realm realm) {
        this.context = context;
        this.realm = realm;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyler_output, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        RealmResults<Table> results = realm.where(Table.class).findAll();
        holder.tv_output.setText(results.get(position).toString());
    }

    @Override
    public int getItemCount() {
        RealmResults<Table> results = realm.where(Table.class).findAll();
        return results.size();
    }
    public class ListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_output) TextView tv_output;
        public ListViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);

        }

    }
}
