package roberteriksson12.gmail.com.monstertamerlabb2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import roberteriksson12.gmail.com.monstertamerlabb2.Adapters.MonsterAdapter;
import roberteriksson12.gmail.com.monstertamerlabb2.Database.DBHelper;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Dungeon;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Monster;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);
    private ListView listView;
    private List<Monster> monsterList;
    private MonsterAdapter monsterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(dbHelper.DB_LOGTAG, "Skapar databasen");

        monsterList = dbHelper.getMonsters();

        monsterAdapter = new MonsterAdapter(this, monsterList);

        //connect monsterAdapter to listView
        listView = findViewById(R.id.listView);
        listView.setAdapter(monsterAdapter);

        //Dungeon dungeon = dbHelper.addDungeon("Depts", 3, 10);
        //dbHelper.addMonster("Slime", 1, 1);
        //dbHelper.addTamedMonster("Slime", 2, 0, 1);
    }
}
