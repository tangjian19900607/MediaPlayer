package com.tibby.media;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpUtil {

	public static ArrayList<String> getMusicUrlList(String url) throws IOException {
		ArrayList<String> tempList = new ArrayList<String>();
		URL httpUrl = new URL(url);
		// 使用HttpURLConnection打开连接
		HttpURLConnection urlConn = (HttpURLConnection) httpUrl
				.openConnection();
		// 得到读取的内容(流)
		InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
		// 为输出创建BufferedReader
		BufferedReader buffer = new BufferedReader(in);
		String line = null;
		// 使用循环来读取获得的数据
		while (((line = buffer.readLine()) != null) && (line.length()>0)) {
			if(line.startsWith("File")){
				String tempStr = line.split("=")[1];
				tempList.add(tempStr);
			}
		}
		in.close();
		buffer.close();
		return tempList;
	}
}
