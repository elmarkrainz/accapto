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

		// Parse XML File and create Java Model
		if (inputFile != null) {
			ModelParser parser = new ModelParser(inputFile, logger);
			parser.parseDSL();

			if (parser.getApp() != null) {

				// create Scaffold
				AppScaffolder scaffold = new AppScaffolder(parser.getApp(),
						logger);// , inputParser.getOutputArg());
				scaffold.generate();
		
			} else {
				logger.log(" app model is not loaded");
			}
		}

	}

}
