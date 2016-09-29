package com.renjie120.jsoup.dto;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ReadFromUrl {
	private String url;
	private String fileName;

	public ReadFromUrl(String url, String fileName) {
		this.url = url;
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String content;

	public String readFromUrl() {
		Document doc;
		try {
			if (content == null) {
//				File cacheFile = new File(fileName);
//				if (MockUtil.readFile(fileName).length < 1) {
					doc = Jsoup.connect(url).get();

					content = doc.toString();

//					MockUtil.writeFile(fileName, content);
//				} else {
//					return MockUtil.readFile(fileName,"\n");
//				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

}
