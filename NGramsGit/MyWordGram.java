import java.util.*;
/**
 * Write a description of WordGram here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWordGram {
    
    private String[] myWords;
    private int myHash;
    
    public MyWordGram(String[] source, int start, int size){
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }
    
    public String wordAt(int pos){
        if (pos<0 || pos>=myWords.length){
            throw new IndexOutOfBoundsException("bad index in wordAt "+pos);}
        return myWords[pos];
    }
    
    public int hashCode(){
        myHash = this.toString().hashCode();
        return myHash;
        
    }
    public boolean equals(Object o){
        MyWordGram other = (MyWordGram) o;
        if (this.length()!= other.length()){
            return false;
        }
        for (int i =0; i<myWords.length; i++){
            if (!this.myWords[i].equals(other.myWords[i])){
                return false;
            }
        }
        return true;
    }
    
    public int length(){
    
        return myWords.length;
    }
    
    public MyWordGram shiftAdd(String word){
        int size = myWords.length;
        MyWordGram newWg = new MyWordGram(myWords, 0, size);
        for (int i = 0; i<myWords.length; i++){
            
            if (i==myWords.length-1){
                newWg.myWords[i] = word;
            }
            else {
                newWg.myWords[i] = newWg.myWords[i+1];
            }
        }
        return newWg;
    }
    //create helper method to allow arraycopy to make a copy offset from 0
    //would entail creating an array of size
    //I guess essentially doing what shiftAdd does
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<myWords.length; i++){
            sb.append(myWords[i]);
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
