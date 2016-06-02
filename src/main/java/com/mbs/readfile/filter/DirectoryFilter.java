package com.mbs.readfile.filter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class DirectoryFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "Directory";
	}

}
