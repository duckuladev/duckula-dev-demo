package net.wicp.tams.app.duckula.controller.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.BatchV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ConfigMap;
import io.kubernetes.client.openapi.models.V1ConfigMapBuilder;
import io.kubernetes.client.openapi.models.V1Job;
import io.kubernetes.client.openapi.models.V1ObjectMetaBuilder;
import io.kubernetes.client.util.Yaml;
import net.wicp.tams.app.duckula.controller.config.k8s.ApiClientManager;
import net.wicp.tams.common.apiext.IOUtil;

public class TestK8s {
	@Test
	public void createConfigMapdDeployment() throws IOException, ApiException {
		ApiClient apiClient = ApiClientManager.getApiClient("1","dddfdafdafafa");
		V1ConfigMapBuilder v1ConfigMapBuilder = new V1ConfigMapBuilder();
		v1ConfigMapBuilder.withApiVersion("v1")
				.withMetadata(new V1ObjectMetaBuilder().withName("rjzjh-duckula-dump-char-root").build());

		Map<String, String> data = new HashMap<String, String>();
		data.put("configmap.properties",
				"common.binlog.alone.dump.global.enable=true\r\n" + "common.apiext.classload.child-first=false");
		v1ConfigMapBuilder.withData(data);
		V1ConfigMap v1ConfigMap = v1ConfigMapBuilder.build();
		System.out.println(v1ConfigMap);
		File file = new File(
				"D:\\source\\otm\\oqs-engine\\xplat-meta-oqsengine-devops-k8s\\src\\test\\resources\\configmap-root.yaml");
		V1ConfigMap yamlSvc = (V1ConfigMap) Yaml.load(file);
		System.out.println(yamlSvc);
		CoreV1Api api = new CoreV1Api(apiClient);
		String pretty = null; // 是否会漂亮输出
		String dryRun = null; //
		// V1ConfigMap createNamespacedConfigMap =
		// api.createNamespacedConfigMap("35-flink-stream-dev", yamlSvc, true,
		// pretty, dryRun);
		// System.out.println("map=" + createNamespacedConfigMap);
	}

	@Test
	public void createJob() throws IOException, ApiException {
		ApiClient apiClient = ApiClientManager.getApiClient();
		File file = new File(
				"D:\\source\\otm\\oqs-engine\\xplat-meta-oqsengine-devops-k8s\\src\\test\\resources\\duckula-job.yaml");
		V1Job yamlSvc = (V1Job) Yaml.load(file);
		BatchV1Api batchV1Api = new BatchV1Api(apiClient);
		// V1Job v1Job = batchV1Api.createNamespacedJob("35-flink-stream-dev", yamlSvc,
		// true, null, null);
		// System.out.println(v1Job);
	}

	@Test
	public void createJobByFreemark() throws IOException {
		String context = IOUtil.slurp(IOUtil.fileToInputStream("/duckula-job-param.yaml", TestK8s.class));
		// JobBean jobBean = JobBean.builder().build();
		// jobBean.setName("abc");
		// jobBean.setTag("task.3.2.7");
		// String result = FreemarkUtil.getInst().doProcessByTemp(context, jobBean);
		// System.out.println("result="+result);
	}

}
