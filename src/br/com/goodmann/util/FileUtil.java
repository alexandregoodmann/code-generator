package br.com.goodmann.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public List<String> readFile(String filePath) {
		File file = new File(filePath);
		return this.readFile(file);
	}

	public void writeFile(String fileName, List<String> lines) throws IOException {

		FileWriter fileW = new FileWriter(fileName);
		BufferedWriter buffW = new BufferedWriter(fileW);
		for (String line : lines) {
			buffW.write(line);
			buffW.newLine();
		}
		buffW.close();
	}

	public List<String> readFile(File file) {

		List<String> list = new ArrayList<String>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new java.io.FileReader(file));
			String st = null;
			while ((st = br.readLine()) != null) {
				list.add(st);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static void main(String[] args) throws IOException {
		FileUtil red = new FileUtil();
		/*
		 * File file = new File(
		 * "D:\\minitube\\PRINTER_PROJECT\\printer-api\\printer-api\\src\\main\\resources\\Example.svg"
		 * ); System.out.println(red.readFile(file));
		 */
		List<String> lis = new ArrayList<String>();
		lis.add("line-1");
		lis.add("line-2");
		lis.add("line-3");

		red.writeFile("D://minitube/file.txt", lis);
	}

}
