package net.wicp.tams.duckula.demo.cassandra.bean;

import java.math.BigDecimal;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;

@Table(keyspace = "gvp", name = "rule_config_template")
@Data
public class RuleConfigTemplate {
	@Column(name = "template_id")
	private UUID templateId;

	@Column(name = "item_config_id")
	private String itemConfigId;

	@Column(name = "item_score")
	private BigDecimal itemScore;
}
