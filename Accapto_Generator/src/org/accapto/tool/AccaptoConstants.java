package org.accapto.tool;

public class AccaptoConstants {

	
	
		// ----- stettings.gradle
		public final static String SETTINGS_GRADLE = "settings.gradle";

		// final static String SETTINGS_GRADLE_text = "include ':app'";
		// final static String SETTINGS_GRADLE_text =
		// "include ':app', ':accessibilitylib'";

		// final static String SETTINGS_GRADLE_text = "include ':app'";

		public final static String SETTINGS_GRADLE_text = "include ':app', ':accessibilitypatternlib'";
		
		
		
		
		// build.gradle
		public 	final static String BUILD_GRADLE = "build.gradle";

		public final static String BUILD_GRADLE_TEXT = 
				"buildscript { \n    repositories {\n"
				+ "jcenter() \n    } \n     dependencies {\n"
				+ "        classpath 'com.android.tools.build:gradle:2.2.3' \n    } \n}\n"
				+ "allprojects {" + "\n repositories {\n       jcenter() \n} \n}";

		final static String APP_BUILD_GRADLE_TEXT = ""
				+ "apply plugin: 'com.android.application'"
				+ "\n android {"
				+ "\n   compileSdkVersion 25"
				+ "\n  	buildToolsVersion '25.0.0'" // '23.0.2'"
				+ "\n defaultConfig { " + "\n  applicationId '%s'"
				+ "\n  minSdkVersion 23" + "\n  targetSdkVersion 25"
				+ "\n 	versionCode 1" + "\n   versionName '1.0'" + "\n   }"
				+ "\n }"

				+ "\n dependencies {" + "\n //compile project(':accessibilitylib')"
				+ "\n compile project(':accessibilitypatternlib')"
				+ "\n  compile 'com.android.support:appcompat-v7:25.1.1'"
				+ "\n }" + "\n ";
}
