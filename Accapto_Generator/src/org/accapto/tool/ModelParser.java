package org.accapto.tool;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.accapto.helper.Logger;
import org.accapto.model.*;

/**
 * Creates a Java Model from a given XML File.
 * 
 * @author Elmar Krainz
 *
 */
public class ModelParser {
	
	// the package where the Java Classes are located
	private static final String SCHEMA = "org.accapto.model";
	
	// XML file to parse
	private File xmlFile;
	
	// Java Model Container
	private AppType app;
	
	// Logger
	private Logger logger;

	
	public ModelParser(File dslFile, Logger log){
		this.xmlFile = dslFile;
		this.logger = log;
	}
	

	/**
	 * parsing xml input file and returns instance of apptype
	 * @return 
	 */
	public AppType parseDSL(){


		logger.log( "INFO Reading DSL file " + xmlFile.getName() + " ("+ xmlFile.length() + " Bytes) ..." );
		try {
			// Read Java Model Schema
			logger.onlyFile("INFO Reading DSL schema ...");
	//		JAXBContext jaxbContext = JAXBContext.newInstance(SCHEMA);
	Class[] classes ={AppType.class, ScreenType.class, ActionType.class, InputType.class, OutputType.class, TransitionType.class};
			JAXBContext jaxbContext = JAXBContext.newInstance(classes);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			// Unmarshall XML file
			logger.onlyFile("INFO Marshalling DSL file ...");
			Object o = jaxbUnmarshaller.unmarshal(xmlFile);
			logger.onlyFile("     " + o);

			logger.onlyFile("INFO Creating model of app ...");
			@SuppressWarnings("unchecked")
			JAXBElement<AppType> root = (JAXBElement<AppType>) jaxbUnmarshaller.unmarshal(xmlFile);
			app = root.getValue();
			logger.onlyFile("     " + app);

			// Write information of model to console
			logger.log("--------------------------------------------------");
			logger.log("APP MODEL: ");
			logger.log("     Name: " + app.getAppname());
			logger.log("     Package: " + app.getPackage());
			//logger.log("     Number of screens: " + app.getScreen().size());

			// Write specified screens to console
			if (app.getScreen().size()>0){
				logger.log("     Screens: ");
				ScreenType screen = app.getScreen().get(0);
				for (int i = 0; i < app.getScreen().size(); i++) {
					screen = app.getScreen().get(i);
					logger.log("        > " + screen.getName());
				}
				logger.log("--------------------------------------------------");
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return app;		
	}

	public AppType getApp() {
		return app;
	}
}
