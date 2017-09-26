package org.accapto.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.Oneway;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.xml.bind.JAXBElement;

import org.accapto.helper.FileStructerBuilder;
import org.accapto.model.ActionType;
import org.accapto.model.InputType;
import org.accapto.model.OutputType;
import org.accapto.model.ScreenType;
import org.accapto.model.TransitionType;

import freemarker.cache.FirstMatchTemplateConfigurationFactory;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

/**
 * Create App structure from model
 * @author Ekrainz
 *
 */

// structure app/src/main

public class ScreenTemplating {

	private static final String LAYOUT = "layout";
	private static final String ACTIVITY = "Activity";
	private String name;
//	private String description;

	private boolean isStartingActivity;
	private ManifesterBuilder manifest;

	String genpatxh;

	private ScreenType screen;
	private List<Serializable> list;

	private Configuration cfg;

	private Map<String, Object> codeTemplate;
	private Map<String, Object> layoutTemplate;

	private Writer layoutWriter;
	private Writer codeWriter;
	private Writer fileWriter;
	
	private String appName;
	private String packageName;
	private String outputPath;

	
	
	
	public ScreenTemplating(ManifesterBuilder m, ScreenType screen, 
			String appName, String packageName, String outputPath) {

		this.appName = appName;
		this.packageName=packageName;
		this.outputPath = outputPath;

		// Configuration of the template engine
		cfg = new Configuration(Configuration.VERSION_2_3_23);
		try {
			//cfg.setDirectoryForTemplateLoading(new File("templates"));
			//cfg.setDirectoryForTemplateLoading(new File(this.getClass().getClassLoader().getResource("templates").getFile()));
			cfg.setClassForTemplateLoading(this.getClass(), "/templates");
			
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		} catch (Exception e){//	(IOException e) {
			e.printStackTrace();
		}

		// consoleWriter = new OutputStreamWriter(System.out);

		// temp Writer
		layoutWriter = new StringWriter();
		codeWriter = new StringWriter();

		//fileWriter = new OutputStreamWriter(System.out);

		codeTemplate = new HashMap<String, Object>();
		layoutTemplate = new HashMap<String, Object>();

		this.manifest = m;

		// add to manifest
		name = screen.getName();
		manifest.addActivity(name, isStartingActivity);

		
		
		// create activixy -
		// create xml layout -
		// add Manifest entry - 

		list = screen.getContent();

		for (Object o : list) {

			// check if JAXB Element
			if (o instanceof JAXBElement<?>) {

				Object jaxbElem = ((JAXBElement) o).getValue();

				if (jaxbElem instanceof OutputType) {
					OutputType new_name = (OutputType) jaxbElem;
					createOutput(new_name);
				}

				if (jaxbElem instanceof InputType) {
					InputType new_name = (InputType) jaxbElem;
					createInput(new_name);
				}

				if (jaxbElem instanceof ActionType) {
					ActionType new_name = (ActionType) jaxbElem;
					createAction(new_name);
				}

				if (jaxbElem instanceof TransitionType) {
					TransitionType new_name = (TransitionType) jaxbElem;
					createTransition(new_name);
				}
				
				// subscreen is missing
			}
		}

		createLayout();
		createActivity();
		
	}

