package roberteriksson12.gmail.com.monstertamerlabb2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Robert on 2018-02-04.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    public static final String DB_LOGTAG = "MonsterTamer";
    private static final String DB_NAME = "MonsterTamer";

    private static final String TABLE_DUNGEON = "dungeon";
    private static final String COLUMN_DUNGEON_ID = "dungeonId";
    private static final String COLUMN_DUNGEON_NAME = "dungeonName";
    private static final String COLUMN_DUNGEON_FLOORS = "dungeonFloors";
    private static final String COLUMN_DUNGEON_EXP = "dungeonExp";

    private static final String TABLE_MONSTER = "monster";
    private static final String COLUMN_MONSTER_ID = "monsterId";
    private static final String COLUMN_MONSTER_NAME = "monsterName";
    private static final String COLUMN_MONSTER_LVL = "monsterLvl";
    private static final String COLUMN_MONSTER_DUNGEONID = "monsterDungeonId";

    private static final String TABLE_TAMEDMONSTERS = "tamedMonster";
    private static final String COLUMN_TAMEDMONSTERS_ID = "tamedMonsterId";
    private static final String COLUMN_TAMEDMONSTERS_NAME = "tamedMonsterName";
    private static final String COLUMN_TAMEDMONSTERS_LVL = "tamedMonsterLvl";
    private static final String COLUMN_TAMEDMONSTERS_EXP = "tamedMonsterExp";
    private static final String COLUMN_TAMEDMONSTERS_ORDER = "tamedMonsterOrder";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDungeonTable());
        db.execSQL(createMonsterTable());
        db.execSQL(createTamedMonsterTable());

        Log.d(DB_LOGTAG, "Databasen kanske skapades");
    }

    public String createDungeonTable() {
        String DB_DUNGEON_CREATE = "CREATE TABLE " + TABLE_DUNGEON + " (" +
                COLUMN_DUNGEON_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_DUNGEON_NAME + " TEXT, " +
                COLUMN_DUNGEON_FLOORS + " INTEGER, " +
                COLUMN_DUNGEON_EXP + " INTEGER" + ")";

        return DB_DUNGEON_CREATE;
    }

    public String createMonsterTable() {
        String DB_MONSTER_CREATE = "CREATE TABLE " + TABLE_MONSTER + " (" +
                COLUMN_MONSTER_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_MONSTER_NAME + " TEXT, " +
                COLUMN_MONSTER_LVL + " INTEGER, " +
                COLUMN_MONSTER_DUNGEONID + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_MONSTER_DUNGEONID + ") REFERENCES dungeon(dungeonId))";

        return DB_MONSTER_CREATE;
    }

    public String createTamedMonsterTable() {
        String DB_TAMEDMONSTERS_CREATE = "CREATE TABLE " + TABLE_TAMEDMONSTERS + " (" +
                COLUMN_TAMEDMONSTERS_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TAMEDMONSTERS_NAME + " TEXT, " +
                COLUMN_TAMEDMONSTERS_LVL + " INTEGER, " +
                COLUMN_TAMEDMONSTERS_EXP + " INTEGER, " +
                COLUMN_TAMEDMONSTERS_ORDER + " INTEGER)";

        return DB_TAMEDMONSTERS_CREATE;
    }

    public void addDungeon(String name, int floors, int exp){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DUNGEON_NAME, name);
        contentValues.put(COLUMN_DUNGEON_FLOORS, floors);
        contentValues.put(COLUMN_DUNGEON_EXP, exp);
        long id = db.insert(TABLE_DUNGEON, null, contentValues);
        Log.d(DB_LOGTAG, "Adding values to dungeon table: " + id);
        db.close();
    }

    public void addMonster(String name, int lvl, int d_Id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MONSTER_NAME, name);
        contentValues.put(COLUMN_MONSTER_LVL, lvl);
        contentValues.put(COLUMN_MONSTER_DUNGEONID, d_Id);
        long id = db.insert(TABLE_MONSTER, null, contentValues);
        Log.d(DB_LOGTAG, "Adding values to monster table: " + id);
        db.close();
    }

    public void addTamedMonster(String name, int lvl, int exp, int order) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAMEDMONSTERS_NAME, name);
        contentValues.put(COLUMN_TAMEDMONSTERS_LVL, lvl);
        contentValues.put(COLUMN_TAMEDMONSTERS_EXP, exp);
        contentValues.put(COLUMN_TAMEDMONSTERS_ORDER, order);
        long id = db.insert(TABLE_TAMEDMONSTERS, null, contentValues);
        Log.d(DB_LOGTAG, "Adding values to tamed monster table: " + id);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
