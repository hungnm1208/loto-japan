package jp.loto.main;

import java.util.TreeMap;
import java.util.TreeSet;

import jp.loto.loto6.Loto6;
import jp.loto.loto6.Loto6Utils;
import jp.loto.loto7.Loto7Utils;

public class LotoParser {

	TreeMap<String, Loto6> resultMap;
	TreeMap<String, Integer> loopCountMap;
	TreeSet<String> loopSet;

	public static void main(String[] args) {
		LotoParser parser = new LotoParser();

		// Loto6
		//parser.checkLoto6();

		// Loto7
		parser.checkLoto7();
	}

	private void checkLoto6() {
		Loto6Utils loto6 = new Loto6Utils();
		loto6.getLotoData();

		loto6.checkLoto6("01-08-09-12-30-31");
		loto6.checkLoto6("09-10-19-20-38-41");
		loto6.checkLoto6("08-09-12-18-30-35");

		loto6.printLoopCount();
		loto6.printLoopSet();
	}

	private void checkLoto7() {
		Loto7Utils loto7 = new Loto7Utils();
		loto7.getLotoData();

		loto7.checkLoto7("01-08-09-11-12-30-31");
		loto7.checkLoto7("08-09-12-18-28-30-35");

		loto7.printLoopCount();
		loto7.printLoopSet();
	}

}
