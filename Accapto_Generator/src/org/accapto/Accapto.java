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

	public static void main(String[] args) {

		InputParser inputParser = new InputParser(args);

		File inputFile = inputParser.getInputFile();
		Logger logger = inputParser.getLogger();
		
		
		logger.logAnyway("Starting Accessible App Tool");
		
		
		// Parse XML File and create Java Model
		if (inputFile != null) {
			ModelParser parser = new ModelParser(inputFile, logger);
			parser.parseDSL();

			if (parser.getApp() != null) {

				// create Scaffold
				AppScaffolder scaffold = new AppScaffolder(parser.getApp(),
						logger);// , inputParser.getOutputArg());
				scaffold.generate();
		
				logger.logAnyway("\nGenerated app:"); 		
				logger.logAnyway("  app name:  "+ 	scaffold.getAppName());
				logger.logAnyway("   package:  "+ 	scaffold.getPackageName());
				logger.logAnyway("    folder:  "+ 	scaffold.getOutputPath());		
				
				System.out.println("You can open the resulting app project with Android Studio or build it with Gradle.");
				System.out.println("\nThe aim of accapto is supporting and improving your app development process. Thanks for using Accapto www.accapto.org");				
				
			} else {
				logger.logAnyway("App model is not loaded");
			}
		}

	}

}
