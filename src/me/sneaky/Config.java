package me.sneaky;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	private static File dataFolder;

	private static File configFile;
	private static File statsFile;

	private static File baseMapsFile;

	private static FileConfiguration config;
	private static FileConfiguration statsConfig;

	@SuppressWarnings("static-access")
	public Config(Main plugin){
		this.dataFolder = plugin.getDataFolder();

		this.configFile = new File(plugin.getDataFolder(), "config.yml");
		this.statsFile = new File(plugin.getDataFolder(), "stats.yml");

		this.baseMapsFile = new File(plugin.getDataFolder() + File.separator + "maps");

		this.config = YamlConfiguration.loadConfiguration(configFile);
		this.statsConfig = YamlConfiguration.loadConfiguration(this.statsFile);
	}

	public static void createAllFiles(){
		
		if(!(configFile.exists())){
		      try
		      {
		        FileConfiguration config = Main.instance.getConfig();
		        
		        config.save(configFile);
		      }
		      catch (IOException e)
		      {
		        e.printStackTrace();
		      }
		}

		if(!(statsFile.exists())){
			try{statsFile.createNewFile();}
			catch(Exception e){e.printStackTrace();}
		}
	}

	public static FileConfiguration createMapFile(String mapName){
		File mapFile = new File(dataFolder + File.separator + "maps", mapName + ".yml");
		FileConfiguration mapConfig = YamlConfiguration.loadConfiguration(mapFile);

		if(!(mapFile.exists())){
			try{
				mapFile.createNewFile();
				mapConfig.load(mapFile);
			} catch(IOException e){
				e.printStackTrace();
			} catch(InvalidConfigurationException e){
				e.printStackTrace();
			}
		}

		return mapConfig;
	}

	//GETTING FILES
	public static File getConfigFile(){
		return configFile;
	}

	public static File getStatsFile(){
		return statsFile;
	}
	
	public static File getMapsFile(String mapName){
		File mapFile = new File(dataFolder + File.separator + "maps", mapName + ".yml");

		if(mapFile.exists()){
			return mapFile;
		} else {
			return null;
		}
	}

	public static File getBaseMapsFile(){
		return baseMapsFile;
	}

	//ACCESSING FILECONFIGURATIONS
	public static FileConfiguration getConfig(){
		return config;
	}

	public static FileConfiguration getStatsConfig(){
		return statsConfig;
	}

	public static FileConfiguration getFileConfig(File file){
		return YamlConfiguration.loadConfiguration(file);
	}

	//SAVING FILES
	public static void saveConfigFile(){
		saveFile(configFile, config);
	}

	public static void saveStatsFile(){
		saveFile(statsFile, statsConfig);
	}


	public static void saveFile(File file, FileConfiguration config){
		try{
			config.save(file);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void saveAllFiles(){
		saveConfigFile();
		saveStatsFile();
	}
}