package com.mak.baca.card;

import java.util.ArrayList;

public class TestGame {

	public static void main(String[] args) {
		ArrayList<String> l = new ArrayList<String>();
		l.add("T");
		l.add("T");
		l.add("P");
		l.add("T");
		l.add("B");
		l.add("T");
		l.add("B");
		l.add("B");
		l.add("P");
		l.add("P");
		l.add("B");
		l.add("P");
		l.add("T");
		l.add("B");
		l.add("T");
		l.add("P");
		l.add("B");
		BaccaratGame.recordResultsInExcel(l,"");

	}

}
