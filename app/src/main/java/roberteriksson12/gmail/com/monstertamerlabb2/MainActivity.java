package roberteriksson12.gmail.com.monstertamerlabb2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.view.Menu;

import java.util.List;

import roberteriksson12.gmail.com.monstertamerlabb2.Adapters.DungeonAdapter;
import roberteriksson12.gmail.com.monstertamerlabb2.Adapters.MonsterAdapter;
import roberteriksson12.gmail.com.monstertamerlabb2.Adapters.TamedMonsterAdapter;
import roberteriksson12.gmail.com.monstertamerlabb2.Database.DBHelper;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Dungeon;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Monster;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.TamedMonster;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);
    private ListView listView;
    private List<Monster> monsterList;
    private List<Dungeon> dungeonList;
    private List<TamedMonster> tamedMonsterList;
    private MonsterAdapter monsterAdapter;
    private DungeonAdapter dungeonAdapter;
    private TamedMonsterAdapter tamedMonsterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(dbHelper.DB_LOGTAG, "Skapar databasen");

        //fill lists
        monsterList = dbHelper.getMonsters();
        dungeonList = dbHelper.getDungeons();
        tamedMonsterList = dbHelper.getTamedMonsters();

        //setup adapters
        dungeonAdapter = new DungeonAdapter(this, dungeonList);
        monsterAdapter = new MonsterAdapter(this, monsterList);
        tamedMonsterAdapter = new TamedMonsterAdapter(this, tamedMonsterList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.dun:
                //connect DungeonAdapter to listView
                listView = findViewById(R.id.listView);
                listView.setAdapter(dungeonAdapter);
                break;
            case R.id.mon:
                //connect monsterAdapter to listView
                listView = findViewById(R.id.listView);
                listView.setAdapter(monsterAdapter);
                break;
            case R.id.tam:
                //connect tamedMonstersAdapter to listView
                listView = findViewById(R.id.listView);
                listView.setAdapter(tamedMonsterAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAddButtonPressed(View view) {
        if (listView.getAdapter() == dungeonAdapter) {

        }
    }
}
