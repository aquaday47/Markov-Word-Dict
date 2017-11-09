//I believe the performance problem relates to indexOf
//I don't know where the premature termination is coming from
//The nextWord seems to be off
import java.util.*;
import static java.lang.System.out;
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EfficientMarkovWord implements IMarkovModel{

    private String[] myWords;
    private Random myRandom;
    private int myOrder;
    private HashMap<MyWordGram, ArrayList<String>> myDict;
    private ArrayList<MyWordGram> maxSizeVals;
    public EfficientMarkovWord(){
    
    }
    public EfficientMarkovWord(int order){
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    public void setTraining(String source){
        myWords = source.trim().split("\\s+");
        buildMap();
        //printHashMapInfo();
    }
    private void buildMap(){
        myDict = new HashMap<MyWordGram, ArrayList<String>>();
        MyWordGram currGram = null; 
        for (int i = 0; i<=myWords.length-myOrder; i++){
            
            String[] currArr = new String[myOrder];
            int gramCounter = 0;
            //int buildPassCounter = 0;
            int ind = i;   
            
            if (i<=myWords.length-myOrder){
                
               if (i == 0){
                    for (int j = 0; j<myOrder; j++){
                        currArr[j] = myWords[i+gramCounter];
                        gramCounter++;
                    }
                    currGram = new MyWordGram(currArr, 0, myOrder);
                }
                else if(i!=0&&i<myWords.length-myOrder){
                    String followsWord = myWords[i+myOrder];
                    currGram = currGram.shiftAdd(followsWord);}
                
                
                //this may be taking too much time?
                /*
                for (int j = 0; j<myOrder; j++){
                    currArr[j] = myWords[i+counter];
                    counter++;
                }
                MyWordGram currGram = new MyWordGram(currArr, 0, myOrder);
               */
                
                if (!myDict.containsKey(currGram)){
                ArrayList<String> newList = new ArrayList<String>();
                    if (i == myWords.length-myOrder){
                        myDict.put(currGram, newList);
                    }    
                    else{
                        String nextWord = myWords[ind+myOrder];
                        newList.add(nextWord);
                        
                        
                        while(ind != -1){
                            ind = indexOf(myWords, currGram, ind+1);
                            if (ind != -1){
                                newList.add(myWords[ind+myOrder]);
                                
                            }
                        }
                        
                        myDict.put(currGram, newList);
                    }
                    
                }
            }
        
        }
    }
    private int indexOf(String[] words, MyWordGram probe, int start){
        for (int i = start; i<myWords.length; i++){
            String[] currArr = new String[myOrder];
            int counter = 0;
            
            if (i<myWords.length-myOrder){
                for (int j = 0; j<myOrder; j++){
                    currArr[j] = myWords[i+j];
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
    ArrayList<String> res = myDict.get(kGram);
        
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
        EfficientMarkovWord mw = new EfficientMarkovWord(order);
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
        EfficientMarkovWord mw = new EfficientMarkovWord(order);
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
    private int getLargestList(HashMap<MyWordGram, ArrayList<String>> source){
        int maxSize = 0;
        maxSizeVals = new ArrayList<MyWordGram>();
        for (Map.Entry<MyWordGram, ArrayList<String>> set : source.entrySet()){
            ArrayList<String> currList = set.getValue();
            //out.println("key: " + set.getKey());
            if (currList.size()==maxSize){
                maxSizeVals.add(set.getKey());
            }
            else if (currList.size()>maxSize){
                maxSizeVals.clear();
                maxSizeVals.add(set.getKey());
                maxSize = currList.size();
            }
            
        }
        return maxSize;
    }
    public void printHashMapInfo(){
        
        for (MyWordGram key : myDict.keySet()){
            out.println( key.toString() + "\t" + myDict.get(key));
            out.println("num keys " + myDict.size());
            
        }
        int maxSize = getLargestList(myDict);
        out.println("max size: " + maxSize);
        out.println("values of maxSize: " + maxSizeVals);
    }
    public void testPrintHashMapInfo(){
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        int order = 2;
        EfficientMarkovWord mw = new EfficientMarkovWord(order);
        mw.setTraining(source);
        mw.getRandomText(24);
        for (MyWordGram key : mw.myDict.keySet()){
            out.println( key.toString() + "\t" + mw.myDict.get(key));
            out.println("num keys " + mw.myDict.size());
            
        }
        int maxSize = getLargestList(mw.myDict);
        out.println("max size: " + maxSize);
        out.println("values of maxSize: " + maxSizeVals);
    }
}
