package com.example.wordcount;

import com.example.wordcount.model.WordFrequency;
import com.example.wordcount.model.WordFrequencyCalculator;
import org.apache.commons.cli.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WordcountApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WordcountApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Define the options.
		Options options = defineOptions();

		// Parse the options provided.
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse( options, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Pass the options on to the functions.
		executeFunctions(options, cmd);
	}

	private void executeFunctions(Options options, CommandLine cmd) {
		WordFrequencyCalculator wordFrequencyCalculator = new WordFrequencyCalculator();
		String textFromCmd = "";
		try {
			textFromCmd = cmd.getOptionValue("t");
		} catch (NullPointerException nullPointerException) {
			System.out.print("\nA problem arose while processing option 't'.");
			System.out.print("\nOption 't' should be non null and filled.\n\n");
			generateHelpAndExit(options);
		}
		if (cmd.hasOption("f")) {
			int highestFrequency = -1;
			try {
				highestFrequency = wordFrequencyCalculator.calculateHighestFrequency(textFromCmd);
			} catch (IllegalArgumentException illegalArgumentException) {
				System.out.print("\nA problem arose while processing option 'w'.\n");
				System.out.print(illegalArgumentException.getMessage() + "\n\n");
				generateHelpAndExit(options);
			}
			System.out.print("\n\n\tFunction: 'calculate-highest-frequency-in-text'\n");
			System.out.print("\n\tText:     \"" + textFromCmd + "\".\n");
			System.out.print("\n\tResult:   '" + highestFrequency + "'.\n\n\n");
		} else if (cmd.hasOption("s")) {
			String wordFromCmd = cmd.getOptionValue("w");
			int frequencyForWord = -1;
			try {
				frequencyForWord = wordFrequencyCalculator.calculateFrequencyForWord(textFromCmd, wordFromCmd);
			} catch (IllegalArgumentException illegalArgumentException) {
				System.out.print("\nA problem arose while processing option 'w'.\n");
				System.out.print(illegalArgumentException.getMessage() + "\n\n");
				generateHelpAndExit(options);
			} catch (NullPointerException nullPointerException) {
				System.out.print("\nA problem arose while processing option 'w'.");
				System.out.print("\nOption 'w' should be non null.\n\n");
				generateHelpAndExit(options);
			}
			System.out.print("\n\n\tFunction: 'calculate-frequency-in-text-for-word'\"\n");
			System.out.print("\n\tText:     \"" + textFromCmd + "\".\n");
			System.out.print("\n\tWord:     \"" + wordFromCmd + "\".\n");
			System.out.print("\n\tResult:   '" + frequencyForWord + "'.\n\n\n");
		} else if (cmd.hasOption("h")) {
			int intNFromCmd;
			List<WordFrequency> headWordFrequencyList = new ArrayList<>();
			try {
				intNFromCmd = Integer.parseInt(cmd.getOptionValue("n"));
				headWordFrequencyList = wordFrequencyCalculator.calculateMostFrequentNWords(textFromCmd, intNFromCmd);
			} catch (NumberFormatException numberFormatException) {
				System.out.println("Option 'n' should be numeric and an integer.");
				generateHelpAndExit(options);
			} catch (NullPointerException nullPointerException) {
				System.out.print("\nA problem arose while processing option 'n'.");
				System.out.print("\nOption 'n' should be non null.\n\n");
				generateHelpAndExit(options);
			} catch (IllegalArgumentException illegalArgumentException) {
				System.out.print("\nA problem arose while processing option 'n'.\n");
				System.out.print(illegalArgumentException.getMessage() + "\n\n");
				generateHelpAndExit(options);
			}
			System.out.print("\n\n\tFunction: 'calculate-frequency-in-text-for-most-frequent-n-words'\"\n");
			System.out.print("\n\tText:     \"" + textFromCmd + "\".\n");
			System.out.print("\n\tWord Frequency List:\n");
			for (int i = 0; i < headWordFrequencyList.size(); i++) {
				WordFrequency wordFrequency = headWordFrequencyList.get(i);
				System.out.print("\n\t\tIndex:\t\t" + (i + 1) + "\n\t\tWord:\t\t" + wordFrequency.getWord() + "\n\t\tFrequency:\t" + wordFrequency.getFrequency() + "\n");
			}
		} else {
			// If we reach this branch, we are missing a critical option. Provide a hint.
			System.out.println("At least an option 'f', 'h' or 's', and an option 't' should be provided.");
			generateHelpAndExit(options);
		}
		// Do not exit, leaving the REST services running.
	}

	private void generateHelpAndExit(Options options) {
		// Generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "ant", options);
		// Exit with an error code.
		System.exit(-1);
	}

	private Options defineOptions() {
		// create Options object.
		Options options = new Options();

		// Define the options.
		OptionGroup functionOptionGroup = new OptionGroup();
		functionOptionGroup
				.isRequired();
		Option HIGHEST_FREQUENCY = Option.builder("f")
				.longOpt("calculate-highest-frequency-in-text")
				.desc("Calculates the highest frequency of the highest frequent word of a given text.")
				.build();
		functionOptionGroup.addOption(HIGHEST_FREQUENCY);
		Option FREQUENCY_FOR_WORD = Option.builder("s")
				.longOpt("calculate-frequency-in-text-for-word")
				.desc("Calculates the frequency of the given word in a given text.")
				.build();
		functionOptionGroup.addOption(FREQUENCY_FOR_WORD);
		Option TOP_N_FREQUENCIES = Option.builder("h")
				.longOpt("calculate-frequency-in-text-for-most-frequent-n-words")
				.desc("Calculates the frequencies of the given 'n' top frequent words of a given text.")
				.build();
		functionOptionGroup.addOption(TOP_N_FREQUENCIES);
		options.addOptionGroup(functionOptionGroup);

		Option text = Option.builder("t")
				.longOpt("text")
				.hasArg(true)
				.desc("String containing the text to analyze on word frequencies.")
				.required()
				.build();
		options.addOption(text);

		Option word = Option.builder("w")
				.longOpt("word")
				.hasArg(true)
				.desc("Word to find the frequency for in a given text.")
				.build();
		options.addOption(word);

		Option number = Option.builder("n")
				.longOpt("number")
				.hasArg(true)
				.desc("Number of top frequencies to find in a given text.")
				.build();
		options.addOption(number);
		return options;
	}
}
