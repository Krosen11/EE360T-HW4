import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class AnalyzeSequence {
	
	BufferedReader database;
	FileInputStream newStream;
	public AnalyzeSequence(BufferedReader database, FileInputStream newStream){
		this.database = database;
		this.newStream = newStream;
	}
	
	public void findLCS(BufferedReader database, FileInputStream newStream, ArrayList<Integer> longestSeq, ArrayList<String> subsequences){
		//This function will find the LCS in the database for newStream
		
		String inputString = null;
		int returnVal = -1;
		int maxVal = -1;
		ArrayList<String> databaseStrings = new ArrayList<String>();
		
		BufferedReader newData = new BufferedReader(new InputStreamReader(newStream));
		Scanner databaseScan = new Scanner(database);
		try {
			inputString = newData.readLine();
			while (databaseScan.hasNextLine()){
				databaseStrings.add(databaseScan.nextLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Now we need to find the LCS
		for (int i = 0; i < databaseStrings.size(); i++){
			String subsequence = lcs(databaseStrings.get(i), inputString);
			int length = subsequence.length();
			if (length > maxVal){
				maxVal = length;
				longestSeq.clear();
				subsequences.clear();
				longestSeq.add(i+1);
				subsequences.add(subsequence);
			}
			else if (length == maxVal){
				longestSeq.add(i+1);
				subsequences.add(subsequence);
			}
		}
	}
	
	public static String lcs(String a, String b) {
	    int[][] lengths = new int[a.length()+1][b.length()+1];
	 
	    // row 0 and column 0 are initialized to 0 already
	 
	    for (int i = 0; i < a.length(); i++)
	        for (int j = 0; j < b.length(); j++)
	            if (a.charAt(i) == b.charAt(j))
	                lengths[i+1][j+1] = lengths[i][j] + 1;
	            else
	                lengths[i+1][j+1] =
	                    Math.max(lengths[i+1][j], lengths[i][j+1]);
	 
	    // read the substring out from the matrix
	    StringBuffer sb = new StringBuffer();
	    for (int x = a.length(), y = b.length();
	         x != 0 && y != 0; ) {
	        if (lengths[x][y] == lengths[x-1][y])
	            x--;
	        else if (lengths[x][y] == lengths[x][y-1])
	            y--;
	        else {
	            assert a.charAt(x-1) == b.charAt(y-1);
	            sb.append(a.charAt(x-1));
	            x--;
	            y--;
	        }
	    }
	 
	    return sb.reverse().toString();
	}
	
	public static void main(String[] args){
		FileInputStream dataIn = null;
		FileInputStream newIn = null;
		ArrayList<Integer> matchingStrings = new ArrayList<Integer>();
		ArrayList<String> matchingSubsequences = new ArrayList<String>();
		try {
			dataIn = new FileInputStream(args[0]);
			newIn = new FileInputStream(args[1]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader database = new BufferedReader(new InputStreamReader(dataIn));
		AnalyzeSequence analyzer = new AnalyzeSequence(database, newIn);
		
		analyzer.findLCS(database, newIn, matchingStrings, matchingSubsequences);
		
		//Print statement
		if (matchingStrings.size() > 1){
			for (int i = 0; i < matchingStrings.size(); i++){
				System.out.print("Species " + matchingStrings.get(i));
				if (i != matchingStrings.size()-1){
					System.out.print(" and ");
				}
			}
			System.out.print(" are the most similar to the new species having a LCS of length " + matchingSubsequences.get(0).length() + ".\n");
			for (int i = 0; i < matchingStrings.size(); i++){
				System.out.println("LCS (S" + matchingStrings.get(i) + ", new-seq: " + matchingSubsequences.get(i));
			}
		}
		else if (matchingStrings.size() == 1){
			int index = matchingStrings.get(0);
			String subsequence = matchingSubsequences.get(0);
			System.out.println("Species " + index + " is the most similar to the new species having a LCS of length "
					+ subsequence.length() + ".");
			System.out.println("LCS (S" + index + ", new-seq: " + subsequence);
		}
		else {
			System.out.println("No LCS found.");
		}
		/*
		for (int i = 0; i < matchingStrings.size(); i++){
			int index = matchingStrings.get(i);
			String subsequence = matchingSubsequences.get(i);
			System.out.println("Species " + index + " is the most similar to the new species having a LCS of length "
					+ subsequence.length() + ".");
			System.out.println("LCS (S" + index + ", new-seq: " + subsequence);
		}
		*/
	}
}
