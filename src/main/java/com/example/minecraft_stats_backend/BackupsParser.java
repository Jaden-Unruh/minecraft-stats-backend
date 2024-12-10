package com.example.minecraft_stats_backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BackupsParser {
	
	static ArrayList<Backup> backups = new ArrayList<>();
	
	final static Pattern BACKUP_LINE_PATTERN = Pattern.compile("^(.+),(.+),(.+),(.+)$");
	
	static void parseBackups() {
		
		String backupsName = "G:\\Minecraft Servers\\Azure server\\stats tool testing\\backups.txt";
		File backupsFile = new File(backupsName);
		
		try (BufferedReader reader = new BufferedReader(new FileReader(backupsFile))) {
			String[] lines = reader.lines().toArray(String[]::new);
			
			for (String line : lines) {
				Matcher match = BACKUP_LINE_PATTERN.matcher(line);
				if (match.find())
					backups.add(new Backup(match.group(1), match.group(2), match.group(3), match.group(4)));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static JsonNode getBackups() {
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode rootNode = mapper.createArrayNode();
		for (Backup backup : backups) {
			ObjectNode child = mapper.createObjectNode();
			child.put("Date", backup.date);
			child.put("Time", backup.time);
			child.put("Size", backup.size);
			child.put("Link", backup.address);
			
			rootNode.add(child);
		}
		
		return rootNode;
	}
}

class Backup  {
	String date, time, size, address;
	
	Backup(String date, String time, String size, String address) {
		this.date = date;
		this.time = time;
		this.size = size;
		this.address = address;
	}
}
