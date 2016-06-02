package com.mbs.readfile;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.DEFAULT)
public class FileExcuteTest  {
private static Logger logger = Logger.getLogger(FileExcuteTest.class);
private static final String TESTPATH = ".\\src\\test\\resoures\\";
private static final String TESTFILEBAK = "sdw2w";
private static final String TESTFILE = "i5kof-95";
private static final int LEN = 10;
private List<String> list = null;
	@BeforeClass
	public static void testBeforeTest() {
		logger.debug("testBeforeTest - beagin");
		File file = null;
		for(int i=0; i<LEN ;i++) {
			file = new File(TESTPATH + TESTFILEBAK + i + ".txt");
			logger.debug("testBeforeTest - name = " + TESTPATH + TESTFILEBAK + i + ".txt");
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	@Test
	public void testWriteFile() {
		
		FileExcute.getInstance().writeText(TESTPATH, TESTPATH + TESTFILEBAK + "0.txt");
		list = FileExcute.getInstance().readFileList(TESTPATH + TESTFILEBAK + "0.txt");
		logger.debug("testWriteFile - list = " + list.size());
		assertNotNull(list);
		
	}
	
	@Test
	public void testReadFile() {
		FileExcute fe = FileExcute.getInstance();
		logger.debug("123131212=====" + TESTPATH + TESTFILEBAK + "0.txt");
		File file = new File(TESTPATH + TESTFILEBAK + "0.txt");
		logger.debug("123131212=====" + file.exists());
		List<String> list1 = fe.readFileList(file);
		assertNotNull(list1);
	}
	
	@Test(timeout=100)
	public void testDelete() {
		logger.debug("testDelete - beagin");
		File file = null;
		for(int i=0; i<LEN ;i++) {
			file = new File(TESTPATH + TESTFILE + i + ".txt");
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		logger.debug("testDelete - 123456 list = " + (list ==null));
		list = FileExcute.getInstance().readFileList(TESTPATH + TESTFILEBAK + "0.txt");
		logger.debug("testDelete - 1234567 list = " + (list ==null));
		
		FileExcute.getInstance().delete(TESTPATH, list);
		file = new File(TESTPATH);
		File[] files = file.listFiles();
		for(int i=0; i< files.length; i++) {
			logger.debug("testDelete - getName = " + files[i].getName());
			if(files[i].getName().indexOf(TESTFILE) >= 0) {
				fail("this delete not clear");
			}
		}
	}
	
	@AfterClass
	public static void testAfterTest() {
		File file = null;
		for(int i=0; i<LEN ;i++) {
			file = new File(TESTPATH + TESTFILEBAK + i + ".txt");
				if(file.exists()) {
					file.delete();
				}
		}
	}


//	@Test
//	public void testDemo() {
//		FileExcute fe = FileExcute.getInstance();
//		List<String> list = fe.readFileList(".\\src\\test\\resoures\\test2.txt");
//		String path = "F:\\WorkSpace\\readfile\\src\\test\\resoures\\txt";
//		fe.delete(path, list);
//		String tempStr1 = "C:\\Users\\sunmd\\Desktop\\0527\\readfile";
//		String tempStr2 = "C:\\Users\\sunmd\\Desktop\\0527\\123.txt";
//		String tempStr3 = "C:\\Users\\sunmd\\Desktop\\0530\\jmd\\txt";
//		fe.writeText(tempStr1, tempStr2);
//		List<String> list = fe.readFileList(tempStr2);
//		fe.delete(tempStr3, list);
//	}

}
