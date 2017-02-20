package cn.edu.nju.iip.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.NeuralNetwork;
import weka.core.Debug.Random;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class WekaClassifier {
	
	private int batchSize = 100;
	
	
	public static void main(String[] args) throws Exception {
		NeuralNetwork m_classifier = new NeuralNetwork();
		// 训练语料文件，官方自带的 demo 里有
		File inputFile = new File("resources\\train.arff");
		ArffLoader atf = new ArffLoader();
		atf.setFile(inputFile);
		Instances instancesTrain = atf.getDataSet(); // 读入训练文件
		instancesTrain.setClassIndex(instancesTrain.numAttributes()-1);
//		m_classifier.setBatchSize(100);
//		m_classifier.setHiddenLayers("100");
//		m_classifier.setHiddenLayersDropoutRate(0.5);
//		m_classifier.setInputLayerDropoutRate(0.2);
//		m_classifier.setMaxIterations(1000);
//		m_classifier.setNumDecimalPlaces(2);
//		m_classifier.setWeightPenalty(Math.pow(10, -8));
		m_classifier.buildClassifier(instancesTrain); // 训练
		Evaluation eval = new Evaluation(instancesTrain);
		eval.crossValidateModel(m_classifier, instancesTrain, 10, new Random(1));
		System.out.println("result:"+eval.toSummaryString());
//		// load unlabeled data
//		 Instances unlabeled = new Instances(new BufferedReader(new FileReader("resources\\unlable.arff")));
//		 // set class attribute
//		 unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
//		 // label instances
//		 for (int i = 0; i < unlabeled.numInstances(); i++) {
//		   double clsLabel = m_classifier.classifyInstance(unlabeled.instance(i));
//		   double[] array = m_classifier.distributionForInstance(unlabeled.instance(i));
//		   for(double d:array) {
//			   System.out.print(d+",");
//		   }
//		   System.out.print("\n");
//		 }
	}


}
