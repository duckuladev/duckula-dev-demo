package net.wicp.tams.app.duckula.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;

import lombok.extern.slf4j.Slf4j;
import net.wicp.tams.common.apiext.IOUtil;

@Slf4j
public abstract class BusiTools {
	public static String getVersion(String dirPath,String relaPath) {
		try {
			String context = FileUtils.readFileToString(new File(IOUtil.mergeFolderAndFilePath(dirPath, relaPath)));
			int indexOf = context.indexOf("\r\n");
			String version = context.substring(8, indexOf);
			return version;
		} catch (Exception e) {
			log.error("解析tar文件失败", e);
			return "last";
		}
	}
}
