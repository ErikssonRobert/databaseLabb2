package roberteriksson12.gmail.com.monstertamerlabb2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import roberteriksson12.gmail.com.monstertamerlabb2.ListItems.TamedMonster;
import roberteriksson12.gmail.com.monstertamerlabb2.R;

/**
 * Created by mrx on 2018-02-05.
 */

public class TamedMonsterAdapter extends BaseAdapter {

    private List<TamedMonster> tamedMonsterList;
    private Context context;
    private LayoutInflater layoutInflater;

    public TamedMonsterAdapter(Context context, List<TamedMonster> list) {
        this.tamedMonsterList = list;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tamedMonsterList.size();
    }

    @Override
    public Object getItem(int i) {
        return tamedMonsterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tamedMonsterList.get(i).id;
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
        textView.setText(tamedMonsterList.get(i).name);

        return view;
    }
}
