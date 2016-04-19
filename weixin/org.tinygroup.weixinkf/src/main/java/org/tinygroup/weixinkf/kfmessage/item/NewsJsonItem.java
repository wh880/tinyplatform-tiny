package org.tinygroup.weixinkf.kfmessage.item;

import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.annotation.JSONField;

public class NewsJsonItem {

	@JSONField(name="articles")
	private List<ArticleItem> articleList;

	public List<ArticleItem> getArticleList() {
		if(articleList==null){
			articleList = new ArrayList<ArticleItem>();	
		}
		return articleList;
	}

	public void setArticleList(List<ArticleItem> articleList) {
		this.articleList = articleList;
	}
	
}
