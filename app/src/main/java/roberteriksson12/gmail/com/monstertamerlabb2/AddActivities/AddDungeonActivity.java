package roberteriksson12.gmail.com.monstertamerlabb2.AddActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import roberteriksson12.gmail.com.monstertamerlabb2.Database.DBHelper;
import roberteriksson12.gmail.com.monstertamerlabb2.R;

public class AddDungeonActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextFloors;
    private EditText editTextExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dungeon);
        editTextName = findViewById(R.id.edit_text_dungeon_name);
        editTextFloors = findViewById(R.id.edit_text_dungeon_floors);
        editTextExp = findViewById(R.id.edit_text_dungeon_exp);

    }
/*
    public void onAddDungeonButtonPressed(View view) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.addDungeon(editTextName.getText().toString(), Integer.parseInt(editTextFloors.getText().toString()), Integer.parseInt(editTextExp.getText().toString()));
        Toast.makeText(this, "New dungeon added", Toast.LENGTH_SHORT).show();
    }*/
}
