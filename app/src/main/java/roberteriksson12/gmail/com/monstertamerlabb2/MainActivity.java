package roberteriksson12.gmail.com.monstertamerlabb2;

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
import android.widget.EditText;
import android.widget.ListView;
import android.view.Menu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    private FloatingActionButton fab;

    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;

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
            setContentView(R.layout.activity_add_dungeon);
            spinner = findViewById(R.id.spinner_dungeon_name);
            adapter = ArrayAdapter.createFromResource(this, R.array.dungeonArray, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            final EditText editTextName = findViewById(R.id.edit_text_dungeon_name);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    editTextName.setText(selectedItem);

                } // to close the onItemSelected
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }

        if (listView.getAdapter() == monsterAdapter) {
            setContentView(R.layout.activity_add_monster);
            spinner = findViewById(R.id.spinner_monster_name);
            adapter = ArrayAdapter.createFromResource(this, R.array.monsterArray, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            final EditText editTextMonsterName = findViewById(R.id.edit_text_monster_name);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    editTextMonsterName.setText(selectedItem);

                } // to close the onItemSelected
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
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
        Toast.makeText(this, "New dungeon: " + editTextName.getText().toString() +  " added", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setAdapter(dungeonAdapter);
        registerForContextMenu(listView);
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
        Toast.makeText(this, "New monster: " + editTextMonsterName.getText().toString() + " added", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setAdapter(monsterAdapter);
        registerForContextMenu(listView);
        monsterAdapter.notifyDataSetChanged();
    }
}
