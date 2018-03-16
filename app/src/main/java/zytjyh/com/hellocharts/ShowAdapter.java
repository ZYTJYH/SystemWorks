package zytjyh.com.hellocharts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by ZYTJYH on 15/3/2018.
 */

public class ShowAdapter extends ArrayAdapter<ShowItem> {

    private Context context;
    private List<ShowItem>  list;
    private int resource;
    private LayoutInflater inflater;//这个一定要懂它的用法及作用

    public ShowAdapter(@NonNull Context context, int resource, @NonNull List<ShowItem> list) {
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ShowItem getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ShowItem item=list.get(i);
        ViewHolder viewHolder;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.showlist_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.show_name);
            viewHolder.isExpolde=(TextView)convertView.findViewById(R.id.show_isExpolde);
            viewHolder.goWhich=(TextView)convertView.findViewById(R.id.show_goWhich);
            viewHolder.cnt=(TextView)convertView.findViewById(R.id.show_cnt);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.cnt.setText(""+i);
        viewHolder.name.setText(item.getName());
        viewHolder.isExpolde.setText(item.isExplode()?"撞击":"未撞击");
        viewHolder.goWhich.setText(item.getGoWhich());

    return convertView;
}
    class ViewHolder{
        TextView name;
        TextView isExpolde;
        TextView goWhich;
        TextView cnt;
    }
}
