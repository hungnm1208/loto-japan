package jp.loto.loto7;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Loto7Utils {

	TreeMap<String, Loto7> resultMap;
	TreeMap<String, Integer> loopCountMap;
	TreeSet<String> loopSet;

	public void getLotoData() {
		resultMap = new TreeMap<String, Loto7>();
		loopCountMap = new TreeMap<String, Integer>();
		loopSet = new TreeSet<String>();

		System.out.println("Max all time:");
		System.out.println(CombinatoricsUtils.binomialCoefficient(37, 7));

		Document document;
		try {
			document = Jsoup.connect("http://sougaku.com/loto7/data/list1/").get();
			Elements elems = document.select("table.bun_box2");

			if (elems != null && elems.size() > 0) {
				Element main = elems.get(0);

				// loop tr
				for (Element tr : main.select("tr")) {
					// loop td
					Element th = tr.children().first();
					if ("th".equals(th.tagName().toLowerCase())) {
						continue;
					}

					Loto7 loto7 = new Loto7();
					loto7.time = tr.children().get(0).childNode(0).toString().trim();
					loto7.no1 = StringUtils.leftPad(tr.children().get(1).childNode(0).toString().trim(), 2, "0");
					loto7.no2 = StringUtils.leftPad(tr.children().get(2).childNode(0).toString().trim(), 2, "0");
					loto7.no3 = StringUtils.leftPad(tr.children().get(3).childNode(0).toString().trim(), 2, "0");
					loto7.no4 = StringUtils.leftPad(tr.children().get(4).childNode(0).toString().trim(), 2, "0");
					loto7.no5 = StringUtils.leftPad(tr.children().get(5).childNode(0).toString().trim(), 2, "0");
					loto7.no6 = StringUtils.leftPad(tr.children().get(6).childNode(0).toString().trim(), 2, "0");
					loto7.no7 = StringUtils.leftPad(tr.children().get(7).childNode(0).toString().trim(), 2, "0");

					countLoop(loto7.no1);
					countLoop(loto7.no2);
					countLoop(loto7.no3);
					countLoop(loto7.no4);
					countLoop(loto7.no5);
					countLoop(loto7.no6);
					countLoop(loto7.no7);

					if (resultMap.containsKey(loto7.toString())) {
						loopSet.add(loto7.toString() + "-" + loto7.time);
					} else {
						resultMap.put(loto7.toString(), loto7);
					}

				}

			}

			//System.out.println(document.html());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public void checkLoto7(String test) {
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
		for (Entry<String, Loto7> entry : resultMap.entrySet()) {
			System.out.println(entry.getValue().time + ":" + entry.getKey());
		}
	}

	public void printLoopSet() {
		System.out.println("****** Print loop set *********");
		for (String loto : loopSet) {
			System.out.println(loto);
		}
	}

}
