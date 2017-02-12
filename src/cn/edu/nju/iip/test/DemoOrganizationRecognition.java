/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/12/7 19:25</create-date>
 *
 * <copyright file="DemoChineseNameRecoginiton.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014+ 上海林原信息科技有限公司. All Right Reserved+ http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package cn.edu.nju.iip.test;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;

/**
 * 机构名识别
 * @author hankcs
 */
public class DemoOrganizationRecognition
{
    public static void main(String[] args)
    {
    	CustomDictionary.add("Infinity AR", "nz 1");
    	String str = "ZStack获得阿里云领投数千万A轮投资并发布混合云战略";
        String[] testCase = new String[]{str};
        Segment segment = HanLP.newSegment().enableOrganizationRecognize(true).enableCustomDictionary(true);
        for (String sentence : testCase)
        {
            List<Term> termList = NotionalTokenizer.segment(sentence);
            System.out.println(termList);
        }
    }
}
