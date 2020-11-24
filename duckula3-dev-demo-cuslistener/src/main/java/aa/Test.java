package aa;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test {
	public static void main(String[] args) throws IOException {
		String string = FileUtils.readFileToString(new File(
				"D:\\source\\zjh\\duckula3-dev-demo\\duckula3-dev-demo-cuslistener\\src\\main\\resources\\quea.json"));
		JSONArray data = JSON.parseObject(string).getJSONArray("Data");
		AtomicInteger i = new AtomicInteger(0);
		data.forEach(o -> {
			JSONObject o1 = (JSONObject) o;
			String content = o1.getString("Content");
			List<String> collect = o1.getJSONArray("ListQuestionAnswerList").stream().map(o2 -> ((JSONObject) o2))
					.filter(o22 -> o22.getInteger("IsAnswer") == 1)
					.map(jsonObject -> jsonObject.getString("AnswerChoice")).collect(Collectors.toList());
			i.getAndIncrement();
			System.out.println(i + "." + content + " :" + String.join(",", collect));
		});

	}
}