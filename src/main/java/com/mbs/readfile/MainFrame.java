package com.mbs.readfile;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;

import com.mbs.readfile.filter.DirectoryFilter;
import com.mbs.readfile.filter.TextFilter;


/**
 * 这个类为主要执行的类，在main方法中可以启动GUI
 * 
 * @author sunmd
 *
 */
public class MainFrame implements ActionListener {
	private static Logger logger = Logger.getLogger(MainFrame.class); // 打印对象
	private JFrame frame = null; //主的frame
	private JButton fileButton = null; //文件按钮
	private JButton textButton = null; //文档按钮
	private JButton deleteButton = null; //删除按钮
	private JButton importButton = null; //导入按钮
	private JPanel upPanel = new JPanel(); // 上路两个按钮集成的面板
	private JPanel downPanel = new JPanel(); // 下路两个按钮集成的面板
	private JTextArea text = null;
	private File directoryFile = null;
	private File textFile = null;
	private JTextPane textPane = null;
	private JScrollPane jsp = null;
	public static void main(String[] args) {
		logger.debug("main Function is beagin!");
		new MainFrame();// 生成自己启动GUI
	}

	public MainFrame() {
		super();
		getFrame();
	}
	
	/**
	 * 生成面板函数
	 * @return 返回JFrame对象
	 */
	private JFrame getFrame() {
		logger.debug("getFrame frame = " + frame);
		if (frame == null) {
			frame = new JFrame();// 创建层对象
			frame.setTitle("file删除器");// 设置标题
			frame.setBounds(100, 200, 400, 300);// 设置大小
			frame.setLayout(new BorderLayout(3, 3));// 设置布局格式
			upPanel.add(getFileButton());
			upPanel.add(getTextButton());
			frame.add(upPanel,BorderLayout.NORTH);
//			frame.add(getText(),BorderLayout.CENTER);
			getText();
			getTextPane();
			frame.add(jsp,BorderLayout.CENTER);
			downPanel.add(getDeleteButton());
			downPanel.add(getImportButton());
			frame.add(downPanel,BorderLayout.SOUTH);
			frame.setVisible(true);// 显示GUI

			frame.addWindowListener(new WindowAdapter() {// 添加窗口监听

				@Override
				public void windowClosing(WindowEvent e) {// 关闭接口
					System.exit(0);// 关闭软件
				}

			});

		}

		return frame;
	}

	/**
	 * 生成文件路径选择按钮
	 * @return 返回文件按钮的JButton对象
	 */
	private JButton getFileButton() {
		logger.debug("getFileButton fileButton = " + fileButton);
		if(fileButton == null) {
			fileButton = new JButton();//创建JBtton对象
			fileButton.setText("文件按钮");//设置按钮上的提示
			fileButton.addActionListener(this);//添加消息监听机制
		}
		return fileButton;
	}
	
	/**
	 * 生成文本路径选择按钮
	 * @return 返回文本按钮的JButton对象
	 */
	private JButton getTextButton() {
		logger.debug("getTextButton textButton = " + textButton);
		if(textButton == null) {
			textButton = new JButton();//创建JBtton对象
			textButton.setText("文档按钮");//设置按钮上的提示
			textButton.addActionListener(this);//添加消息监听机制
		}
		return textButton;
	}
	
	/**
	 * 状态文本框，写入当前的状态数据
	 * @return 返回JTextArea
	 */
	private JTextArea getText() {
		logger.debug("getText text = " + text);
		if(text == null) {
			text = new JTextArea(); //创建对象
			text.setLineWrap(true);
		}
		return text;
	}
	
	private JTextPane getTextPane() {
		if(textPane == null ) {
			textPane = new JTextPane();
			jsp = new JScrollPane(textPane);
			
		}
		return textPane;
	}
	/**
	 * 删除按钮,具有删除功能的按钮
	 * @return 返回删除按钮的对象
	 */
	private JButton getDeleteButton() {
		logger.debug("getDeleteButton deleteButton = " + deleteButton);
		if(deleteButton == null ) {
			deleteButton = new JButton(); //创建删除按钮
			deleteButton.setText("删除");
			deleteButton.addActionListener(this);
		}
		return deleteButton;
	}
	
