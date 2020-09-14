package net.wicp.tams.duckula.demo.cuslistener;

import java.io.Serializable;

import net.wicp.tams.common.Result;
import net.wicp.tams.common.binlog.alone.DuckulaAssit;
import net.wicp.tams.common.binlog.alone.ListenerConf.ColHis;
import net.wicp.tams.common.binlog.alone.ListenerConf.DuckulaEvent;
import net.wicp.tams.common.binlog.alone.binlog.bean.Rule;
import net.wicp.tams.common.binlog.alone.binlog.listener.AbsBinlogListener;

public class TestListener extends AbsBinlogListener {
	@Override
	public void doBusiTrue(Rule rule, DuckulaEvent duckulaEvent) {
		Integer age = DuckulaAssit.getValue(duckulaEvent, "age", 0);
		System.out.println("age=" + age);
		Serializable postNo = DuckulaAssit.getValue(duckulaEvent, "postNo", 0);
		System.out.println("postNo=" + postNo);
	}

	@Override
	public Result doAlterTableCallBack(Rule rule, ColHis colHis, String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doInit(Rule rule, int index) {

	}
}
