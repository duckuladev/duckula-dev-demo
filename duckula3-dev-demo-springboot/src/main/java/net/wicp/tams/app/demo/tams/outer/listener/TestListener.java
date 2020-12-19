package net.wicp.tams.app.demo.tams.outer.listener;

import net.wicp.tams.common.Result;
import net.wicp.tams.common.binlog.alone.DuckulaAssit;
import net.wicp.tams.common.binlog.alone.ListenerConf.ColHis;
import net.wicp.tams.common.binlog.alone.ListenerConf.DuckulaEvent;
import net.wicp.tams.common.binlog.alone.annotation.BinlogListener;
import net.wicp.tams.common.binlog.alone.binlog.bean.Rule;
import net.wicp.tams.common.binlog.alone.binlog.listener.AbsBinlogListener;

//@BinlogListener(ip = "192.168.104.107", conf = "abc", port = 3308, user = "pvuser", password = "pvuser@TM", chk = "net.wicp.tams.common.binlog.alone.checkpoint.CheckPointMysql", rule = "chps`t_sample`{}")
//chk默认为CheckPointH2db,如果不变无需配置。conf：随意，不能重复  rule：监听配置，监听duckula库sys_user表
@BinlogListener(conf = "abc", chk = "net.wicp.tams.common.binlog.alone.checkpoint.CheckPointMemory", rule = "duckula`sys_user`{}")
public class TestListener extends AbsBinlogListener {
	@Override
	public void doBusiTrue(Rule rule, DuckulaEvent duckulaEvent, boolean isSplit) {
		String username = DuckulaAssit.getValue(duckulaEvent, "username", 0);
		System.out.println("username=" + username);
	}

	// 当修改表时触发，如加字段
	@Override
	public Result doAlterTableCallBack(Rule rule, ColHis colHis, String sql) {
		return Result.getSuc();
	}

	@Override
	public void doInit(Rule rule, int index) {

	}
}
