package cn.edu.nju.iip.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.ansj.vec.Word2VEC;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;

public class PreProcess {

	private static int word2vecSize = 200;// 词向量维数

	private static Word2VEC word2vec = new Word2VEC();

	private static String relation2id = "resources/relation2id.txt";

	private static String traindata = "resources/traindata.txt";

	public static HashMap<String, String> getRelation2id() {
		HashMap<String, String> relation2idMap = new HashMap<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(relation2id));
			String line;
			while ((line = in.readLine()) != null) {
				String[] strs = line.split(" ");
				// System.out.println(strs[0]+" "+strs[1]);
				relation2idMap.put(strs[0], strs[1]);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(relation2idMap.size());
		return relation2idMap;
	}

	public static double[] sentence2Vec(String sentence, String entity1, String entity2) {
		double[] re_vec = new double[word2vecSize];
		CustomDictionary.add(entity1);
		CustomDictionary.add(entity2);
		List<Term> termList = NotionalTokenizer.segment(sentence);
		int index1 = 0;
		int index2 = 0;
		// for(int i=0;i<termList.size();i++) {
		//
		// }
		for (Term term : termList) {
			String word = term.word;
			float[] vector = word2vec.getWordVector(word);
			if (vector == null)
				continue;
			for (int i = 0; i < word2vecSize; i++) {
				re_vec[i] += vector[i];
			}
		}
		for (int i = 0; i < word2vecSize; i++) {
			re_vec[i] /= termList.size();
		}
		CommonUtil.getNormVec(re_vec);
		// System.out.println(termList) ;
		return re_vec;
	}

	public static double[] sentence2VecPosEmbding(String sentence, String entity1, String entity2) {
		double[] re_vec = new double[word2vecSize + 2];
		CustomDictionary.add(entity1);
		CustomDictionary.add(entity2);
		List<Term> termList = NotionalTokenizer.segment(sentence);
		int index1 = 0;//头entity的位置
		int index2 = 0;//尾entity的位置
		for (int i = 0; i < termList.size(); i++) {
			if(termList.get(i).word.equals(entity1)) {
				index1 = i;
			}
			if(termList.get(i).word.equals(entity2)) {
				index2 = i;
			}
		}
		for (int i = 0; i < termList.size(); i++) {
			String word = termList.get(i).word;
			int left = Math.abs(i-index1);
			int right = Math.abs(i-index2);
			float[] vector = word2vec.getWordVector(word);
			if (vector == null)
				continue;
			float[] vecWithPos = new float[word2vecSize + 2];
			for(int j=0;j<word2vecSize;j++) {
				vecWithPos[j] = vector[j];
			}
			vecWithPos[word2vecSize] = left;
			vecWithPos[word2vecSize+1] = right;
			for (int j = 0; j < word2vecSize+2; j++) {
				re_vec[j] += vecWithPos[j];
			}
		}
		for (int i = 0; i < word2vecSize+2; i++) {
			re_vec[i] /= termList.size();
		}
		CommonUtil.getNormVec(re_vec);
		// System.out.println(termList) ;
		return re_vec;
	}

	public static void preprocess() {
		HashMap<String, String> relation2idMap = getRelation2id();
		try {
			FileWriter filewriter = new FileWriter("dataset/train.data");
			BufferedReader in = new BufferedReader(new FileReader(traindata));
			String line;
			while ((line = in.readLine()) != null && line.length() != 0) {
				String[] strs = line.split("\t");
				//double[] vec = sentence2Vec(strs[3], strs[1], strs[2]);
				double[] vec = sentence2VecPosEmbding(strs[3], strs[1], strs[2]);
				String out = "";
				for (double x : vec) {
					out = out + x + ",";
				}
				out += relation2idMap.get(strs[0]) + "\n";
				//System.out.println(out);
				filewriter.write(out);
			}
			in.close();
			filewriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println("加载词向量...");
			word2vec.loadGoogleModel("wiki_chinese_word2vec(Google).model");
			System.out.println("词向量加载成功...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		preprocess();
	}

}
