package org.accapto;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.accapto.helper.InputParser;
import org.accapto.helper.Logger;
import org.accapto.tool.AppScaffolder;
import org.accapto.tool.ModelParser;

/**
 * 
 * @author krajn
 *
 */
public class Accapto {

	private InputParser inputParser;
	private File inputFile;
	private Logger logger;
	private AppScaffolder scaffold;
	private ModelParser parser;

	public void start(String[] args) {

		inputParser = new InputParser(args);

		inputFile = inputParser.getInputFile();
		logger = inputParser.getLogger();

		logger.logAnyway("Starting Accessible App Tool");

		// Parse XML File and create Java Model
		if (inputFile != null) {
			 parser = new ModelParser(inputFile, logger);
			parser.parseDSL();

			if (parser.getApp() != null) {

				// create Scaffold
				scaffold = new AppScaffolder(parser.getApp(), logger);// ,
																		// inputParser.getOutputArg());
				scaffold.generate();

				logger.logAnyway("\nGenerated app:");
				logger.logAnyway("  app name:  " + scaffold.getAppName());
				logger.logAnyway("   package:  " + scaffold.getPackageName());
				logger.logAnyway("    folder:  " + scaffold.getOutputPath());

				System.out
						.println("You can open the resulting app project with Android Studio or build it with Gradle.");
				System.out
						.println("\nThe aim of accapto is supporting and improving your app development process. Thanks for using Accapto www.accapto.org");

			} else {
				logger.logAnyway("App model is not loaded");
			}
		}

	}

	public static void main(String[] args) {

		Accapto accapto = new Accapto();
		accapto.start(args);

	}

}
