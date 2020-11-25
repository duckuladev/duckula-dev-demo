package net.wicp.tams.duckula.demo.cassandra;

import java.util.List;
import java.util.Properties;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;

import net.wicp.tams.common.Conf;
import net.wicp.tams.common.cassandra.CassandrType;
import net.wicp.tams.common.cassandra.CassandraAssit;
import net.wicp.tams.common.cassandra.bean.Columns;
import net.wicp.tams.common.cassandra.jdbc.CassandraData;
import net.wicp.tams.common.cassandra.jdbc.CassandraDatas;
import net.wicp.tams.common.cassandra.jdbc.OptType;
import net.wicp.tams.duckula.demo.cassandra.bean.OdsResumes;
import net.wicp.tams.common.cassandra.jdbc.CassandraDatas.Builder;

public class CassandraDemo {
	public static void main(String[] args) {
		Properties props=new Properties();
		props.put("common.cassandra.pool.default.contactpoint", "192.168.1.222");
		props.put("common.cassandra.pool.default.username", "root");
		props.put("common.cassandra.pool.default.password", "123456");	
		Conf.overProp(props);
		//testinsert();
		List<OdsResumes> mappers = testMapper();
		System.out.println(mappers);
	}
	
	private static List<OdsResumes> testMapper() {
		ResultSet rs = CassandraAssit.getSession().execute("select * from gvp.ods_resumes;");
		Result<OdsResumes> map = CassandraAssit.getMappingManager().mapper(OdsResumes.class).map(rs);
		return map.all();
	}
	
	
	

	private static void testinsert() {
		Builder builder = CassandraDatas.newBuilder();
		builder.setKs("gvp");
		builder.setTb("ods_resumes");
		builder.setKey("id");
		builder.putType("id", CassandrType.uuid.name());
		CassandraData.Builder data = CassandraData.newBuilder();
		data.setOptType(OptType.insert);
		data.putValue("id", "756716f7-2e54-4715-9f00-91dcbea6cf59");
		data.putValue("name", "周俊辉");
		data.putValue("phone", "18964961172");
		data.putValue("sex", "男");
		builder.addDatas(data);
		CassandraAssit.optDatas(builder.build());
	}
}
