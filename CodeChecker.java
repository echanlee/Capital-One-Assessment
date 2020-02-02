import java.io.*;
import java.util.*;

public class CodeChecker {
	static int total = 0, totalComments = 0, singleComments = 0, blockLineComments = 0, blockComments = 0, todos = 0;    
	
	public static void commentOutput(String fileName) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		if(fileName.equals(".") || !fileName.contains("."))
			return; 
		
		//Assuming that only the following types of files will be input
		if(fileName.endsWith(".java") || fileName.endsWith(".c") || fileName.endsWith(".js") || fileName.endsWith(".cpp"))
			dashComments(sc);
		else if(fileName.endsWith(".py"))
			poundComments(sc);
		
		
		outputValues();
	}
	
	private static void poundComments(Scanner sc) {
		String comments = "#", line;
		boolean block = false, previousLineWasComment = false;

		while(sc.hasNextLine()) {
			 line = sc.nextLine();
			total++;
			if(line.contains(comments)) {
				totalComments++;
				if(line.contains("TODO:"))
					todos++;
				if(previousLineWasComment) {
					blockLineComments++;
					if(!block) {
						blockComments++;
						singleComments--;
						blockLineComments++;
					}
					block = true;
				}
				else {
					singleComments++;
					previousLineWasComment = true;
				}
			}
			else {
				block = false;
				previousLineWasComment = false;
			}
		}
	}
	
	private static void dashComments(Scanner sc) {
		String[] comments = {"//", "/*", "*", "*/"};
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			total++;
			if(line.contains(comments[0])) {
				totalComments++;
				singleComments++;
				if(line.contains("TODO:"))
					todos++;
			}
			else if(line.contains(comments[1])) {
				totalComments++;
				blockLineComments++;
				blockComments++;
			}
			else if(line.startsWith(comments[2]) ||line.endsWith(comments[3])) {
				totalComments++;
				blockLineComments++;
			}
		}
	}
	
	private static void outputValues() {
		System.out.println("Total # of lines: " + total);
		System.out.println("Total # of comment lines: " + totalComments);
		System.out.println("Total # of single line comments " + singleComments);
		System.out.println("Total # of comment lines within block comments: " + blockLineComments);
		System.out.println("Total # of block line comments: "+ blockComments);
		System.out.println("Total # of TODO's: " + todos);
	}
}
