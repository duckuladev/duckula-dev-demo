package net.wicp.tams.duckula.demo.cassandra;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import net.wicp.tams.common.Conf;
import net.wicp.tams.common.cassandra.CassandrType;
import net.wicp.tams.common.cassandra.CassandraAssit;
import net.wicp.tams.common.cassandra.jdbc.CassandraData;
import net.wicp.tams.common.cassandra.jdbc.CassandraDatas;
import net.wicp.tams.common.cassandra.jdbc.CassandraDatas.Builder;
import net.wicp.tams.common.cassandra.jdbc.OptType;
import net.wicp.tams.duckula.demo.cassandra.bean.OdsResumes;
import net.wicp.tams.duckula.demo.cassandra.bean.RuleConfigTemplate;
import net.wicp.tams.duckula.demo.cassandra.bean.RuleResult;
import net.wicp.tams.duckula.demo.cassandra.bean.Train;

public class CassandraDemo {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("common.cassandra.pool.default.contactpoint", "192.168.1.222");
		props.put("common.cassandra.pool.default.username", "root");
		props.put("common.cassandra.pool.default.password", "123456");
		Conf.overProp(props);
		// testinsert();
		// countResult();
		countScore();
	}

	private static void countScore() {
		ResultSet rs = CassandraAssit.getSession().execute(
				"select * from gvp.rule_config_template where template_id=d9bf3863-b4f3-4aac-b607-440fe1ae9d1d;");
		List<RuleConfigTemplate> listtemplate = CassandraAssit.getMappingManager().mapper(RuleConfigTemplate.class)
				.map(rs).all();
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		for (RuleConfigTemplate ruleConfigTemplate : listtemplate) {
			map.put(ruleConfigTemplate.getItemConfigId(), ruleConfigTemplate.getItemScore());
		}

		// ResultSet rs2 = CassandraAssit.getSession()
		// .execute("select * from gvp.ods_resumes where
		// id=756716f7-2e54-4715-9f00-91dcbea6cf59;");
		RuleResult ruleResult = CassandraAssit.getMappingManager().mapper(RuleResult.class).get();// .get("1","2020-11-25");
		Map<String, Boolean> result = ruleResult.getResult();
		System.out.println(result);
		BigDecimal scroe = new BigDecimal(0);
		for (String ruleItemKey : map.keySet()) {
			if (result.containsKey(ruleItemKey) && result.get(ruleItemKey)) {
				scroe=scroe.add(map.get(ruleItemKey));
			}
		}
		//更新
		ResultSet rs2 = CassandraAssit.getSession().execute(
				"update gvp.rule_result1 set result['GVP.0.3'] =true,update_time=toTimestamp(now())  where telant_id='1' and statistical_date='2020-11-25';");
        System.out.println("scroe:"+scroe);
	}

	private static void countResult() {
		ResultSet rs = CassandraAssit.getSession()
				.execute("select * from gvp.ods_resumes where id=756716f7-2e54-4715-9f00-91dcbea6cf59;");
		// Result<OdsResumes> map =
		// CassandraAssit.getMappingManager().mapper(OdsResumes.class).map(rs);
		OdsResumes odsResumes = CassandraAssit.getMappingManager().mapper(OdsResumes.class).get();
		// return map.all();
		boolean isGradeUp = false;
		if (odsResumes != null && CollectionUtils.isNotEmpty(odsResumes.getTrains())) {
			for (Train train : odsResumes.getTrains()) {
				if (Integer.parseInt(train.getGrade()) >= 1) {
					isGradeUp = true;
					break;
				}
			}
		}
		if (isGradeUp) {// 更新
			ResultSet rs2 = CassandraAssit.getSession().execute(
					"update gvp.rule_result1 set result['GVP.0.3'] =true,update_time=toTimestamp(now())  where telant_id='1' and statistical_date='2020-11-25';");
			if (rs2.wasApplied()) {
				Row row = rs2.one();
				// 有事务才会出现
				// System.out.println("applied=" + row.getBool("[applied]"));
				// TODO 发kafka消息

			} else {
				Row row = rs2.one();
				System.out.println("applied=" + row.getBool("[applied]") + ",userName=" + row.getString("userName"));
			}
		}
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
