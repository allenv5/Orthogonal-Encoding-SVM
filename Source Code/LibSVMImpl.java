package hk.polyu.cslhu.HIV_1_protease_cleavage_sites.svm;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import libsvm.svm_predict;
import libsvm.svm_train;

public class LibSVMImpl {
	public static Logger logger = Logger.getLogger(LibSVMImpl.class);
	public static String LinearKernel = "0";
	public static String PolynomialKernel = "1";
	public static String RadialBasisKernel = "2";
	public static String SigmoidKernel = "3";
	public static String PrecomputedKernel = "4";
	
	private String directoryPath, trainingPath, testingPath, cParam, kernelType;
	
	public LibSVMImpl(String directoryPath, String trainingFilename, String testingFilename, String cParam, String kernelType) {
		this.directoryPath = directoryPath;
		this.trainingPath = directoryPath + File.separator + trainingFilename;
		this.testingPath = directoryPath + File.separator + testingFilename;
		this.cParam = cParam;
		this.kernelType = kernelType;
	}
	
	public void run() {
		logger.info("\n\n\nStart to train svm in " + directoryPath + " at " + new Date().toString());
		String modelFilename = train();
		
		logger.info("\n\n\nStart to test svm in " + directoryPath + " at " + new Date().toString());
		predict(modelFilename);	
		
		logger.info("\n\n\nComplete the svm task in " + directoryPath + " at " + new Date().toString());
	}
	
	private String train() {
		String[] trainingArgs = {"-t", this.kernelType, "-b", "1", "-h", "0", "-c", this.cParam, this.trainingPath};
		String modelFilename = null;
		
		try {
			modelFilename = svm_train.main(trainingArgs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return modelFilename;
	}
	
	
	private void predict(String modelFilename) {
		try {
			String[] testingArgs = {"-b", "1", this.testingPath, modelFilename, this.directoryPath + File.separator + "result"};
			svm_predict.main(testingArgs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
