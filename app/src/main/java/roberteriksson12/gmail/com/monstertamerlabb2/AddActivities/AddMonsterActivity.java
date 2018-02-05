package roberteriksson12.gmail.com.monstertamerlabb2.AddActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import roberteriksson12.gmail.com.monstertamerlabb2.Database.DBHelper;
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
    }

    public void onAddMonsterButtonPressed(View view) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.addMonster(editTextMonsterName.getText().toString(), Integer.parseInt(editTextMonsterLevel.getText().toString()), Integer.parseInt(editTextMonsterDungeonId.getText().toString()));
        Toast.makeText(this, "New monster added", Toast.LENGTH_SHORT).show();
    }
}
