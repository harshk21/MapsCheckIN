package com.hk210.mapscheckin;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;


import com.hk210.mapscheckin.Adapter.CheckAdapter;
import com.hk210.mapscheckin.Database.DBHelper;
import com.hk210.mapscheckin.Model.Check;


import java.util.List;

public class CheckInHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Check> data;
    private CheckAdapter checkAdapter;
    DBHelper dbHelper;
    String TAG = "main";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_history);
        getSupportActionBar().setTitle("Check-IN History");
        recyclerView = findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        dbHelper = new DBHelper(this);

        fetchalldata();


    }

    private void fetchalldata() {
        CheckAdapter adapter = new CheckAdapter(CheckInHistory.this, dbHelper.getAllData());
        recyclerView.setAdapter(adapter);
    }
}



