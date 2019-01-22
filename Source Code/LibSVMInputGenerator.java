package hk.polyu.cslhu.HIV_1_protease_cleavage_sites.svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import hk.polyu.cslhu.HIV_1_protease_cleavage_sites.Constants;
import hk.polyu.cslhu.HIV_1_protease_cleavage_sites.SharedFunctions;

public class LibSVMInputGenerator {
	public static Logger logger = Logger.getLogger(LibSVMInputGenerator.class);
	public static int NumOfRecordsInEachFile = 50;
	
	public void run(String posFile, String negFile, String outputFolder) {
		Set<String> posSet = readDataFromFile(posFile);
		Set<String> negSet = readDataFromFile(negFile);
		
		int fileID = 0;
		fileID = saveInSeqFiles(posSet, Constants.PosLabel, fileID, outputFolder);
		saveInSeqFiles(negSet, Constants.NegLabel, fileID, outputFolder);
	}

	private int saveInSeqFiles(Set<String> data, String label, int iniFileID, String outputFolder) {
		// TODO Auto-generated method stub
		String content = "";
		int fileIndex = 0, recordIndex = 0;
		
		for (String octamer : data) {
			int currIndex = recordIndex++ / NumOfRecordsInEachFile;
			
			if (currIndex != fileIndex) {
				SharedFunctions.saveFile(outputFolder + File.separator + (iniFileID + fileIndex++), content);
				content = "";
			}
			
			String vector = OrthogonalEncoding.getFeatureVector(octamer);
			for (int j = 0; j < vector.length(); j++) {
				if (j == 0) 
					content += (content.length() == 0 ? "" : "\n") + label;
				
				content += " " + (j + 1) + ":" + vector.charAt(j);
			}
		}
		
		if (content.length() > 0)
			SharedFunctions.saveFile(outputFolder + File.separator + (iniFileID + fileIndex++), content);
		
		return iniFileID + fileIndex;
	}

	private Set<String> readDataFromFile(String file) {
		// TODO Auto-generated method stub
		Set<String> dataSet = new HashSet<String>();
		BufferedReader br = null;
		String line;
		
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				String[] items = line.split(Constants.Separator);
				dataSet.add(items[0]);
			}
			
			logger.info("Read " + dataSet.size() + " records from file [" + file + "]");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataSet;
	}
}
