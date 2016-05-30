package com.freddy.stickylistheaderswithsidebardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.freddy.stickylistheaderswithsidebardemo.bean.City;
import com.freddy.stickylistheaderswithsidebardemo.widget.CharacterParser;
import com.freddy.stickylistheaderswithsidebardemo.widget.CityPinyinComparator;
import com.freddy.stickylistheaderswithsidebardemo.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends AppCompatActivity {

    private StickyListHeadersListView listHeadersListView;
    private List<City> cityList;
    private ChooseCityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city_layout);
        initFakeData();
        listHeadersListView = (StickyListHeadersListView) this.findViewById(R.id.listView);
        filledData(cityList);
        CityPinyinComparator comparator = new CityPinyinComparator();
        Collections.sort(cityList, comparator);
        adapter = new ChooseCityAdapter(this, cityList);
        listHeadersListView.setAdapter(adapter);
        listHeadersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, cityList.get(i).getCityName(), Toast.LENGTH_SHORT).show();
            }
        });

        SideBar sideBar = (SideBar) this.findViewById(R.id.sidebar);
        sideBar.setValues(getLetters());
        sideBar.setOnTouchLetterChangedListener(new SideBar.OnTouchLetterChangedListener() {
            @Override
            public void onLetterChanged(String str) {
                int position = adapter.getPositionForSection(str.charAt(0));
                if (position != -1){
                    listHeadersListView.setSelection(position);
                }
            }
        });
    }

    /**
     * 添加首字母 以便排序
     * @param data city数据
     */
    private void filledData(List<City> data) {
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                City city = data.get(i);
                String pinyin = CharacterParser.getInstance().getSelling(city.getCityName());//将中文转拼音
                if (TextUtils.isEmpty(pinyin)) {
                    city.setSortLetters("#");
                } else {
                    String sortString = pinyin.substring(0, 1).toUpperCase();
                    if (sortString.matches("[A-Z]")) {
                        city.setSortLetters(sortString.toUpperCase());
                    } else {
                        city.setSortLetters("#");
                    }
                }
            }
        }
    }

    /**
     * 获得 顺序的 不重复的 首字母
     * @return 首字母列表
     */
    private String[] getLetters(){
        List<String> letters = new ArrayList<String>();
        char lastFistChar = cityList.get(0).getSortLetters().charAt(0);
        letters.add(cityList.get(0).getSortLetters());
        for (int i = 1; i < cityList.size(); i++) {
            if (lastFistChar != cityList.get(i).getSortLetters().charAt(0)){
                lastFistChar = cityList.get(i).getSortLetters().charAt(0);
                letters.add(cityList.get(i).getSortLetters());
            }
        }
        return letters.toArray(new String[letters.size()]);
    }

    /**
     * 本来打算直接用 array-item 解决了
     * 想复习一下地理知识，结果发现默写不全。。。。尴尬
     */
    private void initFakeData(){
        cityList = new ArrayList<>();
        City city0 = new City("北京市","1");
        cityList.add(city0);
        City city1 = new City("天津市","2");
        cityList.add(city1);
        City city2 = new City("重庆市","3");
        cityList.add(city2);
        City city3 = new City("上海市","4");
        cityList.add(city3);
        City city4 = new City("河北省","5");
        cityList.add(city4);
        City city5 = new City("河南省","6");
        cityList.add(city5);
        City city6 = new City("陕西省","7");
        cityList.add(city6);
        City city7 = new City("山西省","8");
        cityList.add(city7);
        City city8 = new City("辽宁省","9");
        cityList.add(city8);
        City city9 = new City("吉林省","10");
        cityList.add(city9);
        City city10 = new City("新疆省","11");
        cityList.add(city10);
        City city11 = new City("吉林省","12");
        cityList.add(city11);
        City city12 = new City("四川省","13");
        cityList.add(city12);
        City city13 = new City("西藏自治区","14");
        cityList.add(city13);
        City city14 = new City("湖北省","15");
        cityList.add(city14);
        City city15 = new City("湖南省","16");
        cityList.add(city15);
        City city16 = new City("江西省","17");
        cityList.add(city16);
        City city17 = new City("浙江省","18");
        cityList.add(city17);
        City city18 = new City("福建省","19");
        cityList.add(city18);
        City city19 = new City("广东省","20");
        cityList.add(city19);
        City city20 = new City("广西省","21");
        cityList.add(city20);
        City city21 = new City("云南省","22");
        cityList.add(city21);
        City city22 = new City("海南省","23");
        cityList.add(city22);
        City city23 = new City("贵州省","24");
        cityList.add(city23);
        City city24 = new City("安徽省","25");
        cityList.add(city24);
        City city25 = new City("香港特别行政区","26");
        cityList.add(city25);
        City city26 = new City("澳门特别行政区","27");
        cityList.add(city26);
        City city27 = new City("台湾","28");
        cityList.add(city27);





    }
}
