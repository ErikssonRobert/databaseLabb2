package roberteriksson12.gmail.com.monstertamerlabb2.AddActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import roberteriksson12.gmail.com.monstertamerlabb2.Database.DBHelper;
import roberteriksson12.gmail.com.monstertamerlabb2.MainActivity;
import roberteriksson12.gmail.com.monstertamerlabb2.R;

public class AddDungeonActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextFloors;
    private EditText editTextExp;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dungeon);

        editTextName = findViewById(R.id.edit_text_dungeon_name);
        editTextFloors = findViewById(R.id.edit_text_dungeon_floors);
        editTextExp = findViewById(R.id.edit_text_dungeon_exp);

        spinner = findViewById(R.id.spinner_dungeon_name);
        adapter = ArrayAdapter.createFromResource(this, R.array.dungeonArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                editTextName.setText(selectedItem);
                switch (selectedItem) {
                    case "Cave":
                        editTextFloors.setText(getResources().getTextArray(R.array.cave_array)[0]);
                        editTextExp.setText(getResources().getTextArray(R.array.cave_array)[1]);
                        break;
                    case "Haunted mansion":
                        editTextFloors.setText(getResources().getTextArray(R.array.haunted_mansion_array)[0]);
                        editTextExp.setText(getResources().getTextArray(R.array.haunted_mansion_array)[1]);
                        break;
                    case "Catacombs":
                        editTextFloors.setText(getResources().getTextArray(R.array.catacombs_array)[0]);
                        editTextExp.setText(getResources().getTextArray(R.array.catacombs_array)[1]);
                        break;
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void onAddDungeonButtonPressed(View view) {
        if (editTextName.getText().toString().isEmpty() || editTextFloors.getText().toString().isEmpty() || editTextExp.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.addDungeon(editTextName.getText().toString(), Integer.parseInt(editTextFloors.getText().toString()), Integer.parseInt(editTextExp.getText().toString()));
        Toast.makeText(this, "New dungeon: " + editTextName.getText().toString() +  " added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(this, MainActivity.class);
        back.putExtra("back", 1);
        startActivity(back);
    }
}
