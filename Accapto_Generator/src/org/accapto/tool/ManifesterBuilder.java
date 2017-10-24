package org.accapto.tool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.accapto.helper.FileStructerBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Generates Manifest form package name
 * 
 * add activities
 * 
 * 
 * generates DOM Doc
 * 
 * @author EKrainz, Anja, Johanna
 *
 */
public class ManifesterBuilder {
	
	private org.w3c.dom.Document doc;

	
	private Set<String> activities;  // Set or List
	private List<String> permissions;

	private String startingActivity;

	private String packageName;

	private String appName;


	private String genPath;
	
	
	public ManifesterBuilder(String packageString, String appName, String outputPath) {
		this.packageName=packageString;
		this.appName=appName;
		this.genPath = outputPath;
	}
	
	
	public void addActivity(String activityName) {
		
		if (this.activities == null){
			this.activities = new HashSet<String>();
			
			// set the first activity as default SET or LIST??
			this.startingActivity = activityName;
		}
		
		this.activities.add(activityName);
	
	}
	
	public void addActivity(String activityName, boolean isStarting) {
				
		addActivity(activityName);
	
		this.startingActivity = activityName;
		
	}

	
	public void addPermission(String permissionName) {
		
		if (this.permissions == null){
			this.permissions = new ArrayList<String>();
		}
		
		this.activities.add(permissionName);
	}

	
	
	public Document generateDocument() throws ParserConfigurationException, TransformerException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
	
		
		// <manifest> -------------------------------------------------------------
		Element rootElement = doc.createElement("manifest");
		doc.appendChild(rootElement);

		//Attributes for <manifest>
		//Namespace for android
		rootElement.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android");

		//Package
		rootElement.setAttribute("package", packageName);

		
		
		//<application> ----------------------------------------------------------
		Element application = doc.createElement("application");
		rootElement.appendChild(application);

		//attributes for <application>
		application.setAttribute("android:allowBackup", "true"); //?
		application.setAttribute("android:label", appName);
		application.setAttribute("android:icon", "@drawable/ic_a11y_human_web");

		application.setAttribute("android:theme", "@style/AccaptoAppTheme");
		
		
		
		//TODO alot Todos
		//TODO: Icon in Projekt kopieren
//		application.setAttribute("android:icon", "@mipmap/ic_launcher");
//		application.setAttribute("android:label", "@string/app_name");
//		application.setAttribute("android:supportsRtl", "true");
//		application.setAttribute("android:theme", "@style/AppTheme");
		
		
		for (String activityString : activities) {
						
			// add activity

			//<activity> --------------------------------------------------------------
			Element activity = doc.createElement("activity");
			application.appendChild(activity);
			activity.setAttribute("android:name",  activityString);
			
					
			// if the starting activity -> set intentfilter for starting
			if (activityString.equals(this.startingActivity)){
				
				//<intent-filter> ---------------------------------------------------------
				Element intentFilter = doc.createElement("intent-filter");
				activity.appendChild(intentFilter);
				
				
				//<action> ----------------------------------------------------------------
				Element action = doc.createElement("action");
				intentFilter.appendChild(action);
				action.setAttribute("android:name", "android.intent.action.MAIN");
				
				
				//<categoy> ---------------------------------------------------------------
				Element category = doc.createElement("category");			
				intentFilter.appendChild(category);
				category.setAttribute("android:name", "android.intent.category.LAUNCHER");
				
			}
				
		}
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
			
		//StreamResult result = new StreamResult(new File("../" + appName + "/app/src/main/AndroidManifest.xml"));
		
		
		String manifestPath = genPath +"/app/src/main/" ;
		String manifestName = "AndroidManifest.xml" ;
				
	
		File f = new File(manifestPath);
		FileStructerBuilder.generateFileWithFullPath(f);
				
		StreamResult result = new StreamResult(new File(f.getAbsolutePath()+"/"+manifestName));
		transformer.transform(source, result);

		//System.out.println(".. Manifest created");
		
	
		// - not active logger.log("File saved!");
				
		return doc;
	
	}	
	
	
	

	
	
	
	
/*	public static void main(String[] args) {
		ManifesterBuilder mf = new ManifesterBuilder("org.accapto.testapp","xTestApp");
		mf.addActivity("Main",true);
		mf.addActivity("Second");
		try {
			mf.generateDocument();
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/

	

	
	
}
