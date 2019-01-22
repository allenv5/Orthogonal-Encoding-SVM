package hk.polyu.cslhu.HIV_1_protease_cleavage_sites.svm;

import java.io.File;

import org.apache.log4j.Logger;

import hk.polyu.cslhu.HIV_1_protease_cleavage_sites.EvoCleaveSVM;
import hk.polyu.cslhu.HIV_1_protease_cleavage_sites.SharedFunctions;

public class OrthogonalEncodingBasedSVMImpl {
	public static Logger logger = Logger.getLogger(OrthogonalEncodingBasedSVMImpl.class);

	public static void main(String[] args) {
		String rootDir = args[0];
		String cParam = args[1];
		String kernelType = args[2];
		
		String[] modes = new String[] {"train", "test"};
		for (String mode : modes) {
			String posFile = rootDir + File.separator + mode + File.separator + "pos";
			String negFile = rootDir + File.separator + mode + File.separator + "neg";
			String outputFolder = rootDir + File.separator + "ortho-encoding" + File.separator + mode;
			
			SharedFunctions.createFolder(outputFolder);
			LibSVMInputGenerator generator = new LibSVMInputGenerator();
			generator.run(posFile, negFile, outputFolder);
			
			EvoCleaveSVM.mergeAllFilesInFolder(outputFolder, rootDir + File.separator + mode + ".data");
		}

		LibSVMImpl svm = new LibSVMImpl(rootDir, "train.data", "test.data", cParam, kernelType);
		svm.run();
	}
}
