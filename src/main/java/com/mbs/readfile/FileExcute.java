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
 * 这个类主要对于文件进行处理，由于有对文件进行修改，所以这里制作成为单例模式
 * 
 * @author sunmd
 *
 */
public class FileExcute {

	/**
	 * 声明内部静态类，持有本对象引用行程，单例模式
	 * 
	 * @author sunmd
	 *
	 */
	private static class LazyHolder {
		private static final FileExcute INSTANCE = new FileExcute();
	}

	/**
	 * 静态打印对象，用来记录日志
	 */
	private static Logger logger = Logger.getLogger(FileExcute.class);

	/**
	 * 私有化内部初始化方法
	 */
	private FileExcute() {
		super();
	}

	/**
	 * 获得单向里对象
	 * 
	 * @return 返回单例的FileExcute对象
	 */
	public static FileExcute getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * 获取文件里面的数据信息，以行为单元，一行一个数据
	 * 
	 * @param file
	 *            可以读的数据文件，一行记录一个文件名称
	 * @return 返回List<String>将数据已list传递，没有数据或者不合法将返回null
	 */
	public List<String> readFileList(File file) {

		List<String> list = null;

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		boolean exists = file.exists();//是否存在文件的标识符
		boolean canRead = file.canRead();//是否能去数据标识符
		
		logger.debug("readFileList exists = " + exists + "&canRead = " + canRead);
		
		if (file.exists() && file.canRead()) {
			try {
				fis = new FileInputStream(file);
				isr = new InputStreamReader(fis);
				br = new BufferedReader(isr);
				list = new ArrayList<String>();
				String str = br.readLine();
				while (str != null) {
					list.add(str);
					logger.debug("readFileList readLine str = " + str);
					str = br.readLine();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error("readFileList FileNotFoundException ");
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("readFileList IOException ");
			} finally {
				try {
					br.close();
					isr.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("readFileList IOException ");
				} finally {
					br = null;
					isr = null;
					fis = null;
				}
			}

		}
		return list;
	}
	
	/**
	 * 获取文件里面的数据信息，以行为单元，一行一个数据
	 * @param path 数据文件路径
	 * @return 返回List<String>将数据已list传递，没有数据或者不合法将返回null
	 */
	public List<String> readFileList(String path) {
		logger.debug("getFile is beagin");
		File file = new File(path);
		List<String> list = readFileList(file);
		logger.debug("getFile is end");
		return list;
	}
	
	/**
	 * 内部类filter，只接受后缀txt文件
	 * @author sunmd
	 *
	 */
	private class TextFilter implements FilenameFilter{

		public boolean accept(File dir, String name) {
			logger.debug("TextFilter accept name = " + name);
			Pattern pattern = Pattern.compile("^.*.txt$");
			Matcher matcher = pattern.matcher(name);
			logger.debug("TextFilter matches = " + matcher.matches());
			return matcher.matches();
		}
		
	}
	
	/**
	 * 将list里面不包含的txt文件删除
	 * @param deletePath 需要删除的对应路径
	 * @param list 删除的白名单
	 */
	public void delete(String deletePath, List<String> list) {
		logger.debug("delete deletePath = " + deletePath);
		File file = new File(deletePath);
		if (file.exists() && file.isDirectory()) {
			String str = "";
			
			File[] subFiles = file.listFiles(new TextFilter());
			//对于搜索到的文件进行循环查看
			for (int i = 0; i < subFiles.length; i++) {
				boolean flag = false;
				str = subFiles[i].getAbsolutePath();
				
				for (int j = 0; j < list.size(); j++) {
					if (str.indexOf(list.get(j)) >= 0) {
						logger.debug("str = " + str + "&j=" + list.get(j));
						flag = true;
					}
				}
				
				if (!flag) {//如果没有搜索到就进行删除
					logger.debug("delete deleteFile = " + str);
					subFiles[i].delete();
				}
			}
		}
	}

	/**
	 * 根据文件夹里面的txt文件写入文件中
	 * @param FilePath 获取的文件路径
	 * @param WritePath 写入文件路径
	 */
	public void writeText(String filePath, String writePath) {
		logger.debug("writeText filePath = " + filePath + "&writePath = " + writePath);
		File file = new File(filePath);
		File[] subFiles = file.listFiles(new TextFilter());

		OutputStream os = null;
		BufferedWriter buffWrite = null;
		OutputStreamWriter osw = null;
		
		try {
			
			os = new FileOutputStream(new File(writePath));
			osw = new OutputStreamWriter(os);
			buffWrite = new BufferedWriter(osw);
			String temp = "";
			for (int i = 0; i < subFiles.length; i++) {
				//进行循环写入状态
				temp = subFiles[i].getName();
				logger.debug("writeText fileName = " + temp);
				buffWrite.write(temp);
				buffWrite.newLine();
				buffWrite.flush();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.debug("writeText FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug("writeText IOException");
		} finally {
			try {
				buffWrite.close();
				osw.close();
				os.close();
			} catch (IOException e) {
				logger.debug("writeText IOException");
				e.printStackTrace();
			} finally {
				buffWrite = null;
				osw = null;
				os = null;
			}
		}

	}

}
