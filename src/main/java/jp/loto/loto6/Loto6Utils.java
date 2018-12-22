package jp.loto.loto6;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jp.loto.LotoUtils;

public class Loto6Utils {

	TreeMap<String, Loto6> resultMap;
	TreeMap<String, Integer> loopCountMap;
	TreeSet<String> loopSet;
	Map<Integer, Map<String, Integer>> loopAllCountMap;

	public void getLotoData() {
		resultMap = new TreeMap<String, Loto6>();
		loopCountMap = new TreeMap<String, Integer>();
		loopSet = new TreeSet<String>();
		loopAllCountMap = new HashMap<Integer, Map<String,Integer>>();


//		System.out.println("Max all time:");
//		System.out.println(CombinatoricsUtils.binomialCoefficient(43, 6));

		Document document;
		try {
			document = Jsoup.connect("http://sougaku.com/loto6/data/list1/").get();
			Elements elems = document.select("table.bun_box1");

			if (elems != null && elems.size() > 0) {
				Element main = elems.get(0);

				// loop tr
				for (Element tr : main.select("tr")) {
					// loop td
					Element th = tr.children().first();
					if ("th".equals(th.tagName().toLowerCase())) {
						continue;
					}

					Loto6 loto6 = new Loto6();
					loto6.time = tr.children().get(0).childNode(0).toString().trim();
					loto6.no1 = StringUtils.leftPad(tr.children().get(1).childNode(0).toString().trim(), 2, "0");
					loto6.no2 = StringUtils.leftPad(tr.children().get(2).childNode(0).toString().trim(), 2, "0");
					loto6.no3 = StringUtils.leftPad(tr.children().get(3).childNode(0).toString().trim(), 2, "0");
					loto6.no4 = StringUtils.leftPad(tr.children().get(4).childNode(0).toString().trim(), 2, "0");
					loto6.no5 = StringUtils.leftPad(tr.children().get(5).childNode(0).toString().trim(), 2, "0");
					loto6.no6 = StringUtils.leftPad(tr.children().get(6).childNode(0).toString().trim(), 2, "0");

					countLoop(loto6.no1);
					countLoop(loto6.no2);
					countLoop(loto6.no3);
					countLoop(loto6.no4);
					countLoop(loto6.no5);
					countLoop(loto6.no6);

					if (resultMap.containsKey(loto6.toString())) {
						loopSet.add(loto6.toString() + "-" + loto6.time);
					} else {
						resultMap.put(loto6.toString(), loto6);
					}

					addCount(loto6.toString());

				}

			}

			//System.out.println(document.html());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public void checkLoto6(String test) {
		if (resultMap.containsKey(test)) {
			System.out.println("Matched: " + test + " at " + resultMap.get(test).time);
		} else {
			System.out.println("Not match: " + test);
		}
	}

	public void countLoop(String number) {
		if (loopCountMap.containsKey(number)) {
			loopCountMap.put(number, loopCountMap.get(number) + 1);
		} else {
			loopCountMap.put(number, 1);
		}
	}

	public void printLoopCount() {
		System.out.println("****** Print loop count *********");
		for (Entry<String, Integer> entry : loopCountMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

	public void printAllResult() {
		System.out.println("****** Print all results *********");
		System.out.println("All total time: " + resultMap.size());
		for (Entry<String, Loto6> entry : resultMap.entrySet()) {
			System.out.println(entry.getValue().time + ":" + entry.getKey());
		}
	}

	public void printLoopSet() {
		System.out.println("****** Print loop set *********");
		for (String loto : loopSet) {
			System.out.println(loto);
		}
	}

	private void addCount(String str) {
		for(int i = 2; i <= 5; i++) {
			if (!loopAllCountMap.containsKey(i)) {
				loopAllCountMap.put(i, new HashMap<String, Integer>());
			}

			for (String s : LotoUtils.getAllPossible(str, i)) {
				Map<String, Integer> map = loopAllCountMap.get(i);
				if (map.containsKey(s)) {
					map.put(s, map.get(s) + 1);
				} else {
					map.put(s, 1);
				}
			}
		}
	}

	public void printLoopCount(String str) {
		for(int i = 2; i < 5; i++) {
			for (String s : LotoUtils.getAllPossible(str, i)) {
				System.out.println(s + ":" + loopAllCountMap.get(i).get(s));
			}
		}
	}

	public void printLoopCount(String str, Integer k) {
		for (String s : LotoUtils.getAllPossible(str, k)) {
			System.out.println(s + ":" + loopAllCountMap.get(k).get(s));
		}
	}

	public void printLoopCountAll() {
		Map<String, Integer> map = loopAllCountMap.get(4);
		for (Entry<String, Integer> s : map.entrySet()) {
			System.out.println(s.getKey() + ":" + s.getValue());
		}
	}


}
