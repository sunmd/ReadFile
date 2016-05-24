package com.mbs.readfile;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.DEFAULT)
public class FileExcuteTest {
private static Logger logger = Logger.getLogger(FileExcuteTest.class);
	@Test
	public void testReadFile() {
		File test = new File(".");
		logger.debug(test.getAbsolutePath());
		FileExcute fe = FileExcute.getInstance();
		List<String> list = fe.getFile(".\\src\\test\\resoures\\test.txt");
		assertFalse(list.isEmpty());
		
	}
	
	@Test
	public void testFind() {
		FileExcute fe = FileExcute.getInstance();
		List<String> list = fe.getFile(".\\src\\test\\resoures\\test.txt");
		String path = ".\\src\\test\\resoures\\test";
		fe.delete(path, list);
	}
	
	@Test
	public void testWriteFile() {
		FileExcute fe = FileExcute.getInstance();
		String path1 = ".\\src\\test\\resoures\\test";
		String path2 = ".\\src\\test\\resoures\\test2.txt";
		fe.writeText(path1,path2);
	}

}
