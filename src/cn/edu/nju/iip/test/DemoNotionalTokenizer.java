/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/4/6 23:20</create-date>
 *
 * <copyright file="DemoStopword.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package cn.edu.nju.iip.test;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;

import java.util.List;

/**
 * 演示自动去除停用词、自动断句的分词器
 * @author hankcs
 */
public class DemoNotionalTokenizer
{
    public static void main(String[] args)
    {
        String text = "同时，新车搭载了上汽集团与阿里巴巴合作开发的智能互联系统，将支持智能地图导航、语音识别控制、车机账户互联、4G通信以及车载WIFI等";
        // 自动去除停用词
        System.out.println(NotionalTokenizer.segment(text));    // 停用词典位于data/dictionary/stopwords.txt，可以自行修改
    }
}
