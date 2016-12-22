package org.imakhnyk.samples.wordscounter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import junit.framework.TestCase;

/**
 * Unit test for App.
 */
public class AppTest extends TestCase {

	public void testWordsListToWordCountMap() {
		List<String> wordsList = Arrays.asList("Hello", "World", "Hi", "There", "Hi");

		Map<String, Integer> wordCountMap = App.wordsListToWordCountMap(wordsList);

		assertEquals(new Integer(1), wordCountMap.get("Hello"));
		assertEquals(new Integer(1), wordCountMap.get("World"));
		assertEquals(new Integer(2), wordCountMap.get("Hi"));
		assertNull(wordCountMap.get("Unexisted"));
	}

	public void testMergeWordToCountMaps() {
		Map<String, Integer> m1 = Arrays.stream(new Object[][] 
				{ { "a", 1 }, { "b", 2 }, { "c", 3 } })
				.collect(Collectors.toMap(kv -> (String) kv[0], kv -> (Integer) kv[1]));
		Map<String, Integer> m2 = Arrays.stream(new Object[][] 
				{ { "b", 1 }, { "c", 2 }, { "d", 10 } })
				.collect(Collectors.toMap(kv -> (String) kv[0], kv -> (Integer) kv[1]));
		Map<String, Integer> res = App.mergeWordToCountMaps(m1, m2);

		assertEquals(new Integer(1), res.get("a"));
		assertEquals(new Integer(3), res.get("b"));
		assertEquals(new Integer(5), res.get("c"));
		assertEquals(new Integer(10), res.get("d"));
		assertNull(res.get("f"));
	}
}
