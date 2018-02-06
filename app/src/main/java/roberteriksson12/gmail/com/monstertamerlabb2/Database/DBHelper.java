package roberteriksson12.gmail.com.monstertamerlabb2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Dungeon;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Monster;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.TamedMonster;

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

    public Dungeon addDungeon(String name, int floors, int exp){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DUNGEON_NAME, name);
        contentValues.put(COLUMN_DUNGEON_FLOORS, floors);
        contentValues.put(COLUMN_DUNGEON_EXP, exp);
        long id = db.insert(TABLE_DUNGEON, null, contentValues);
        Dungeon dungeon = new Dungeon();
        dungeon.name = name;
        dungeon.floors = floors;
        dungeon.exp = exp;
        Log.d(DB_LOGTAG, "Adding values to dungeon table: " + id);
        db.close();
        return dungeon;
    }

    public Monster addMonster(String name, int lvl, int d_Id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MONSTER_NAME, name);
        contentValues.put(COLUMN_MONSTER_LVL, lvl);
        contentValues.put(COLUMN_MONSTER_DUNGEONID, d_Id);
        long id = db.insert(TABLE_MONSTER, null, contentValues);
        Monster monster = new Monster();
        monster.name = name;
        monster.lvl = lvl;
        monster.d_Id = d_Id;
        Log.d(DB_LOGTAG, "Adding values to monster table: " + id);
        db.close();
        return monster;
    }

    public TamedMonster addTamedMonster(Monster monster) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAMEDMONSTERS_NAME, monster.name);
        contentValues.put(COLUMN_TAMEDMONSTERS_LVL, monster.lvl);
        long id = db.insert(TABLE_TAMEDMONSTERS, null, contentValues);
        TamedMonster tamedMonster = new TamedMonster();
        tamedMonster.name = monster.name;
        tamedMonster.lvl = monster.lvl;
        Log.d(DB_LOGTAG, "Adding values to tamed monster table: " + id);
        db.close();
        return tamedMonster;
    }

    public List<Dungeon> getDungeons() {
        List<Dungeon> dungeonList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_DUNGEON, null, null, null, null, null, null);

        boolean success = c.moveToFirst();
        if (!success) {
            return dungeonList; //return empty list if false
        }

        //loop for every row in table
        do {
            Dungeon dungeon = new Dungeon();
            dungeon.id = c.getLong(0);
            dungeon.name = c.getString(c.getColumnIndex(COLUMN_DUNGEON_NAME));

            dungeonList.add(dungeon);

            Log.d(DB_LOGTAG, dungeon.id + ", " + dungeon.name + ", " + dungeon.floors + ", " + dungeon.exp);
        } while (c.moveToNext()); //move cursor to next row

        db.close();
        return dungeonList;
    }

    public List<Monster> getMonsters() {
        List<Monster> monsterList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_MONSTER, null, null, null, null, null, null);

        boolean success = c.moveToFirst();
        if (!success) {
            return monsterList; //return empty list if false
        }

        //loop for every row in table
        do {
            Monster monster = new Monster();
            monster.id = c.getLong(0);
            monster.name = c.getString(c.getColumnIndex(COLUMN_MONSTER_NAME));

            monsterList.add(monster);

            Log.d(DB_LOGTAG, monster.id + ", " + monster.name + ", " + monster.lvl + ", " + monster.d_Id);
        } while (c.moveToNext()); //move cursor to next row

        db.close();
        return monsterList;
    }

    public List<TamedMonster> getTamedMonsters() {
        List<TamedMonster> tamedMonsterList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_TAMEDMONSTERS, null, null, null, null, null, null);

        boolean success = c.moveToFirst();
        if (!success) {
            return tamedMonsterList; //return empty list if false
        }

        //loop for every row in table
        do {
            TamedMonster tamedMonster = new TamedMonster();
            tamedMonster.id = c.getLong(0);
            tamedMonster.name = c.getString(c.getColumnIndex(COLUMN_TAMEDMONSTERS_NAME));

            tamedMonsterList.add(tamedMonster);

            Log.d(DB_LOGTAG, tamedMonster.id + ", " + tamedMonster.name + ", " + tamedMonster.lvl);
        } while (c.moveToNext()); //move cursor to next row

        db.close();
        return tamedMonsterList;
    }

    public boolean deleteDungeon(Dungeon dungeon){

        return deleteDungeon(dungeon.id);
    }

    private boolean deleteDungeon(long id) {
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[]{Long.toString(id)};

        int result = db.delete(TABLE_DUNGEON, COLUMN_DUNGEON_ID + "=?", selectionArgs);

        db.close();
        return result == 1;
    }

    public boolean deleteMonster(Monster monster){

        return deleteMonster(monster.id);
    }

    private boolean deleteMonster(long id) {
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[]{Long.toString(id)};

        int result = db.delete(TABLE_MONSTER, COLUMN_MONSTER_ID + "=?", selectionArgs);

        db.close();
        return result == 1;
    }

    public boolean deleteTamedMonster(TamedMonster tamedMonster) {
        return deleteTamedMonster(tamedMonster.id);
    }

    private boolean deleteTamedMonster(long id) {
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[]{Long.toString(id)};

        int result = db.delete(TABLE_TAMEDMONSTERS, COLUMN_TAMEDMONSTERS_ID + "=?", selectionArgs);

        db.close();
        return result == 1;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
