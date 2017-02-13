package cn.edu.nju.iip.test;

import java.io.File;
import java.io.FileWriter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
/**
 * 360新闻网站新闻抓取
 * @author wangqiang
 *
 */
public class SearchSpider extends BreadthCrawler {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchSpider.class);
	
	//private int count = 0;
	
	private String company = "百度";
	
	private String keyword = "董事长";
	
	private static FileWriter filewriter;
	
	/**
	 * @param crawlPath
	 *            crawlPath is the path of the directory which maintains
	 *            information of this crawler
	 * @param autoParse
	 *            if autoParse is true,BreadthCrawler will auto extract links
	 *            which match regex rules from pag
	 */
	public SearchSpider(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		this.addSeed(new CrawlDatum("http://news.so.com/ns?q="+company+"+"+keyword+"&rank=pdate&src=srp&tn=news"));
		this.addRegex("http://news.so.com/ns\\?q=.+&pn=\\d+&tn=news&rank=pdate&j=0&src=page");
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		try {
			Document doc = page.getDoc();
			Elements reslist = doc.select("li.res-list");
			for(Element res:reslist) {
				Element e = res.select("h3").first();
				String url = e.select("a").attr("href");
				//System.out.println("url="+url);
				News news = ContentExtractor.getNewsByUrl(url);
				String content = news.getContent();
				//System.out.println("content="+content);
				String[] sentences = content.split("。");
				for(String str:sentences) {
					if(str.contains(company)&&str.contains(keyword)) {
						//System.out.println(str);
						filewriter.write(str.trim()+"\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void stratSearchSpider() {
		try{
			logger.info("************360SearchSpider start************");
			SearchSpider crawler = new SearchSpider("crawl", true);
			filewriter = new FileWriter("resources/temp.data");
			crawler.setThreads(2);
			crawler.setTopN(50000);
			crawler.start(4);
			crawler.setRetry(1);
			File file = new File("crawl");
			CommonUtil.deleteFile(file);
			logger.info("************360SearchSpider finish************");
			filewriter.close();
		}catch(Exception e) {
			logger.error("stratSearchSpider run() failed", e);
		}
	}
	public static void main(String[] args) throws Exception {
		stratSearchSpider();
	}
}
