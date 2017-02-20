package org.accapto.helper;

import java.io.File;

/**
 * Helper to create file structure
 * @author EKrainz
 *
 * e.g. one needs /app/src/main/java
 * 
 *  check which folder exist, else create em
 */
public  class FileStructerBuilder{
	
	public static File generateFileWithFullPath(File f){
		System.out.println( " ... generate file with full path");
	//	System.out.println(f.toString());
		
		if (f.exists()){
		//	System.out.println( "Exits alreay");
			return f;
		}
		else{
			
			if (f.getParentFile().exists()){
			//	System.out.println(" .. create file"); 
				f.mkdir();
				
			}
			else{
				generateFileWithFullPath(f.getParentFile());
			}
			generateFileWithFullPath(f);
		}
		return null;
	}

}
