
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import static java.lang.System.out;
public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 
    public void runEfficientMarkovN(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        EfficientMarkovWord mw = new EfficientMarkovWord(2);
        runModel(mw, st, 100, 42);
    }
    public void runMarkovN() { 
            FileResource fr = new FileResource(); 
            String st = fr.asString(); 
            st = st.replace('\n', ' '); 
            MarkovWord markovWord = new MarkovWord(3); 
            
            runModel(markovWord, st, 120, 643); 
        } 
    /*
        public void runMarkovOne() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWordOne markovWord = new MarkovWordOne(); 
        runModel(markovWord, st, 120, 139); 
    } 
    
    public void runMarkovTwo() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWordTwo markovWord = new MarkovWordTwo(); 
        runModel(markovWord, st, 120, 832); 
    } 
    */
    public void testHashMap(){
        String test = "this is a test yes this is really a test";
        EfficientMarkovWord mw = new EfficientMarkovWord(2);
        runModel(mw, test, 50, 42);
    }
    public void testHashMapTwo(){
        String test = "this is a test yes this is really a test yes a test this is wow";
        EfficientMarkovWord mw = new EfficientMarkovWord(2);
        runModel(mw, test, 50, 42);
    }
    /*
    public void testGetFollows(){
        
        String st = "this is just a test yes this is a simple test";
        MarkovWordOne markovWord = new MarkovWordOne(); 
        runModel(markovWord, st, 200); 
    }
    public void testGetFollowsM2(){
        
        String st = "this is just a test yes this is a simple test";
        MarkovWordTwo markovWord = new MarkovWordTwo(); 
        runModel(markovWord, st, 200); 
    }
    */
    public void compareMethods(){
        FileResource fr = new FileResource();
        String test = fr.asString();
        test = test.replace('\n', ' ');
        long elapsedSlow = 0;
        long elapsedFast = 0;
        
        MarkovWord mwSlow = new MarkovWord(2);
        long startSlow = System.nanoTime();
        for (int i= 0; i<5; i++){
        runModel(mwSlow, test, 100, 42);
        }
        long endSlow = System.nanoTime();
        
        FileResource fr2 = new FileResource();
        String test2 = fr2.asString();
        test = test.replace('\n', ' ');
        EfficientMarkovWord mwFast = new EfficientMarkovWord(2);
        long startFast = System.nanoTime();
        for (int i= 0; i<5; i++){
        runModel(mwFast, test2, 100, 42);
        }
        long endFast = System.nanoTime();
        
        out.println("reg time: " + (endSlow - startSlow) + " \t efficient: " + (endFast -startFast));
    }
    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 

}