	private void createLayout() {

		
		fileWriter = getOutputFile(LAYOUT);
		Map<String, Object> basicLayout = new HashMap<String, Object>();
		basicLayout.put("additional_layout", layoutWriter.toString());

		processTemplating("basic_layout.ftl", basicLayout, fileWriter);

	}

	
	private OutputStreamWriter getOutputFile(String type){
		
		
		String path = this.outputPath + "/app/src/main/";
		String packagePath = "java/";
		String layoutPath = "res/layout/";
		
		// File to save the generated file
		String[] packageSplit = packageName.split("[.]");
		// Creates String with "org/sfsf/sgfsodeling/" (for the testcase)
		for(String s: packageSplit) {packagePath += s + "/";}
		
				
		File file= null;
		
		if (type.equals(ACTIVITY)){
			
			FileStructerBuilder.generateFileWithFullPath(new File(path + packagePath));
			file = new File(path + packagePath + name + ".java");
		}
		if (type.equals(LAYOUT)){
			
			FileStructerBuilder.generateFileWithFullPath(new File(path + layoutPath));
			file = new File(path +  layoutPath + name.toLowerCase() + ".xml");
		}
	

		FileOutputStream fileoutput=null;
		try {
			fileoutput = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return new OutputStreamWriter(fileoutput);
	}
	
		
	private void createActivity() {

		fileWriter = this.getOutputFile(ACTIVITY);
	
		Map<String, Object> basicActivity = new HashMap<String, Object>();

		basicActivity.put("package", packageName);
		basicActivity.put("imports", "// additional imports");

		basicActivity.put("activityname", name);
		basicActivity.put("activity_layout", name.toLowerCase());

		basicActivity.put("variables", "//add. vars");
		basicActivity.put("onCreate", "//oncreate");
		basicActivity.put("methods", codeWriter.toString());

		//processTemplating("basic_activity.ftl", basicActivity, fileWriter);
		processTemplating("accapto_basic_activity_new.ftl", basicActivity, fileWriter);

	}

	private void createTransition(TransitionType transition) {

		// create
		System.out.println(" Transition ");
		
		layoutTemplate.put("name_nospace",
				transition.getName().replaceAll("\\s", ""));
		layoutTemplate.put("name", transition.getName());
		layoutTemplate.put("description", transition.getDescription());
		layoutTemplate.put("function", "goTo"+transition.getTarget());
			
		codeTemplate.put("transition", transition.getTarget());
		
		
		try {
			Template template = cfg.getTemplate("accapto_action_layout.ftl");
			template.process(layoutTemplate, layoutWriter);

			Template templateCode = cfg.getTemplate("accapto_transition_code.ftl");
			templateCode.process(codeTemplate, codeWriter);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createAction(ActionType action) {

		System.out.println(" Action ");

		// create element in Layout file
		// - add onclick or else

		layoutTemplate.put("name_nospace",
				action.getName().replaceAll("\\s", ""));
		layoutTemplate.put("name", action.getName());
		layoutTemplate.put("description", action.getDescription());
		layoutTemplate.put("function", action.getFunction());

		codeTemplate.put("function", action.getFunction());

		try {
			Template template = cfg.getTemplate("accapto_action_layout.ftl");
			template.process(layoutTemplate, layoutWriter);

			Template templateCode = cfg.getTemplate("accapto_action_code.ftl");
			templateCode.process(codeTemplate, codeWriter);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createInput(InputType input) {
		System.out.println(" INput ");
		
		// if checkbox
		
		// if radio
		
		// if text
		
		layoutTemplate.put("name_nospace", input.getName().replaceAll("\\s", ""));
		layoutTemplate.put("name", input.getName());
		layoutTemplate.put("description", input.getDescription());

		
		
		
		processTemplating("accapto_input.ftl", layoutTemplate, layoutWriter);

		

	}

	private void createOutput(OutputType out) {
		System.out.println(" Output ");
		
		
		
		
		

		layoutTemplate.put("name_nospace", out.getName().replaceAll("\\s", ""));
		layoutTemplate.put("name", out.getName());
		layoutTemplate.put("description", out.getDescription());

		if (out.getType().equalsIgnoreCase("text")){
			processTemplating("accapto_output.ftl", layoutTemplate, layoutWriter);
		}

		if (out.getType().equalsIgnoreCase("image")){
			processTemplating("accapto_output_image.ftl", layoutTemplate, layoutWriter);
		}
		
		
	//	processTemplating("accapto_output.ftl", layoutTemplate, layoutWriter);

	}

	/**
	 * Starts freemarker templating engine for specified template & templating
	 * model. Writes Output into specified outputStream
	 * 
	 * @param templateString
	 *            used Template
	 * @param root
	 *            Tempalting Model
	 * @param outputstream
	 *            OutputStream
	 */
	private void processTemplating(String templateString,
			Map<String, Object> root, Writer outputstream) {
		try {
			Template temp = cfg.getTemplate(templateString);
			// temp.process(root, out);
			temp.process(root, outputstream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

}
