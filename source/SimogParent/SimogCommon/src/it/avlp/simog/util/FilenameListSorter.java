package it.avlp.simog.util;

import java.io.File;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;

public class FilenameListSorter {

	public static void main(String[] args) {
	  File dir = new File(args[0]);
	  File[] selectedFiles = dir.listFiles();

	  if(selectedFiles != null) {
	      for(int i = 0; i < selectedFiles.length; i++)
	        System.err.println(selectedFiles[i]);

	      FilenameListSorter.sortFilenameList(selectedFiles);

	      for(int i = 0; i < selectedFiles.length; i++)
	        System.err.println(selectedFiles[i]);
	    }

	}

	public static void sortFilenameList(File[] selectedFiles) {
		
		  if(selectedFiles != null) {
		      Arrays.sort(selectedFiles, new FileComparator());
		    }
	}

	private static class FileComparator implements Comparator {

		private Collator c = Collator.getInstance();
		public int compare(Object o1,Object o2) {
		  if(o1 == o2)
		    return 0;
	
		  File f1 = (File) o1;
		  File f2 = (File) o2;
	
		  if(f1.isDirectory() && f2.isFile())
		    return -1;
		  if(f1.isFile() && f2.isDirectory())
		    return 1;
	
		  return c.compare(f1.getName(), f2.getName());
		}
	}
}
