package cn.edu.nju.iip.test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;


public class CommonUtil {
	
	private static String traindata = "resources/traindata.txt";
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 从excel导入原始语料库
	 */
	public static void importFromXls() {
		Workbook workbook = null;
		FileWriter filewriter = null;
		try {
			filewriter = new FileWriter(traindata);
			workbook = Workbook.getWorkbook(new File(System
					.getProperty("user.dir") + "/resources/关系语料库-新闻.xls"));
		} catch (Exception e) {
			logger.error("importFromXls error!", e);
		}
		Sheet sheet = workbook.getSheet(0);
		int rowCount = sheet.getRows();
		logger.info("rowCount="+rowCount);
		for(int i=1;i<rowCount;i++) {
			String relation = sheet.getCell(2, i).getContents().trim();
			if(relation.length()!=0) {
				String entity1 = sheet.getCell(0, i).getContents().trim();
				String entity2 = sheet.getCell(1, i).getContents().trim();
				
				String sentence = sheet.getCell(3, i).getContents().trim();
				String line = relation+"\t"+entity1+"\t"+entity2+"\t"+sentence+"\n";
				try {
					filewriter.write(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
				//System.out.println(line);
			}
		}
	}
	
	public static void deleteFile(File file) {
		try{
			if (file.exists()) { // 判断文件是否存在
				if (file.isFile()) { // 判断是否是文件
					file.delete(); // delete()方法 你应该知道 是删除的意思;
				} else if (file.isDirectory()) { // 否则如果它是一个目录
					File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
				file.delete();
			} else {
				logger.info("所删除的文件不存在！");
			}
		}catch(Exception e) {
			logger.error("deleteFile error",e);
		}
		
	}
	
	public static void getNormVec(double[] vec) {
		if(vec==null) return;
		double sum = 0.0;
		for(int i=0;i<vec.length;i++) {
			sum += vec[i]*vec[i];
		}
		sum = Math.sqrt(sum);
		for(int i=0;i<vec.length;i++) {
			vec[i] /= sum;
		}
	}
	
	public static void main(String[] args) {
		importFromXls();
//		for(int i=0;i<200;i++) {
//			System.out.println("@attribute 'X"+i+"' numeric");
//		}
	}


}
