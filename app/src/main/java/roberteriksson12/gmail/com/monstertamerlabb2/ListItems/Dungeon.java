package roberteriksson12.gmail.com.monstertamerlabb2.ListItems;

/**
 * Created by mrx on 2018-02-04.
 */

public class Dungeon {
    public long id;
    public String name;
    public int floors;
    public int exp;

    public Dungeon(String name, int floors, int exp) {
        this.name = name;
        this.floors = floors;
        this.exp = exp;
    }
}
