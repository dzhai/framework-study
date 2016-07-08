package net.xulingbo.zookeeper;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class GBK2UTF8 {

	public static void main(String[] args) throws IOException {
		// GBK
		String srcDirPath = "D:\\gitproject\\framework-study\\zookeeper\\zookeeper\\src\\main\\java\\net\\xulingbo\\zookeeper";
		// utf-8
		String utf8DirPath = "D:\\gitproject\\framework-study\\zookeeper\\zookeeper\\src\\main\\java\\net\\xulingbo\\zookeeper";

		@SuppressWarnings("unchecked")
		Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), new String[] { "java" }, true);

		for (File javaGbkFile : javaGbkFileCol) {

			String utf8FilePath = utf8DirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());

			FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(javaGbkFile, "GBK"));
		}
	}
}
