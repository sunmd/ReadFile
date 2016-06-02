package com.mbs.readfile.filter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 过滤后缀是text的文件
 * @author sunmd
 *
 */
public class TextFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = Utils.getExtension(f);
		if (extension != null) {
			if (extension.equals(Utils.text)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "TextFile";
	}

}
