package me.sneaky;

import java.util.ArrayList;
import java.util.logging.Logger;

import me.sneaky.anticheat.aAutosoup;
import me.sneaky.anticheat.aMacro;
import me.sneaky.anticheat.aUtils;
import me.sneaky.cmds.Help;
import me.sneaky.cmds.Ping;
import me.sneaky.cmds.Spectate;
import me.sneaky.cmds.staff.Admin;
import me.sneaky.cmds.staff.Ban;
import me.sneaky.cmds.staff.Block;
import me.sneaky.cmds.staff.Broadcast;
import me.sneaky.cmds.staff.ClearChat;
import me.sneaky.cmds.staff.DuelPos;
import me.sneaky.cmds.staff.FreeKitDay;
import me.sneaky.cmds.staff.IP;
import me.sneaky.cmds.staff.Kick;
import me.sneaky.cmds.staff.MuteChat;
import me.sneaky.cmds.staff.Op;
import me.sneaky.cmds.staff.build;
import me.sneaky.cmds.staff.cAdd;
import me.sneaky.events.Events;
import me.sneaky.events.anvil.ListenerAnvil;
import me.sneaky.events.anvil.UtilsAnvil;
import me.sneaky.events.brackets.Brackets;
import me.sneaky.events.chickenwars.ListenerChickenWars;
import me.sneaky.events.chickenwars.UtilsChickenWars;
import me.sneaky.events.competitive.GuiCompetitive;
import me.sneaky.events.competitive.ListenerCompetitve;
import me.sneaky.events.competitive.TimerCompetitive;
import me.sneaky.events.competitive.UtilsCompetitve;
import me.sneaky.events.custom.TimeSecondLoop;
import me.sneaky.events.deathrace.ListenerDeathRace;
import me.sneaky.events.deathrace.UtilsDeathRace;
import me.sneaky.events.hg.ListenerHG;
import me.sneaky.events.hg.UtilsHG;
import me.sneaky.events.oitc.ListenerOITC;
import me.sneaky.events.oitc.UtilsOITC;
import me.sneaky.events.sg.ListenerSG;
import me.sneaky.events.sg.UtilsSG;
import me.sneaky.events.tntrun.ListenerTNTRun;
import me.sneaky.events.tntrun.UtilsTNTRun;
import me.sneaky.feast.feastCMD;
import me.sneaky.feast.feastListener;
import me.sneaky.kits.KitGUI;
import me.sneaky.kits.Kits;
import me.sneaky.kits.Kits.sKits;
import me.sneaky.kits.KitsListener;
import me.sneaky.kits.gladiator.GladiatorListener;
import me.sneaky.kits.satan.SatanListener;
import me.sneaky.listeners.CannonListener;
import me.sneaky.listeners.PlayerListener;
import me.sneaky.listeners.SignListener;
import me.sneaky.listeners.diaBlockListener;
import me.sneaky.mysql.MySqlManager;
import me.sneaky.onevsone.CMD1v1;
import me.sneaky.onevsone.Listener1v1;
import me.sneaky.spawnprotection.sProtectionCMD;
import me.sneaky.spawnprotection.sProtectionListener;
import me.sneaky.stats.Stats;
import me.sneaky.stats.StatsUtils;
import me.sneaky.stats.killstreaks.Killstreak;
import me.sneaky.utils.ServerHighRam;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin implements Listener{
	
	public static Main instance;
	
	public static int timeSecondLoop = 0;
	
	public Utils util;
	public Chat chat;
	public Kits KitsClass;
	public StatsUtils stats;
	public LocationUtils locUtil;
	public aUtils anticheat;
	public GuiCompetitive CompGUI;

	  Logger log;
	  
	  ArrayList<sKits> kits = new ArrayList<sKits>();

	public Main() {
		instance = this;
		this.anticheat = new aUtils(this);
		this.util = new Utils(this);
		this.chat = new Chat(this);
		this.KitsClass = new Kits(this);
		this.stats = new StatsUtils(this);
		this.locUtil = new LocationUtils(this);
		this.CompGUI = new GuiCompetitive();
	}
	
	public void onEnable() {
		Config.createAllFiles();
		this.anticheat.checkHax();
		SetupCMD();
		Setup();
		timeSecondLoop = getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimeSecondLoop(), 1l, 20l);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new ServerHighRam(), 0, 20 * 60l);
		for(sKits kit : sKits.values()){
			kits.add(kit);
		}
		//getServer().getScheduler().scheduleSyncRepeatingTask(this, new ScoreBoard(this), 0l, 20l);
		feastListener.repeatFeast(this);
		for(Material mat : Material.values()){
			if(!this.util.allMaterials.contains(mat)){
			this.util.allMaterials.add(mat);
			}
		}
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimerCompetitive(), 0l, 20l);
		
	} 
	
	public void onDisable() {
        HandlerList.unregisterAll((Listener) this);
		
		UtilsAnvil anvil = new UtilsAnvil();
		UtilsHG hg = new UtilsHG();
		UtilsTNTRun tntrun = new UtilsTNTRun();
		UtilsChickenWars cwars = new UtilsChickenWars();
		UtilsOITC oitc = new UtilsOITC();
		UtilsDeathRace drace = new UtilsDeathRace();
		UtilsCompetitve comp = new UtilsCompetitve();
		
		
		anvil.stopGame();
		hg.stopGame();
		tntrun.stopGame();
		cwars.stopGame();
		oitc.stopGame();
		drace.stopGame();
		comp.stopGame();
	}

	public void SetupCMD(){
		getCommand("brackets").setExecutor(new Brackets(this));
		getCommand("help").setExecutor(new Help(this));
		getCommand("ping").setExecutor(new Ping(this));
		getCommand("admin").setExecutor(new Admin(this));
		getCommand("ban").setExecutor(new Ban(this));
		getCommand("build").setExecutor(new build(this));
		getCommand("cadd").setExecutor(new cAdd(this));
		getCommand("broadcast").setExecutor(new Broadcast(this));
		getCommand("clearchat").setExecutor(new ClearChat(this));
		getCommand("ip").setExecutor(new IP(this));
		getCommand("kick").setExecutor(new Kick(this));
		getCommand("mutechat").setExecutor(new MuteChat(this));
		getCommand("op").setExecutor(new Op(this));
		getCommand("stats").setExecutor(new Stats(this));
		getCommand("1v1").setExecutor(new CMD1v1(this));
		getCommand("sprot").setExecutor(new sProtectionCMD(this));
		getCommand("feast").setExecutor(new feastCMD(this));
		getCommand("block").setExecutor(new Block(this));
		
		getCommand("hg").setExecutor(new UtilsHG());
		getCommand("cwars").setExecutor(new UtilsChickenWars());
		getCommand("tntrun").setExecutor(new UtilsTNTRun());
		getCommand("anvil").setExecutor(new UtilsAnvil());
		getCommand("oitc").setExecutor(new UtilsOITC());
		getCommand("drace").setExecutor(new UtilsDeathRace());
		getCommand("sg").setExecutor(new UtilsSG());
		getCommand("competitive").setExecutor(new UtilsCompetitve());
		
		getCommand("duelpos").setExecutor(new DuelPos(this));
		getCommand("spectate").setExecutor(new Spectate());
		
		getCommand("freekitday").setExecutor(new FreeKitDay(this));
			
	}
	
	public void Setup(){ 
		
		getServer().getPluginManager().registerEvents(new aMacro(this), this);
		getServer().getPluginManager().registerEvents(new aAutosoup(this), this);
		
		
		getServer().getPluginManager().registerEvents(new sProtectionListener(this), this);

		getServer().getPluginManager().registerEvents(new KitsListener(this), this);
		getServer().getPluginManager().registerEvents(new GladiatorListener(this), this);
		getServer().getPluginManager().registerEvents(new SatanListener(this), this);
		
		getServer().getPluginManager().registerEvents(new Listener1v1(this), this);
		
		getServer().getPluginManager().registerEvents(new ListenerHG(this), this);
		getServer().getPluginManager().registerEvents(new ListenerChickenWars(this), this);
		getServer().getPluginManager().registerEvents(new ListenerTNTRun(this), this);
		getServer().getPluginManager().registerEvents(new ListenerAnvil(this), this);
		getServer().getPluginManager().registerEvents(new ListenerOITC(this), this);
		getServer().getPluginManager().registerEvents(new ListenerDeathRace(this), this);
		getServer().getPluginManager().registerEvents(new ListenerSG(this), this);
		getServer().getPluginManager().registerEvents(new ListenerCompetitve(this), this);
		
		getServer().getPluginManager().registerEvents(new Events(), this);
		
		getServer().getPluginManager().registerEvents(new Spectate(), this);
		
		getServer().getPluginManager().registerEvents(new Stats(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new diaBlockListener(this), this);
		getServer().getPluginManager().registerEvents(new KitGUI(this), this);
		
		getServer().getPluginManager().registerEvents(new MuteChat(this), this);
		
		getServer().getPluginManager().registerEvents(new CannonListener(), this);
		
		getServer().getPluginManager().registerEvents(new SignListener(), this);
		
		getServer().getPluginManager().registerEvents(new Killstreak(), this);
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
	    final Player player = (Player)sender;
	    if(cmd.getName().equalsIgnoreCase("kit")){
	    	if(args.length == 1){
	    	if(player.getName().equalsIgnoreCase("sneakylegend") || player.getName().equalsIgnoreCase("mrjayharm")){
	    for(sKits kit : sKits.values()){
	    	String kitName = kit.toString().toLowerCase();
	    	if(args[0].equalsIgnoreCase(kitName)){
	    		KitsClass.giveKit(player, kit);
	    	}
	    }
	    }
	    }
	    }
		return false;
	}
	

}
