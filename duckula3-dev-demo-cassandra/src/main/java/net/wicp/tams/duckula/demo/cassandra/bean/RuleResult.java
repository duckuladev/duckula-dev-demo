package net.wicp.tams.duckula.demo.cassandra.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;

@Table(keyspace = "gvp", name = "rule_result1")
@Data
public class RuleResult {
	@Column(name = "telant_id")
	private String telantId;
	@Column(name = "statistical_date")
	private Date statisticalDate;
	@Column(name = "result")
	private Map<String, Boolean> result;
}
