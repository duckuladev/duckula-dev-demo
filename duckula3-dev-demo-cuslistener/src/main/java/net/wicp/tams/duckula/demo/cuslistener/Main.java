package net.wicp.tams.duckula.demo.cuslistener;

import java.io.IOException;
import java.util.Properties;

import net.wicp.tams.common.Conf;
import net.wicp.tams.common.apiext.IOUtil;
import net.wicp.tams.common.binlog.alone.BusiAssit;

public class Main {

	public static void main(String[] args) throws IOException {
		Properties props = IOUtil.fileToProperties("/common-binlog-chk-mysql.properties", Main.class);
		Conf.overProp(props);
		BusiAssit.startListenerForConfig();
		System.in.read();
	}

}
