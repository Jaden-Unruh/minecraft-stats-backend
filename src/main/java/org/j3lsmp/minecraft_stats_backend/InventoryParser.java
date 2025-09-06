package org.j3lsmp.minecraft_stats_backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.StringTag;
import net.querz.nbt.tag.Tag;

public class InventoryParser {
	
	private final static Path playerDataPath = MinecraftStatsBackendApplication.IS_LOCAL_TEST ? Path.of("G:\\Minecraft Servers\\Azure server\\stats tool testing\\playerdata") : Path.of("/home/jaden/webpage/playerdata");
	
	public static Map<String,List<Map<String,Object>>> getPlayerInventory(String uuid) throws IOException {
		
		uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
		
		File playerFile = playerDataPath.resolve(uuid + ".dat").toFile();
		
		if (!playerFile.exists())
			throw new IllegalArgumentException("Player data file not found for UUID: " + uuid);
		
		NamedTag tag = NBTUtil.read(playerFile);
		
		CompoundTag root = (CompoundTag) tag.getTag();
		
		Map<String,List<Map<String,Object>>> enderAndInv = new HashMap<>();
		
		//Get Inventory
		ListTag<CompoundTag> inventory = (ListTag<CompoundTag>) root.getListTag("Inventory");
		List<Map<String,Object>> inventoryItems = new ArrayList<>();
		getItems(inventory, inventoryItems);
		
		enderAndInv.put("Inventory", inventoryItems);
		
		//Get Ender Chest
		ListTag<CompoundTag> enderChest = (ListTag<CompoundTag>) root.getListTag("EnderItems");
		List<Map<String,Object>> enderItems = new ArrayList<>();
		getItems(enderChest, enderItems);
		
		enderAndInv.put("EnderChest", enderItems);
		
		return enderAndInv;
	}
	
	private static void getItems(ListTag<CompoundTag> inventory, List<Map<String,Object>> outList) {
		if (inventory != null)
			for (CompoundTag item : inventory) {
				Map<String, Object> itemData = new HashMap<>();
				itemData.put("id", item.getString("id").replace("minecraft:", ""));
				itemData.put("count", item.getInt("count"));
				itemData.put("slot", item.getByte("Slot"));
				
				//Get item components data, if applicable
				CompoundTag componentsTag = item.getCompoundTag("components");
				if (componentsTag != null) {
					Map<String,Object> itemComponents = new HashMap<>();
					
					readComponents(componentsTag, itemComponents);
					
					//Shulker contents
					ListTag<CompoundTag> contentsTag = (ListTag<CompoundTag>) componentsTag.getListTag("minecraft:container");
					if (contentsTag != null) {
						List<Map<String, Object>> shulkerItems = new ArrayList<>();
						
						//Yes, we have to write this again - mojang inconsistent with how they organize nbt data of items within shulkers as compared to player inv.
						for (CompoundTag subItem : contentsTag) {
							Map<String, Object> subItemData = new HashMap<>();
							subItemData.put("slot", subItem.getInt("slot"));
							CompoundTag subItemTag = subItem.getCompoundTag("item");
							if (subItemTag != null) {
								subItemData.put("id", subItemTag.getString("id").replace("minecraft:", ""));
								subItemData.put("count", subItemTag.getInt("count"));
								
								CompoundTag subItemComponentsTag = subItemTag.getCompoundTag("components");
								if (subItemComponentsTag != null) {
									Map<String, Object> subItemComponents = new HashMap<>();
									
									readComponents(subItemComponentsTag, subItemComponents);
									
									//don't need to do shulker contents: no shulkers within shulkers
									
									subItemData.put("components", subItemComponents);
								}
							}
							
							shulkerItems.add(subItemData);
						}
						
						itemComponents.put("contents", shulkerItems);
					}
					
					itemData.put("components", itemComponents);
				}
				
				outList.add(itemData);
			}
	}
	
	//Read enchantments, names, trims - used in inv and in shulkers. Shulker contents handled separately.
	private static void readComponents(CompoundTag componentsTag, Map<String, Object> itemComponents) {
		//Enchantments
		CompoundTag enchantmentsTag = componentsTag.getCompoundTag("minecraft:enchantments");
		if (enchantmentsTag != null) {
			ArrayList<Map<String, Object>> enchantments = new ArrayList<>();
			CompoundTag enchantmentLevels = enchantmentsTag.getCompoundTag("levels");
			for (Map.Entry<String, Tag<?>> enchantmentTag : enchantmentLevels) {
				Map<String, Object> enchantment = new HashMap<>();
				enchantment.put("name", enchantmentTag.getKey().replace("minecraft:", ""));
				enchantment.put("level", ((IntTag) enchantmentTag.getValue()).asInt());
				enchantments.add(enchantment);
			}
			itemComponents.put("Enchantments", enchantments);
		}
		
		//Custom names
		StringTag nameTag = componentsTag.getStringTag("minecraft:custom_name");
		if (nameTag != null) {
			itemComponents.put("customName", nameTag.getValue());
		}
		
		//Armor Trim
		CompoundTag trimTag = componentsTag.getCompoundTag("minecraft:trim");
		if (trimTag != null) {
			Map<String, String> trimInfo = new HashMap<>();
			trimInfo.put("material", trimTag.getStringTag("material").getValue().replace("minecraft:", ""));
			trimInfo.put("pattern", trimTag.getStringTag("pattern").getValue().replace("minecraft:", ""));
			itemComponents.put("Trim", trimInfo);
		}
	}
}