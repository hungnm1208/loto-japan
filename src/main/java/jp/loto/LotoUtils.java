package jp.loto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import de.javagl.utils.math.combinatorics.SequenceWithoutRepetitionIterable;

public class LotoUtils {
	public static Set<String> getAllPossible(String str, int size) {
		List<String> input = Arrays.asList(str.split("-"));
		Set<String> result = new TreeSet<String>();
		SequenceWithoutRepetitionIterable<String> com = new SequenceWithoutRepetitionIterable<String>(size, input);
		for (List<String> sub : com)
		{
			Collections.sort(sub);
			result.add(StringUtils.join(sub, "-"));
		}

		return result;
	}
}
