package org.j3lsmp.minecraft_stats_backend;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@Service
public class DiscordBot extends ListenerAdapter {
	private final StatsService statsService;
	
	@Value("${discord.bot.token}")
	private String discordToken;
	
	public DiscordBot(StatsService statsService) {
		this.statsService = statsService;
	}
	
	@PostConstruct
	public void init() {
		try {
			var jda = JDABuilder.createDefault(discordToken)
			    .addEventListeners(this)
			    .build()
			    .awaitReady();
			
			String guildID = "1310410465867862087";
			jda.getGuildById(guildID).updateCommands()
					.addCommands(
							Commands.slash("stats", "Get a player's stats")
								.addOption(OptionType.STRING, "player", "The player to get stats for", true)
								.addOption(OptionType.STRING, "category", "The stats category to poll - use /help for details", true)
								.addOption(OptionType.STRING, "key", "The specific stat to get - use /help for details", true),
							Commands.slash("help", "Information about using the bot"),
							Commands.slash("leaderboard", "Get a leaderboard for a stat")
									.addOption(OptionType.STRING, "category", "The stats category to poll - use /help for details", true)
									.addOption(OptionType.STRING, "key", "The specific stat to get - use /help for details", true),
							Commands.slash("liststats", "List all custom stats")
									.addOption(OptionType.INTEGER, "page", "The page number of the stats list", false)
						).queue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		try {
			if (!handleCommand(event))
			    event.reply("Unknown command: " + event.getName());
		} catch (Exception e) {
			e.printStackTrace();
			event.reply("Error processing command: " + event.getCommandString()).queue();
		}
	}
	
	boolean handleCommand(SlashCommandInteractionEvent event) {
		if (event.getName().equals("stats")) {
			String player = event.getOption("player").getAsString();
			String category = event.getOption("category").getAsString();
			String key = event.getOption("key").getAsString();
			
			String statResult = statsService.getStat(player, category, key);
			
			if (statResult != null) {
				event.reply(statResult).queue();
				return true;
			} else {
				event.reply("Error getting stats").queue();
				return true;
			}
		}
		else if (event.getName().equals("help")) {
			EmbedBuilder embed = new EmbedBuilder()
					.setTitle("Stats Bot Help")
					.setDescription("This bot can be used to get stats from the Minecraft server. This is a WIP, report any bugs or feature requests to Jaden.")
					.setColor(0xaa00aa)
					.addField("/stats `player` `category` `key`", "Gets a specific stat for a specific player", true)
					.addField("/leaderboard `category` `key`", "Gets a leaderboard for a specific stat", true)
					.addField("/liststats", "Lists all stats in the custom category", true)
					.addField("`player` argument", "The player to get a stat for, properly capitalized", false)
					.addField("`category` argument", "The category of stat to get: broken, picked_up, mined, crafted, used, dropped, killed, killed_by, or custom", false)
					.addField("`key` argument", "The specific stat to get within a category, such as dirt or diamond_sword, or the stat name for custom stats, such as deaths or aviate_one_cm", false)
					.setFooter("Created by Jaden for the J3L+ SMP");
			event.replyEmbeds(embed.build()).setEphemeral(true).queue();
			return true;
		} else if (event.getName().equals("leaderboard")) {
			String category = event.getOption("category").getAsString();
			String key = event.getOption("key").getAsString();
			
			Map<String, PlayerStats> allStats = StatsParser.allPlayerStats;
			ArrayList<LeaderboardEntry> leaderboard = new ArrayList<>();
			for (String uuid : allStats.keySet()) {
				PlayerStats playerStats = allStats.get(uuid);
				if (playerStats.getStats().containsKey("minecraft:" + category))
					if (playerStats.getStats().get("minecraft:" + category).containsKey("minecraft:" + key))
						leaderboard.add(new LeaderboardEntry(MojangAPI.nameCache.get(uuid),
								playerStats.getStats().get("minecraft:" + category).get("minecraft:" + key)));
			}
			
			leaderboard.sort((a, b) -> b.value() - a.value());
			
			EmbedBuilder embed = new EmbedBuilder()
					.setTitle("Leaderboard for " + category + " " + key)
					.setColor(0xffaa00)
					.setDescription("Top players for this stat.")
					.setFooter("Bot created by Jaden for the J3L+ SMP");
			
			for (int i = 0; i < Math.min(10, leaderboard.size()); i++) {
				LeaderboardEntry entry = leaderboard.get(i);
				embed.addField(entry.name(), Integer.toString(entry.value()), false);
			}
			
			event.replyEmbeds(embed.build()).queue();
			return true;
		} else if (event.getName().equals("liststats")) {
			System.out.println("Listing stats");
			long page = event.getOption("page") != null ? event.getOption("page").getAsLong() : 1;
			
			Map<String, PlayerStats> allStats = StatsParser.allPlayerStats;
			ArrayList<String> statsNames = new ArrayList<>();
			for (PlayerStats playerStats : allStats.values())
				for (String statKey : playerStats.getStats().get("minecraft:custom").keySet())
					if (!statsNames.contains(statKey.replace("minecraft:", "")))
						statsNames.add(statKey.replace("minecraft:", ""));

			EmbedBuilder embed = new EmbedBuilder().setTitle("Custom Stats List").setColor(0x00aa00)
					.setDescription("Page " + page + " of " + (int)(Math.ceil(statsNames.size() / 25.0)))
					.setFooter("Use /liststats `page` to see more custom stats")
					.setColor(0xffaa00);
			
			statsNames.sort(String::compareTo);
			
			for (int i = (int) (0 + (page - 1) * 25); i < Math.min(page * 25, statsNames.size()); i++) {
				String stat = statsNames.get(i);
				embed.addField("", stat, true);
			}

			event.replyEmbeds(embed.build()).queue();
			return true;
		}
		return false;
	}
}

record LeaderboardEntry(String name, int value) {}