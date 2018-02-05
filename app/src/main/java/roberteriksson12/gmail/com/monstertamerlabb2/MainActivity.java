package roberteriksson12.gmail.com.monstertamerlabb2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.view.Menu;
import android.widget.Toast;

import java.util.List;

import roberteriksson12.gmail.com.monstertamerlabb2.Adapters.DungeonAdapter;
import roberteriksson12.gmail.com.monstertamerlabb2.Adapters.MonsterAdapter;
import roberteriksson12.gmail.com.monstertamerlabb2.Adapters.TamedMonsterAdapter;
import roberteriksson12.gmail.com.monstertamerlabb2.AddActivities.AddDungeonActivity;
import roberteriksson12.gmail.com.monstertamerlabb2.AddActivities.AddMonsterActivity;
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
        initialize();
    }

    public void initialize() {
        //fill lists
        monsterList = dbHelper.getMonsters();
        dungeonList = dbHelper.getDungeons();
        tamedMonsterList = dbHelper.getTamedMonsters();

        //setup adapters
        dungeonAdapter = new DungeonAdapter(this, dungeonList);
        monsterAdapter = new MonsterAdapter(this, monsterList);
        tamedMonsterAdapter = new TamedMonsterAdapter(this, tamedMonsterList);

        dungeonAdapter.notifyDataSetChanged();
        monsterAdapter.notifyDataSetChanged();
        tamedMonsterAdapter.notifyDataSetChanged();
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
            setContentView(R.layout.activity_add_dungeon);
        }

        if (listView.getAdapter() == monsterAdapter) {
            setContentView(R.layout.activity_add_monster);
        }
    }

    public void onAddDungeonButtonPressed(View view) {
        EditText editTextName = findViewById(R.id.edit_text_dungeon_name);
        EditText editTextFloors = findViewById(R.id.edit_text_dungeon_floors);
        EditText editTextExp = findViewById(R.id.edit_text_dungeon_exp);

        if (editTextName.getText().toString().isEmpty() || editTextFloors.getText().toString().isEmpty() || editTextExp.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        dungeonList.add(dbHelper.addDungeon(editTextName.getText().toString(), Integer.parseInt(editTextFloors.getText().toString()), Integer.parseInt(editTextExp.getText().toString())));
        Toast.makeText(this, "New dungeon added", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setAdapter(dungeonAdapter);
        dungeonAdapter.notifyDataSetChanged();
    }

    public void onAddMonsterButtonPressed(View view) {
        EditText editTextMonsterName = findViewById(R.id.edit_text_monster_name);
        EditText editTextMonsterLevel = findViewById(R.id.edit_text_monster_level);
        EditText editTextMonsterDungeonId = findViewById(R.id.edit_text_monster_dungeon_id);

        if (editTextMonsterName.getText().toString().isEmpty() || editTextMonsterLevel.getText().toString().isEmpty() || editTextMonsterDungeonId.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        monsterList.add(dbHelper.addMonster(editTextMonsterName.getText().toString(), Integer.parseInt(editTextMonsterLevel.getText().toString()), Integer.parseInt(editTextMonsterDungeonId.getText().toString())));
        Toast.makeText(this, "New dungeon added", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setAdapter(monsterAdapter);
        monsterAdapter.notifyDataSetChanged();
    }
}
