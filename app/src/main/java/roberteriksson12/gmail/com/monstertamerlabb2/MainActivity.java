package roberteriksson12.gmail.com.monstertamerlabb2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
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

    private DBHelper dbHelper;
    private ListView listView;
    private List<Monster> monsterList;
    private List<Dungeon> dungeonList;
    private List<TamedMonster> tamedMonsterList;
    private List<Monster> monstersInDungeonList;
    private MonsterAdapter monsterAdapter;
    private MonsterAdapter monstersInDungeonAdapter;
    private DungeonAdapter dungeonAdapter;
    private TamedMonsterAdapter tamedMonsterAdapter;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(dbHelper.DB_LOGTAG, "Skapar databasen");
        initialize();
        fab = findViewById(R.id.fab_add);
        fab.hide();
    }

    public void initialize() {
        dbHelper = new DBHelper(this);
        //fill lists
        monsterList = dbHelper.getMonsters();
        dungeonList = dbHelper.getDungeons();
        tamedMonsterList = dbHelper.getTamedMonsters();

        //setup adapters
        dungeonAdapter = new DungeonAdapter(this, dungeonList);
        monsterAdapter = new MonsterAdapter(this, monsterList);
        tamedMonsterAdapter = new TamedMonsterAdapter(this, tamedMonsterList);

        if (getIntent().getIntExtra("back", 0) == 0){
            Log.d(dbHelper.DB_LOGTAG, "" + 0);
            return;
        }
        if (getIntent().getIntExtra("back", 0) == 1){
            Log.d(dbHelper.DB_LOGTAG, "" + 1);
            listView = findViewById(R.id.listView);
            listView.setAdapter(dungeonAdapter);
        }
        if (getIntent().getIntExtra("back", 0) == 2){
            Log.d(dbHelper.DB_LOGTAG, "" + 2);
            listView = findViewById(R.id.listView);
            listView.setAdapter(monsterAdapter);
        }
        registerForContextMenu(listView);

    }

    //skapa menyn i övre hörnet
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //hantera clicks på menyn
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.dun:
                //connect DungeonAdapter to listView
                listView = findViewById(R.id.listView);
                listView.setAdapter(dungeonAdapter);
                fab.show();
                registerForContextMenu(listView);
                break;
            case R.id.mon:
                //connect monsterAdapter to listView
                listView = findViewById(R.id.listView);
                listView.setAdapter(monsterAdapter);
                registerForContextMenu(listView);
                fab.show();
                break;
            case R.id.tam:
                //connect tamedMonstersAdapter to listView
                listView = findViewById(R.id.listView);
                listView.setAdapter(tamedMonsterAdapter);
                fab.hide();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //skapa context meny
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (listView.getAdapter() == dungeonAdapter) {
            MenuInflater inflaterDungeon = getMenuInflater();
            inflaterDungeon.inflate(R.menu.menu_context_dungeon, menu);
        }
        if (listView.getAdapter() == monsterAdapter) {
            MenuInflater inflaterMonster = getMenuInflater();
            inflaterMonster.inflate(R.menu.menu_context_monster, menu);
        }
        if (listView.getAdapter() == tamedMonsterAdapter) {
            MenuInflater inflaterTamed = getMenuInflater();
            inflaterTamed.inflate(R.menu.menu_context_tamed, menu);
        }
    }

    //hantera clicks på context menyn
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.show:
                Toast.makeText(this, "Showing monsters in dungeon", Toast.LENGTH_SHORT).show();
                monstersInDungeonList = dbHelper.getMonstersInDungeon(dungeonList.get(info.position));
                monstersInDungeonAdapter = new MonsterAdapter(this, monstersInDungeonList);
                listView.setAdapter(monstersInDungeonAdapter);
                break;
            case R.id.tame:
                Toast.makeText(this, "Tamed monster", Toast.LENGTH_SHORT).show();
                tamedMonsterList.add(dbHelper.addTamedMonster(monsterList.get(info.position)));
                tamedMonsterAdapter.notifyDataSetChanged();
                break;
            case R.id.release:
                Toast.makeText(this, "Released monster", Toast.LENGTH_SHORT).show();
                dbHelper.deleteTamedMonster(tamedMonsterList.get(info.position));
                tamedMonsterList.remove(info.position);
                tamedMonsterAdapter.notifyDataSetChanged();
                break;
            case R.id.del:
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                if (listView.getAdapter() == dungeonAdapter) {
                    dbHelper.deleteDungeon(dungeonList.get(info.position));
                    dungeonList.remove(info.position);
                    dungeonAdapter.notifyDataSetChanged();
                    break;
                }
                if (listView.getAdapter() == monsterAdapter) {
                    dbHelper.deleteMonster(monsterList.get(info.position));
                    monsterList.remove(info.position);
                    monsterAdapter.notifyDataSetChanged();
                    break;
                }
        }

        return super.onContextItemSelected(item);
    }

    public void onAddButtonPressed(View view) {
        if (listView.getAdapter() == dungeonAdapter) {
            Intent dungeonIntent = new Intent(this, AddDungeonActivity.class);
            startActivity(dungeonIntent);
            this.finish();
        }

        if (listView.getAdapter() == monsterAdapter) {
            Intent monsterIntent = new Intent(this, AddMonsterActivity.class);
            startActivity(monsterIntent);
            this.finish();
        }
    }
}
