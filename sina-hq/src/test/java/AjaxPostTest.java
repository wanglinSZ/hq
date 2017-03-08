
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AjaxPostTest {
	private static final Logger LOG = LoggerFactory.getLogger(AjaxPostTest.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void testAjax() throws Exception {
		String url = "http://gs.amac.org.cn/amac-infodisc/api/pof/fund?page=${page}&size=20";
		JSONObject object = JSONObject.fromObject(okGet(url.replace("${page}", "1")));
		int maxPage = object.getInt("totalPages");
		int totalSize = object.getInt("totalElements");
		LOG.info("Total records :{},total pages {}", totalSize, maxPage);

		String filename = "私募基金公示.txt";
		FileWriter fw = new FileWriter(filename, true);
		fw.write("id^基金代码^基金名称^私募基金管理人名称^管理类型^运作状态^备案时间^lastQuarterUpdate^isDeputeManage^基金url^成立时间^管理人url" + "\r\n");
		for (int i = 0; i <= maxPage; i++) {
			String result = okGet(url.replace("${page}", Integer.toString(i)));
			JSONObject obj = JSONObject.fromObject(result);
			JSONArray arrays = obj.getJSONArray("content");
			for (int j = 0; j < arrays.size(); j++) {
				String id = arrays.getJSONObject(j).getString("id");
				String fundNo = arrays.getJSONObject(j).getString("fundNo");
				String fundName = arrays.getJSONObject(j).getString("fundName");
				String managerName = arrays.getJSONObject(j).getString("managerName");
				String managerType = arrays.getJSONObject(j).getString("managerType");
				String workingState = arrays.getJSONObject(j).getString("workingState");
				long putOnRecordDate = arrays.getJSONObject(j).getString("putOnRecordDate").equals("null") ? -1
						: arrays.getJSONObject(j).getLong("putOnRecordDate");
				boolean lastQuarterUpdate = arrays.getJSONObject(j).getBoolean("lastQuarterUpdate");
				String isDeputeManage = arrays.getJSONObject(j).getString("isDeputeManage");
				String detailUrl = arrays.getJSONObject(j).getString("url");

				long establishDate = arrays.getJSONObject(j).getString("establishDate").equals("null") ? -1
						: arrays.getJSONObject(j).getLong("establishDate");
				String managerUrl = arrays.getJSONObject(j).getString("managerUrl");

				StringBuilder sb = new StringBuilder();
				sb.append(id).append("^").append(fundNo).append("^").append(fundName.equals("null") ? "" : fundName)
						.append("^").append(managerName.equals("null") ? "" : managerName).append("^")
						.append(managerType.equals("null") ? "" : managerType).append("^")
						.append(workingState.equals("null") ? "" : workingState).append("^")
						.append(putOnRecordDate == -1 ? "" : sdf.format(new Date(putOnRecordDate))).append("^")
						.append(lastQuarterUpdate).append("^")
						.append(isDeputeManage.equals("null") ? "" : isDeputeManage).append("^")
						.append("http://gs.amac.org.cn/amac-infodisc/res/pof/fund/" + detailUrl).append("^")
						.append(establishDate == -1 ? "" : sdf.format(new Date(establishDate))).append("^")
						.append("http://gs.amac.org.cn/amac-infodisc/res/pof").append(managerUrl.replace("..", ""));
				fw.write(sb.toString() + "\r\n");
			}
			LOG.info("Page {},size:{} finish!", i,arrays.size());
		}
		fw.close();
	}

	public String okGet(String url) throws Exception {

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{}");
		Request request = new Request.Builder().url(url).post(body)
				.addHeader("accept", "application/json, text/javascript, */*; q=0.01")
				.addHeader("accept-encoding", "gzip, deflate")
				.addHeader("accept-language", "zh,en;q=0.8,zh-CN;q=0.6,en-US;q=0.4")
				.addHeader("referer", "http://gs.amac.org.cn/amac-infodisc/res/pof/fund/index.html")
				.addHeader("user-agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
				.addHeader("x-requested-with", "XMLHttpRequest").addHeader("content-length", "2")
				.addHeader("content-type", "application/json").addHeader("origin", "http://gs.amac.org.cn")
				.addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "a192dd9f-42ed-72a7-f296-468cae5846e7").build();

		Response response = client.newCall(request).execute();

		return response.body().string();
	}
}