	/**
	 * 导入数据按钮
	 * @return 然会导入数据的JButton对象
	 */
	private JButton getImportButton() {
		logger.debug("getImportButton importButton = " + importButton);
		if(importButton == null) {
			importButton = new JButton();
			importButton.setText("导入");
			importButton.addActionListener(this);
		}
		return importButton;
	}
	
	public void actionPerformed(ActionEvent e) {
		logger.debug("actionPerformed is beagin");
		int result;// 设置对话框结果
		JFileChooser chooser = new JFileChooser();
		String tempStr = "";
		Object obj = e.getSource();
		if(obj == this.fileButton) {
			logger.debug("this.fileButton");
//			text.setText("");// 清除文本框数据
			textPane.setText("");
			chooser.setApproveButtonText("打开");// 设置对话框允许按钮名称
			chooser.addChoosableFileFilter(new DirectoryFilter());// 添加filter过滤文件
			chooser.setAcceptAllFileFilterUsed(false);// 关闭允许所有文件可选
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 智能选择选择路径
			chooser.setMultiSelectionEnabled(false);// 可以进行多选。
			result = chooser.showOpenDialog(frame);// 打开对话框
			switch (result) {// 对对话框结果分情况处理
			case JFileChooser.APPROVE_OPTION:// 点打开后消息回调
				directoryFile = chooser.getSelectedFile();
				tempStr = textPane.getText();
				//text.append("文件数据地址：" + directoryFile.getAbsolutePath()+"\r\n");
				textPane.setText(tempStr + "文件数据地址：" + directoryFile.getAbsolutePath()+"\r\n");
				break;
			case JFileChooser.CANCEL_OPTION:// 点击取消按钮
				text.setText("取消操作");
				break;
			default:// 其他
				text.setText("操作错误");
				System.exit(0);
				break;
			}
		}else if(obj == this.textButton) {
			logger.debug("this.textButton");
			chooser.setApproveButtonText("打开");// 设置对话框允许按钮名称
			chooser.setFileFilter(new TextFilter());// 添加filter过滤文件
			chooser.setAcceptAllFileFilterUsed(false);// 关闭允许所有文件可选
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// 智能选择文件不能选择路径
			chooser.setMultiSelectionEnabled(false);// 可以进行多选。
			result = chooser.showOpenDialog(frame);// 打开对话框
			switch (result) {// 对对话框结果分情况处理
			case JFileChooser.APPROVE_OPTION:// 点打开后消息回调
				textFile = chooser.getSelectedFile();
//				text.append("文件地址：" + textFile.getAbsolutePath() + "\r\n");
				tempStr = textPane.getText();
				textPane.setText(tempStr + "文件地址：" + textFile.getAbsolutePath() + "\r\n"); 
				
				break;
			case JFileChooser.CANCEL_OPTION:// 点击取消按钮
				text.setText("取消操作");
				break;
			default:// 其他
				text.setText("操作错误");
				System.exit(0);
				break;
			}
			
		}else if (obj == this.importButton) {
			logger.debug("this.importButton");
			if(directoryFile != null && textFile != null) {
				File[] files = FileExcute.getInstance().writeText(directoryFile, textFile);
				for(int i=0; i<files.length; i++) {
//					text.append(" 写入 - " + files[i].getName() + "\r\n");
					tempStr += " 写入 - " + files[i].getName() + "\r\n";
				}
				String str = textPane.getText();
				textPane.setText(str + tempStr);
			} else {
//				text.setText("请设置文件路径和文档路径");
				textPane.setText("请设置文件路径和文档路径");
			}
		}else if (obj == this.deleteButton) {
			logger.debug("this.deleteButton");
			if(directoryFile != null && textFile != null) {
				List<String> list = FileExcute.getInstance().readFileList(textFile);
				List<String> reList = FileExcute.getInstance().delete(directoryFile.getAbsolutePath(), list);
				for(int i=0; i<reList.size(); i++) {
//					text.append( reList.get(i) + "\r\n");
					tempStr += reList.get(i) + "\r\n";
				}
				String str = textPane.getText();
				textPane.setText(str + tempStr);
			} else {
//				text.setText("请设置文件路径和文档路径");
				textPane.setText("请设置文件路径和文档路径");
			}
		}else {
			logger.debug("error");
			System.exit(0);
		}
	}
}
