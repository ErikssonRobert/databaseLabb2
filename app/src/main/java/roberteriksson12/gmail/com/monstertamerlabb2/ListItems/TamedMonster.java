package roberteriksson12.gmail.com.monstertamerlabb2.ListItems;

/**
 * Created by mrx on 2018-02-04.
 */

public class TamedMonster {
    public long id;
    public String name;
    public int lvl;
    public int exp;
    public int order;

    public TamedMonster(String name, int lvl, int exp, int order) {
        this.name = name;
        this.lvl = lvl;
        this.exp = exp;
        this.order = order;
    }
}
