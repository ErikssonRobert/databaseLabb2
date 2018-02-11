package roberteriksson12.gmail.com.monstertamerlabb2.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import roberteriksson12.gmail.com.monstertamerlabb2.Database.DBHelper;
import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Monster;
import roberteriksson12.gmail.com.monstertamerlabb2.R;

/**
 * Created by mrx on 2018-02-05.
 */

public class MonsterAdapter extends BaseAdapter {

    private List<Monster> monsterList;
    private Context context;
    private LayoutInflater layoutInflater;

    public MonsterAdapter(Context context, List<Monster> list) {
        this.monsterList = list;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return monsterList.size();
    }

    @Override
    public Object getItem(int i) {
        return monsterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return monsterList.get(i).id;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_item_layout, parent, false);
        }
        else {
            view = convertView;
        }

        TextView textViewName = view.findViewById(R.id.textViewName);
        textViewName.setText(monsterList.get(i).name);
        TextView textViewLevel = view.findViewById(R.id.textView2);
        textViewLevel.setText("Level: " + String.valueOf(monsterList.get(i).lvl));
        TextView textViewDungeon = view.findViewById(R.id.textView3);
        textViewDungeon.setText("Habitat: " + monsterList.get(i).dungeon);

        return view;
    }
}
