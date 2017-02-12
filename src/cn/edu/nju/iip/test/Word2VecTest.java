package cn.edu.nju.iip.test;

import java.io.IOException;
import java.util.Set;

import com.ansj.vec.Word2VEC;
import com.ansj.vec.domain.WordEntry;

public class Word2VecTest {
    public static void main(String[] args) throws IOException {
        Word2VEC w1 = new Word2VEC() ;
        w1.loadGoogleModel("wiki_chinese_word2vec(Google).model") ;
//        System.out.println(w1.getWords());
        
//        Set<WordEntry> set = w1.distance("思科");
//        for(WordEntry we:set) {
//        	System.out.println(we.name+" "+we.score);
//        }
         
        
          float[] vector = w1.getWordVector("合作");
          System.out.println(vector.length);
          for(float x:vector) {
        	  System.out.print(x+",");
          }
        
    }
}
