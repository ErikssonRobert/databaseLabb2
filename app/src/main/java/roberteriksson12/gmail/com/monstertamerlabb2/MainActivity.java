package roberteriksson12.gmail.com.monstertamerlabb2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import roberteriksson12.gmail.com.monstertamerlabb2.Database.DBHelper;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Dungeon;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(dbHelper.DB_LOGTAG, "Skapar databasen");
        //Dungeon dungeon = dbHelper.addDungeon("Depts", 3, 10);
        //dbHelper.addMonster("Slime", 1, 1);
        //dbHelper.addTamedMonster("Slime", 2, 0, 1);
    }
}
