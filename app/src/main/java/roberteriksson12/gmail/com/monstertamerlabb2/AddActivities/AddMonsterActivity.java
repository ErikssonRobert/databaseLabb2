package roberteriksson12.gmail.com.monstertamerlabb2.AddActivities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import roberteriksson12.gmail.com.monstertamerlabb2.Database.DBHelper;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Monster;
import roberteriksson12.gmail.com.monstertamerlabb2.MainActivity;
import roberteriksson12.gmail.com.monstertamerlabb2.R;

public class AddMonsterActivity extends AppCompatActivity {

    private EditText editTextMonsterName;
    private EditText editTextMonsterLevel;
    private EditText editTextMonsterDungeonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monster);

        editTextMonsterName = findViewById(R.id.edit_text_monster_name);
        editTextMonsterLevel = findViewById(R.id.edit_text_monster_level);
        editTextMonsterDungeonId = findViewById(R.id.edit_text_monster_dungeon_id);

        Spinner spinner = findViewById(R.id.spinner_monster_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.monsterArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                editTextMonsterName.setText(selectedItem);
                switch (selectedItem) {
                    case "Slime":
                        editTextMonsterLevel.setText(getResources().getTextArray(R.array.slime_array)[0]);
                        editTextMonsterDungeonId.setText(getResources().getTextArray(R.array.slime_array)[1]);
                        break;
                    case "Goblin":
                        editTextMonsterLevel.setText(getResources().getTextArray(R.array.goblin_array)[0]);
                        editTextMonsterDungeonId.setText(getResources().getTextArray(R.array.goblin_array)[1]);
                        break;
                    case "Gargoyle":
                        editTextMonsterLevel.setText(getResources().getTextArray(R.array.gargoyle_array)[0]);
                        editTextMonsterDungeonId.setText(getResources().getTextArray(R.array.gargoyle_array)[1]);
                        break;
                    case "Dragon":
                        editTextMonsterLevel.setText(getResources().getTextArray(R.array.dragon_array)[0]);
                        editTextMonsterDungeonId.setText(getResources().getTextArray(R.array.dragon_array)[1]);
                        break;
                    case "Vampire":
                        editTextMonsterLevel.setText(getResources().getTextArray(R.array.vampire_array)[0]);
                        editTextMonsterDungeonId.setText(getResources().getTextArray(R.array.vampire_array)[1]);
                        break;
                    case "Ghost":
                        editTextMonsterLevel.setText(getResources().getTextArray(R.array.ghost_array)[0]);
                        editTextMonsterDungeonId.setText(getResources().getTextArray(R.array.ghost_array)[1]);
                        break;
                    case "Giant spider":
                        editTextMonsterLevel.setText(getResources().getTextArray(R.array.giant_spider_array)[0]);
                        editTextMonsterDungeonId.setText(getResources().getTextArray(R.array.giant_spider_array)[1]);
                        break;
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void onAddMonsterButtonPressed(View view) {
        if (editTextMonsterName.getText().toString().isEmpty() || editTextMonsterLevel.getText().toString().isEmpty() || editTextMonsterDungeonId.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.addMonster(editTextMonsterName.getText().toString(), Integer.parseInt(editTextMonsterLevel.getText().toString()), dbHelper.getDungeonId(editTextMonsterDungeonId.getText().toString()));
        Toast.makeText(this, "New monster: " + editTextMonsterName.getText().toString() + " added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(this, MainActivity.class);
        back.putExtra("back", 2);
        startActivity(back);
    }
}
