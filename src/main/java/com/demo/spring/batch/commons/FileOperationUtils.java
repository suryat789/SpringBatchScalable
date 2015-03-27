package com.demo.spring.batch.commons;

import java.io.File;
import java.io.IOException;

import org.springframework.util.FileCopyUtils;

public class FileOperationUtils {

	public static void moveLocalFile(String sourceDir, String destinationDir, String fileName) throws IOException {
		File fSource = new File(sourceDir + File.separator + fileName);
		File fDestination = new File(destinationDir + File.separator + fileName);

		FileCopyUtils.copy(fSource, fDestination);
		fSource.delete();
	}

}