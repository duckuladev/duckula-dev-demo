package net.wicp.tams.duckula.demo.cassandra.bean;

import java.util.List;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;

@Table(keyspace = "gvp", name = "ods_resumes")
@Data
public class OdsResumes {
	// column_name,kind,type,clustering_order
	@Column(name = "id")
	private UUID id;
	@Column(name = "name")
	private String name;
	@Column(name = "phone")
	private String phone;
	@Frozen
	@Column(name = "trains")
	private List<Train> trains;
}
