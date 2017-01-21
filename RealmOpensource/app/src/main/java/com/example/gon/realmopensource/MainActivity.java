package com.example.gon.realmopensource;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gon.realmopensource.Model.Table;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_id) EditText et_id;
    @BindView(R.id.et_age) EditText et_age;
    @BindView(R.id.rv_view) RecyclerView rv_view;
    @BindView(R.id.bt_save) Button bt_save;
    @BindView(R.id.bt_delete) Button bt_delete;
    @BindView(R.id.bt_update) Button bt_update;
//    @ViewById(R.id.et_id) EditText et_id;
//    @ViewById(R.id.et_age) EditText et_age;
//    @ViewById(R.id.rv_view) RecyclerView rv_view;

    @OnClick({R.id.bt_save,R.id.bt_delete,R.id.bt_update})
    void onClickButton(View view){
        if(view.getId() == R.id.bt_save){
            insert(et_id.getText().toString().trim(),
                    Integer.parseInt(et_age.getText().toString().trim()));
            getUserlist();
        }else if(view.getId() == R.id.bt_delete){
            delete(et_id.getText().toString().trim());
            getUserlist();
        } else if(view.getId() == R.id.bt_update){
            update(et_id.getText().toString().trim(),
                    Integer.parseInt(et_age.getText().toString().trim()));
            getUserlist();
        }
    }
    private Realm realm;
    private ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        adapter = new ListAdapter(this, realm);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_view.setLayoutManager(layoutManager);
        rv_view.setAdapter(adapter);
    }

    private void insert(final String name, final int age){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm mrealm){
                Table table = mrealm.createObject(Table.class, name);
                table.setAge(age);
            }
        });
    }
    private void delete(final String name){
        final RealmResults<Table> table = realm.where(Table.class).equalTo("name", name).findAll();
        Log.i("TAG","In delete before transaction");
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm mrealm){
                table.deleteFromRealm(0);
            }
        });
        Log.i("TAG", "transaction finish");
    }
    private void update(final String name, final int age){
        final Table table = realm.where(Table.class).equalTo("name", name).findFirst();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm mrealm){
                table.setAge(age);
            }
        });
    }
    private void getUserlist(){
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
