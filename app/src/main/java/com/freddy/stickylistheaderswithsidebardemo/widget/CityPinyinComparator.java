package com.freddy.stickylistheaderswithsidebardemo.widget;


import com.freddy.stickylistheaderswithsidebardemo.bean.City;

import java.util.Comparator;

/**
 * freddy 2016-05-30 17:48:43
 */
public class CityPinyinComparator implements Comparator<City> {

	public int compare(City o1, City o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
