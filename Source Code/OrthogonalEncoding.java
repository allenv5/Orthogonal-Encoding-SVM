package hk.polyu.cslhu.HIV_1_protease_cleavage_sites.svm;

import org.apache.log4j.Logger;

public class OrthogonalEncoding {
	public static Logger logger = Logger.getLogger(OrthogonalEncoding.class);
	public static int EncodingLength = 20;
	
	public static String getFeatureVector(String octamer) {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < octamer.length(); i++) {
			char c = octamer.charAt(i);
			sb.append(getEncoding(c));
		}
		
		return sb.toString();
	}
	
	private static String getEncoding(char c) {
		// TODO Auto-generated method stub
		int index = -1;
		
		switch (c) {
						case 'A':
							index = 0;
							break;
						case 'G':
							index = 1;
							break;
						case 'V':
							index = 2;
							break;
						case 'I':
							index = 3;
							break;
						case 'L':
							index = 4;
							break;
						case 'F':
							index = 5;
							break;
						case 'P':
							index = 6;
							break;
						case 'Y':
							index = 7;
							break;
						case 'M':
							index = 8;
							break;
						case 'T':
							index = 9;
							break;
						case 'S':
							index = 10;
							break;
						case 'H':
							index = 11;
							break;
						case 'N':
							index = 12;
							break;
						case 'Q':
							index = 13;
							break;
						case 'W':
							index = 14;
							break;
						case 'R':
							index = 15;
							break;
						case 'K':
							index = 16;
							break;
						case 'D':
							index = 17;
							break;
						case 'E':
							index = 18;
							break;
						case 'C':
							index = 19;
							break;
						default:
							break;
		}
		
		if (index == -1) {
			logger.error("No mapping encoding was found!");
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < EncodingLength; i++) {
			if (i == index)
				sb.append(1);
			else
				sb.append(0);
		}
		
		return sb.toString();
	}
}
