package com.freddy.stickylistheaderswithsidebardemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.freddy.stickylistheaderswithsidebardemo.bean.City;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Description:
 * Created by freddy on 16/5/26.
 */
public class ChooseCityAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {
    private Context context;
    private List<City> cities;

    private int[] sectionsIndices;//不同字母 首次出现在列表的位置
    private Character[] mSectionLetters;//首字母

    public ChooseCityAdapter(Context context, List<City> cities){
        this.context = context;
        this.cities = cities;
        sectionsIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
    }

    public void updateListView(List<City> list){
        this.cities = list;
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new HeaderViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.city_item_header, parent, false);
            viewHolder.tvHeader = (TextView) convertView.findViewById(R.id.tv_header);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }

        CharSequence charSequence = cities.get(position).getSortLetters();
        viewHolder.tvHeader.setText(charSequence);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return cities.get(position).getSortLetters().charAt(0);
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int i) {
        return cities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        final City city = cities.get(i);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.city_item, viewGroup, false);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvName.setText(city.getCityName());
        return view;
    }

    @Override
    public Object[] getSections() {
        return mSectionLetters;
    }

    @Override
    public int getPositionForSection(int section) {

        if(sectionsIndices.length == 0){
            return 0;
        }
        int letterPosition = 0;
        for (int i = 0; i < mSectionLetters.length; i++) {
            if (mSectionLetters[i] == section){
                letterPosition = i;
            }
        }
        return sectionsIndices[letterPosition];
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < sectionsIndices.length; i++) {
            if (position < sectionsIndices[i]){
                return i-1;
            }
        }
        return sectionsIndices.length - 1;
    }

    private int[] getSectionIndices(){
        List<Integer> sectionIndices = new ArrayList<Integer>();
        char lastFirstChar = cities.get(0).getSortLetters().charAt(0);
        sectionIndices.add(0);
        for (int i = 1; i < cities.size(); i++) {
            if (cities.get(i).getSortLetters().charAt(0) != lastFirstChar){
                lastFirstChar = cities.get(i).getSortLetters().charAt(0);
                sectionIndices.add(i);
            }
        }

        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    private Character[] getSectionLetters(){
        Character[] letters = new Character[sectionsIndices.length];
        for (int i = 0; i < sectionsIndices.length; i++) {
            letters[i] = cities.get(sectionsIndices[i]).getSortLetters().charAt(0);
        }
        return letters;
    }

    class ViewHolder {
        TextView tvName;
    }

    class HeaderViewHolder {
        TextView tvHeader;
    }
}
