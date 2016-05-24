package com.mbs.readfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 这个类主要对于文件进行处理
 * @author sunmd
 *
 */
public class FileExcute {
	private static class LazyHolder {
		private static final FileExcute INSTANCE = new FileExcute();
	}

	private static Logger logger = Logger.getLogger(FileExcute.class);
	
	
	private FileExcute() {
	}

	public static FileExcute getInstance() {
		return LazyHolder.INSTANCE;
	}

	public List<String> getFile(String path) {
		logger.debug("this is getFile");
		File file = new File(path);
		FileInputStream fis = null;
		BufferedReader br = null;
		List<String> list = null;
		logger.debug("exists = " + file.exists() + "canRead = "
				+ file.canRead());
		if (file.exists() && file.canRead()) {
			try {
				fis = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(fis));
				list = new ArrayList<String>();
				String str = br.readLine();
				while (str != null) {
					list.add(str);
					logger.debug("this is str = " + str);
					str = br.readLine();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
					fis.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public void delete(String path, List<String> list) {
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			String str = "";
			File[] subFiles = file.listFiles(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					logger.debug("name = " + name);
					Pattern pattern = Pattern.compile("^.*.txt$");
					Matcher matcher = pattern.matcher(name);
					// [^\\]*\\.txt$
					logger.debug("1111 = " + matcher.matches());
					return matcher.matches();
				}
			});
			for (int i = 0; i < subFiles.length; i++) {
				boolean flag = false;
				str = subFiles[i].getAbsolutePath();
				for (int j = 0; j < list.size(); j++) {

					if (str.indexOf(list.get(j)) >= 0) {
						flag = true;
					}
				}
				if (!flag) {
					subFiles[i].delete();
				}
			}
		}
	}

	public void writeText(String path1, String path2) {
		File file = new File(path1);
		File[] subFiles = file.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				logger.debug("name = " + name);
				Pattern pattern = Pattern.compile("^.*.txt$");
				Matcher matcher = pattern.matcher(name);
				logger.debug("1111 = " + matcher.matches());
				return matcher.matches();
			}
		});

		OutputStream os;
		BufferedWriter buffWrite = null;
		try {
			os = new FileOutputStream(new File(path2));
			buffWrite = new BufferedWriter(new OutputStreamWriter(os));
			for (int i = 0; i < subFiles.length; i++) {
				buffWrite.write(subFiles[i].getName());
				buffWrite.newLine();
				buffWrite.flush();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buffWrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
