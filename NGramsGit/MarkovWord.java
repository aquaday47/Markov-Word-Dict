import java.util.*;
import static java.lang.System.out;
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MarkovWord implements IMarkovModel{

    private String[] myWords;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(){
    
    }
    public MarkovWord(int order){
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    public void setTraining(String source){
        myWords = source.trim().split("\\s+");
    }
    private int indexOf(String[] words, MyWordGram probe, int start){
        for (int i = start; i<myWords.length; i++){
            String[] currArr = new String[myOrder];
            int counter = 0;
            if (i<myWords.length-myOrder){
                for (int j = 0; j<myOrder; j++){
                    currArr[j] = myWords[i+counter];
                    counter++;
                }
                MyWordGram currWg = new MyWordGram(currArr,0,myOrder); 
                if (currWg.equals(probe)){
                    return i;
                }
            }
        }
        return -1;
    }
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int ind = myRandom.nextInt(myWords.length-myOrder);
        MyWordGram first = new MyWordGram(myWords, ind, myOrder);
        for (int k = 0;k< first.length(); k++)
        {
            sb.append(first.wordAt(k));
            sb.append(" ");
        }
        MyWordGram key = first;
        for (int i = 0; i<numWords-myOrder; i++){
            ArrayList<String> followsList = getFollows(key);
            if (followsList!= null && followsList.size() != 0){
                int followInd = myRandom.nextInt(followsList.size());
                
                String followWord = followsList.get(followInd);
                sb.append(followWord);
                sb.append(" ");
                key = key.shiftAdd(followWord);
            }
        }
        return sb.toString();
    }
    
    public ArrayList<String> getFollows(MyWordGram kGram){
    ArrayList<String> res = new ArrayList<String>();
        
        int currInd = 0;
        do {
            //if (currInd!=myWords.length-myOrder
            currInd = indexOf(myWords, kGram, currInd);
            if (currInd != -1){
                res.add(myWords[currInd+myOrder]);
                currInd++;
            }
            else{
                break;}
        }
        while(currInd!=-1);
        
        return res;
        }
      
    
    public String toString(){
        return "Markov Word Ngram of order " + myOrder;
    }
    
    public void testGetFollows(){
        String source = "this is a test this is a test this is a test of words";
		String[] words = source.split("\\s+");
		int order = 2;
		MarkovWord mw = new MarkovWord(order);
		mw.setTraining(source);
		String[] tester = new String[2];
		tester[0] = "a";
		tester[1] = "test";
		MyWordGram testerWg = new MyWordGram(tester, 0, 2); 
		/*
		int test1 = mw.indexOf(myWords, testerWg, 0);
		int test1b = mw.indexOf(myWords, testerWg, 3);
		int test1c = mw.indexOf(myWords, testerWg, 4);
		int test1d = mw.indexOf(myWords, testerWg, 8);
		int test1e = mw.indexOf(myWords, testerWg, 12);
		out.println(test1 + " \t p3: " + test1b + " \t p4: " + test1c + 
		              " \t p8: " + test1d + " \t p12: " + test1e);
		  */
		//MyWordGram wg = new MyWordGram(words, 0, size);
		//out.println(wg);
		
		ArrayList<String> testList = mw.getFollows(testerWg);
		out.println(testList);
	
        
    }
    public void testIndexOf(){
        String source = "this is a test this is a test this is a test of words";
		String[] words = source.split("\\s+");
		int order = 2;
		MarkovWord mw = new MarkovWord(order);
		mw.setTraining(source);
		String[] tester = new String[2];
		tester[0] = "a";
		tester[1] = "test";
		MyWordGram testerWg = new MyWordGram(tester, 0, 2); 
		int test1 = mw.indexOf(myWords, testerWg, 0);
		int test1b = mw.indexOf(myWords, testerWg, 3);
		int test1c = mw.indexOf(myWords, testerWg, 4);
		int test1d = mw.indexOf(myWords, testerWg, 8);
		int test1e = mw.indexOf(myWords, testerWg, 12);
		out.println(test1 + " \t p3: " + test1b + " \t p4: " + test1c + 
		              " \t p8: " + test1d + " \t p12: " + test1e);
		//MyWordGram wg = new MyWordGram(words, 0, size);
		//out.println(wg);
		
        
    }
     public void testGetRandomText(){
        String source = "this is a test this is a test this is a test of words";
		String[] words = source.split("\\s+");
		int order = 2;
		MarkovWord mw = new MarkovWord(order);
		mw.setTraining(source);
		/*
		String[] tester = new String[2];
		tester[0] = "a";
		tester[1] = "test";
		MyWordGram testerWg = new MyWordGram(tester, 0, 2); 
		int test1 = mw.indexOf(myWords, testerWg, 0);
		int test1b = mw.indexOf(myWords, testerWg, 3);
		int test1c = mw.indexOf(myWords, testerWg, 4);
		int test1d = mw.indexOf(myWords, testerWg, 8);
		int test1e = mw.indexOf(myWords, testerWg, 12);
		out.println(test1 + " \t p3: " + test1b + " \t p4: " + test1c + 
		              " \t p8: " + test1d + " \t p12: " + test1e);
		              */
		String testOut = mw.getRandomText(50);
		out.println(testOut);
		//MyWordGram wg = new MyWordGram(words, 0, size);
		//out.println(wg);
		
        
    }
}
