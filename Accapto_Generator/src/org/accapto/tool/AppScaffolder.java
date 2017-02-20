package org.accapto.tool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

	final static String SETTINGS_GRADLE = "settings.gradle";

	// final static String SETTINGS_GRADLE_text = "include ':app'";
	final static String SETTINGS_GRADLE_text = "include ':app', ':accessibilitylib'";

	final static String BUILD_GRADLE = "build.gradle";
	final static String BUILD_GRADLE_TEXT = "buildscript { \n    repositories {\n"
			+ "jcenter() \n    } \n     dependencies {\n"
			+ "        classpath 'com.android.tools.build:gradle:2.2.3' \n    } \n}\n"
			+ "allprojects {" + "\n repositories {\n       jcenter() \n} \n}";

	String APP_BUILD_GRADLE_TEXT = ""
			+ "apply plugin: 'com.android.application'" + "\n android {"
			+ "\n   compileSdkVersion 23" + "\n  	buildToolsVersion '23.0.2'"
			+ "\n defaultConfig { " + "\n  applicationId '%s'"
			+ "\n  minSdkVersion 23" + "\n  targetSdkVersion 24"
			+ "\n 	versionCode 1" + "\n   versionName '1.0'" + "\n   }"
			+ "\n }"

			+ "\n dependencies {" + "\n compile project(':accessibilitylib')"
			+ "\n }" + "\n ";

	
	private AppType app;
	// Path where scaffold will be located
	private String path = "AppProject_Scaffold";
	
	private Logger logger;
	

	public AppScaffolder() {
	}

	public AppScaffolder(AppType app, Logger logger) {
		this.app = app;
		this.appName = this.app.getAppname();
		this.packageName= this.app.getPackage();
		this.logger = logger;
	}

	private String appName;
	private String packageName;

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
		System.out.println("Creating Directory!");
		System.out.println("----------------------------");
		new File("../" + path).mkdir();

		if (appName != null && packageName != null) {

			logger.log(".... Creating App Scaffold !");

			String genPath = "../" + this.appName;

			
			// -------- Project + settings.gradle + build.gradle
			new File(genPath).mkdir();

			try {
				PrintWriter printer = new PrintWriter(new File(genPath + "/"
						+ SETTINGS_GRADLE));
				printer.write(SETTINGS_GRADLE_text);
				printer.close();
				printer = null;

				printer = new PrintWriter(
						new File(genPath + "/" + BUILD_GRADLE));
				printer.write(BUILD_GRADLE_TEXT);
				printer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			
			//-----------  app folder + build.gradle
			new File(genPath + "/app").mkdir();

			try {
				PrintWriter printer = new PrintWriter(new File(genPath
						+ "/app/" + BUILD_GRADLE));

				printer.write(String.format(APP_BUILD_GRADLE_TEXT,
						this.packageName));
				printer.close();
				printer = null;

			} catch (IOException e) {
				e.printStackTrace();
			}

	
			//--------------- create Manifest
			ManifesterBuilder m = new ManifesterBuilder(this.app.getPackage(),
					this.app.getAppname());

			
			// parse all screens
			for (ScreenType screen : app.getScreen()) {
				
				System.out.println("process " + screen.getName());
		
				ScreenTemplating s = new ScreenTemplating(m, screen,
						this.appName, this.packageName);

			}


			try {
				
				// add a11y settings
				m.addActivity("org.accapto.accessibility.A12ySettingsActivity");
				
				//  gen Manifest
				m.generateDocument();

			
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}

			//----  add a11y-Lib
			unZipLib(genPath);
			

		}

		//System.out.println(".... App Scaffold created");
		logger.log(".... App Scaffold created");
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
		               
		                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		                byte[] bytesIn = new byte[1024];
		                int read = 0;
		                while ((read = zis.read(bytesIn)) != -1) {
		                    bos.write(bytesIn, 0, read);
		                }
		                bos.close();
		                
		                
		            } 
				 else {
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

	/*	System.out.println("... testing appscaffolder started");

		ModelParser parser = new ModelParser(new File("accapto_model_whereami.xml"));
		//ModelParser parser = new ModelParser(new  File("accapto_model_routingapp.xml"));
	   	parser.parseDSL();

	   	
		AppScaffolder a = new AppScaffolder();
		a.setApp(parser.getApp());
		a.generate();

		System.out.println("... testing appscaffolder finished");
*/
	}

}