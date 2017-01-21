package com.example.gon.greenopen;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gon.greenopen.db.DaoMaster;
import com.example.gon.greenopen.db.DaoSession;
import com.example.gon.greenopen.db.Person;
import com.example.gon.greenopen.db.PersonDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_id) EditText et_id;
    @BindView(R.id.et_age) EditText et_age;
    @BindView(R.id.rv_view) RecyclerView rv_view;
    @BindView(R.id.bt_save) Button bt_save;
    @BindView(R.id.bt_delete) Button bt_delete;
    @BindView(R.id.bt_update) Button bt_update;
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

    private PersonDao personDao;
    private ListAdapter adapter;
    private Query<Person> personQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rv_view.setHasFixedSize(true);
        rv_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListAdapter(getApplicationContext());
        rv_view.setAdapter(adapter);
        personDao = initDb();
        personQuery = personDao.queryBuilder().orderAsc(PersonDao.Properties.Name).build();
        getUserlist();
    }
    public void insert(String name, int age){
        Person person = new Person(name, age);
        personDao.insert(person);
    }
    public void delete(String name){
        personDao.deleteByKey(name);
    }
    public void update(String name, int age){
        Person person = new Person();
        personDao.update(new Person(name, age));
    }
    private void getUserlist(){
        List<Person> person = personQuery.list();
        adapter.setView(person);
    }
    public PersonDao initDb(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "default-db-name", null);
        //DB가 존재하지 않으면 default이름으로 생성.
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        DaoSession masterSession = master.newSession();
        return masterSession.getPersonDao();
    }
}
