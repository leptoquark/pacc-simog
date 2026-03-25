package test;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class IOReader {
	
	private static final int BUFFER_SIZE = 4096;
	public static String extractContents(String fileName) throws IOException {
        StringBuffer buffer = new StringBuffer();

        Reader reader = new FileReader(fileName);
        char[] cbuf = new char[BUFFER_SIZE];
        try {
            while (reader.read(cbuf) > 0) {
                buffer.append(cbuf);
                // memory is cheap - nullify the old cbuf so GC will collect & create new memory in a 4k chunk
                cbuf = null;
                cbuf = new char[BUFFER_SIZE];
            }
            return buffer.toString().trim();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Throwable t) {
                System.out.println("catch nel finally");
            }
        }
    }
	public static void write(String content2Write,String destinationFile){
	      try{
    	    // Create file 
    	    FileWriter fstream = new FileWriter(destinationFile);
    	    BufferedWriter out = new BufferedWriter(fstream);
    	    out.write(content2Write);
    	    //Close the output stream
    	    out.close();
	    }catch (Exception e){//Catch exception if any
	      System.err.println("Error: " + e.getMessage());
	    }
	}
	public static void append(String content2Write,String destinationFile){
	      try{
  	    // Create file 
  	    FileWriter fstream = new FileWriter(destinationFile,true);
  	    BufferedWriter out = new BufferedWriter(fstream);
  	    out.write(content2Write);
  	    //Close the output stream
  	    out.close();
	    }catch (Exception e){//Catch exception if any
	      System.err.println("Error: " + e.getMessage());
	    }
	}
}
