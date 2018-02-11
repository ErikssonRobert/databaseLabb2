package roberteriksson12.gmail.com.monstertamerlabb2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.Dungeon;
import roberteriksson12.gmail.com.monstertamerlabb2.R;

/**
 * Created by mrx on 2018-02-05.
 */

public class DungeonAdapter extends BaseAdapter {
    private List<Dungeon> dungeonList;
    private Context context;
    private LayoutInflater layoutInflater;

    public DungeonAdapter(Context context, List<Dungeon> list) {
        this.dungeonList = list;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dungeonList.size();
    }

    @Override
    public Object getItem(int i) {
        return dungeonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return dungeonList.get(i).id;
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

        TextView textView = view.findViewById(R.id.textViewName);
        textView.setText(dungeonList.get(i).name);
        TextView textViewFloors = view.findViewById(R.id.textView2);
        textViewFloors.setText("Number of floors: " + String.valueOf(dungeonList.get(i).floors));
        TextView textViewReward = view.findViewById(R.id.textView3);
        textViewReward.setText("Reward: " + String.valueOf(dungeonList.get(i).exp) + " experience");

        return view;
    }
}
