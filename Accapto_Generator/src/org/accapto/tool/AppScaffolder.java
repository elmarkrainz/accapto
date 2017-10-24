package org.accapto.tool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.accapto.helper.Logger;
import org.accapto.model.AppType;
import org.accapto.model.ScreenType;

/**
 * Create Scaffold for android Gradle Project
 * 
 * @author EKrainz
 *
 */
public class AppScaffolder {

	

	private AppType app;
	private String appName;
	private String packageName;
	private Logger logger;

	
	private String outputPath;
	
	public AppScaffolder() {
	}

	public AppScaffolder(AppType app, Logger logger) {
		this.app = app;
		this.appName = this.app.getAppname();
		this.packageName = this.app.getPackage();
		this.logger = logger;
		
		this.outputPath = this.appName;
		//eclipse mode
		//outputPath = "../" + this.appName;
		
		//this.logger.initLog(outputPath);
		
		
	}
	
	public String getOutputPath(){
		
			
		return outputPath;
	}

	public String getAppName() {
		return appName;
		
		
	}

	public void setAppName(String appName) {
		this.appName = appName;
		
		
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public AppType getApp() {
		return app;
	}

	public void setApp(AppType app) {
		this.app = app;
		this.appName = app.getAppname();
		this.packageName = app.getPackage();
	}

	public void generate() {
		//System.out.println("Creating Directory!");
		//System.out.println("----------------------------");
		
		logger.logAnyway(".. Creating Directory! !");


		if (appName != null && packageName != null) {

			logger.logAnyway(".... Creating App Scaffold !");

		
			logger.log("../" + this.appName);

			// -------- Project + settings.gradle + build.gradle
			new File(outputPath).mkdir();

			try {
				PrintWriter printer;
				printer = new PrintWriter(new File(outputPath + "/"
						+ AccaptoConstants.SETTINGS_GRADLE));
				printer.write(AccaptoConstants.SETTINGS_GRADLE_text);
				printer.close();
				printer = null;

				printer = new PrintWriter(
						new File(outputPath + "/" + AccaptoConstants.BUILD_GRADLE));
				printer.write(AccaptoConstants.BUILD_GRADLE_TEXT);
				printer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			// ----------- app folder + build.gradle
			new File(outputPath + "/app").mkdir();

			try {
				PrintWriter printer = new PrintWriter(new File(outputPath
						+ "/app/" + AccaptoConstants.BUILD_GRADLE));

				printer.write(String.format(AccaptoConstants.APP_BUILD_GRADLE_TEXT,
						this.packageName));
				printer.close();
				printer = null;

			} catch (IOException e) {
				e.printStackTrace();
			}

			// --------------- create Manifest
			ManifesterBuilder m = new ManifesterBuilder(this.app.getPackage(),
					this.app.getAppname(), getOutputPath());

			// parse all screens
			for (ScreenType screen : app.getScreen()) {

				//System.out.println("process " + screen.getName());
				logger.log("process " + screen.getName());

				ScreenTemplating s = new ScreenTemplating(m, screen,
						this.appName, this.packageName, this.getOutputPath());

			}

			try {

				// add a11y settings
			// ---TODO REFACKTO 	m.addActivity("org.accapto.accessibility.A12ySettingsActivity");

				// gen Manifest
				m.generateDocument();

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}

			// ---- add a11y-Lib
			// unZipLib(genPath);

			addLibStuff(outputPath);

		}

		// System.out.println(".... App Scaffold created");
		logger.logAnyway(".... App Scaffold created");
	}

	private void addLibStuff(String outputFolder) {

		// ----------- folder + file
		//new File(outputFolder + "/libtest").mkdir();

		//PrintWriter printer;
		try {
			/*printer = new PrintWriter(new File(outputFolder + "/libtest/"
					+ "libfile.txt"));

			printer.write("libfile content");
			printer.close();
			printer = null;
*/

			
			String zipFile = "/libs/accessibilitypatternlib.zip";
			
			InputStream zipInput = getClass().getResourceAsStream(zipFile);
			
			ZipInputStream zis = new ZipInputStream(zipInput);
			

			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String filePath = outputFolder + File.separator + ze.getName();

				if (!ze.isDirectory()) {
					// if the entry is a file, extracts it

					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(filePath));
					byte[] bytesIn = new byte[1024];
					int read = 0;
					while ((read = zis.read(bytesIn)) != -1) {
						bos.write(bytesIn, 0, read);
					}
					bos.close();

				} else {
					// if the entry is a directory, make the directory
					File dir = new File(filePath);
					dir.mkdir();
				}

				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (Exception e) {// (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	private void unZipLib(String outputFolder) {

		// lib TODO more general
		String zipFile = "a11ylib.zip";

		try {

			// create output directory is not exists
			File folder = new File(outputFolder);
			if (!folder.exists()) {
				folder.mkdir();
			}

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(zipFile));

			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String filePath = outputFolder + File.separator + ze.getName();

				if (!ze.isDirectory()) {
					// if the entry is a file, extracts it
					// System.out.println(ze.getName());

					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(filePath));
					byte[] bytesIn = new byte[1024];
					int read = 0;
					while ((read = zis.read(bytesIn)) != -1) {
						bos.write(bytesIn, 0, read);
					}
					bos.close();

				} else {
					// if the entry is a directory, make the directory
					File dir = new File(filePath);
					dir.mkdir();
				}

				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println(".. a11y-lib added");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {

		/*
		 * System.out.println("... testing appscaffolder started");
		 * 
		 * ModelParser parser = new ModelParser(new
		 * File("accapto_model_whereami.xml")); //ModelParser parser = new
		 * ModelParser(new File("accapto_model_routingapp.xml"));
		 * parser.parseDSL();
		 * 
		 * 
		 * AppScaffolder a = new AppScaffolder(); a.setApp(parser.getApp());
		 * a.generate();
		 * 
		 * System.out.println("... testing appscaffolder finished");
		 */
	}

}