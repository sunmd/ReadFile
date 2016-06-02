package com.mbs.readfile.filter;

import java.io.File;

public class Utils {
	
	public static final String text = "txt";
	public static final String xls = "xls";

	/*
	 * Get the extension of a file.
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

}
