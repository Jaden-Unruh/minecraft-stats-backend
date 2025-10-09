package org.j3lsmp.minecraft_stats_backend;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.core.io.ClassPathResource;

public class AdvancementParser {

	private final static Path advancementsPath = MinecraftStatsBackendApplication.IS_LOCAL_TEST ? Path.of("G:\\Minecraft Servers\\Azure server\\stats tool testing\\advancements") : Path.of("E:\\J3L+ Server Live\\webpage\\advancements");

	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss X");

	public static Map<String, String> getAdvancements(String uuid) throws IOException, ParseException {

		uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-"
				+ uuid.substring(16, 20) + "-" + uuid.substring(20, 32);

		File playerFile = advancementsPath.resolve(uuid + ".json").toFile();

		if (!playerFile.exists())
			throw new IllegalArgumentException("Player data file not found for UUID: " + uuid);

		ObjectMapper objectMapper = new ObjectMapper();
		
		JsonNode rootNode = objectMapper.readTree(playerFile);
		((ObjectNode) rootNode).remove("DataVersion");

		Map<String, AdvancementData> advancements = objectMapper.convertValue(rootNode,
				objectMapper.getTypeFactory().constructMapType(Map.class, String.class, AdvancementData.class));

		Map<String, String> ret = new HashMap<>();

		for (Map.Entry<String, AdvancementData> advancement : advancements.entrySet()) {
			String title = advancement.getKey();
			AdvancementData data = advancement.getValue();

			if (!data.done || title.startsWith("minecraft:recipes") || !title.startsWith("minecraft:"))
				continue;

			if (data.criteria.size() == 1)
				ret.put(title.replace("minecraft:", "").replace("/", "."),
						SDF.format(SDF.parse(data.criteria.entrySet().iterator().next().getValue())));
			else {
				Date maxDate = new Date();
				for (Entry<String, String> entry : data.criteria.entrySet())
					maxDate = (SDF.parse(entry.getValue()).after(maxDate)) ? SDF.parse(entry.getValue()) : maxDate;
				ret.put(title.replace("minecraft:", "").replace("/", "."), SDF.format(maxDate));
			}
		}

		return ret;
	}
	
	static Map<String, Map<String, String>> advancementInfo = new HashMap<>();
	
	static void getAdvancementMappings() throws StreamReadException, DatabindException, IOException {
		
		InputStream advancementsData = new ClassPathResource("static/assets/advancements.json").getInputStream();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Map<String, String> namesMap = objectMapper.readValue(advancementsData, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class));
	
		for (Map.Entry<String, String> line : namesMap.entrySet()) {
			String nameTag = line.getKey().replace("advancements.", "");
			boolean isDesc = nameTag.endsWith(".description");
			nameTag = nameTag.replace(".description", "").replace(".title", "");
			if (advancementInfo.containsKey(nameTag)) {
				if (isDesc)
					advancementInfo.get(nameTag).put("description", line.getValue());
				else
					advancementInfo.get(nameTag).put("title", line.getValue());
			} else
				if (isDesc) {
					Map<String, String> map = new HashMap<>();
					map.put("description", line.getValue());
					advancementInfo.put(nameTag, map);
				} else {
					Map<String, String> map = new HashMap<>();
					map.put("title", line.getValue());
					advancementInfo.put(nameTag, map);
				}
		}
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class AdvancementData {

	@JsonProperty("done")
	boolean done;

	@JsonProperty("criteria")
	Map<String, String> criteria;

}
