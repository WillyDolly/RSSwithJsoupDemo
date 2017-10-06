package com.popland.pop.rsswithjsoupdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hai on 24/07/2016.
 */
public class CustomBaseAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<RSSitems> myItems;
    public CustomBaseAdapter(Context context,int layout,List<RSSitems> items){
        this.myContext = context;
        this.myLayout = layout;
        this.myItems = items;
    }
    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
class ViewHolder{
    ImageView imageView;
    TextView TVtitle, TVlink, TVpubDate;
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if(view == null){
         view  = inflater.inflate(R.layout.custom_layoutlv,null);
         ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.IV);
            viewHolder.TVtitle = (TextView)view.findViewById(R.id.TVtitle);
            viewHolder.TVlink = (TextView)view.findViewById(R.id.TVlink);
            viewHolder.TVpubDate = (TextView)view.findViewById(R.id.TVpubDate);
            view.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.TVtitle.setText(myItems.get(position).title);
        holder.TVlink.setText(myItems.get(position).link);
        holder.TVpubDate.setText(myItems.get(position).pubDate);
        Picasso.with(myContext).load(myItems.get(position).img).into(holder.imageView);
        return view;
    }
}
