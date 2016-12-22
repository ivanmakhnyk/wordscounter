package org.imakhnyk.samples.wordscounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		CommandLine cmd = parseInputParameters(args);

		//if incorrect input parameters provided -> exit application
		if (cmd == null) {
            return;
		}
		
		String filePath = cmd.getOptionValue("input");
		int N = Integer.parseInt(cmd.getOptionValue("top"));

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			String inputLine = null;
			
			//total 'word to count' map
			Map<String, Integer> wordToCount = new HashMap<String, Integer>();

			//read input text file line by line
			while ((inputLine = bufferedReader.readLine()) != null) {
				// get list of words
				List<String> wordsList = Arrays.asList(inputLine.split(" ")); 
				
				//create 'word to count' map for current text line
				Map<String, Integer> wordsToCountInLine = wordsListToWordCountMap(wordsList);
				
				//merge current line 'word to count' map into total 'wordToCount' map
				wordToCount = mergeWordToCountMaps(wordToCount, wordsToCountInLine);
			}
			bufferedReader.close();
			
			System.out.println("Top "+ N + " occurrences:");
			//get top N entries
			Stream<Entry<String, Integer>> topN = getTopN(wordToCount, N);
			topN.forEach((e)->System.out.println("\t" + e.getKey() + " : " + e.getValue()));
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	static Stream<Entry<String, Integer>> getTopN(Map<String, Integer> wordToCount, int n) {
		return wordToCount.entrySet().stream()
		.sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) 
		.limit(n);
	}

	static Map<String, Integer> mergeWordToCountMaps(Map<String, Integer> wordToCount, Map<String, Integer> collect) {
		wordToCount = Stream.of(wordToCount, collect).map(Map::entrySet).flatMap(Collection::stream)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
		return wordToCount;
	}

	static Map<String, Integer> wordsListToWordCountMap(List<String> wordsList) {
		Map<String, Integer> collect = wordsList.parallelStream()
				.collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum));
		return collect;
	}

	private static CommandLine parseInputParameters(String[] args) {
		Options options = new Options();

		Option input = new Option("i", "input", true, "input file path");
		input.setRequired(true);
		options.addOption(input);

		Option n = new Option("n", "top", true, "the top 'N' occurrences");
		n.setRequired(true);
		options.addOption(n);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();

		try {
			return parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("worlds-counter", options);
		}
		
		return null;
	}
}
