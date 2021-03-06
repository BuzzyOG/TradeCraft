package us.corenetwork.tradecraft;

import java.util.HashMap;
import java.util.Random;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import us.corenetwork.tradecraft.commands.BaseCommand;
import us.corenetwork.tradecraft.commands.ReloadCommand;
import us.corenetwork.tradecraft.commands.SaveCommand;
import us.corenetwork.tradecraft.commands.SpawnCommand;

public class TradeCraftPlugin extends JavaPlugin {
	public static TradeCraftPlugin instance;
	
	public static Random random;
	
	public static HashMap<String, BaseCommand> commands = new HashMap<String, BaseCommand>();
	
	@Override
	public void onEnable() {
		instance = this;
		random = new Random();
		
		commands.put("reload", new ReloadCommand());
		commands.put("save", new SaveCommand());
		commands.put("spawn", new SpawnCommand());
		
		getServer().getPluginManager().registerEvents(new TradeCraftListener(), this);
		
		IO.SaveExample();
		IO.LoadSettings();
		IO.PrepareDB();
		
		NMSVillagerManager.register();
		Villagers.LoadVillagers();
	}

	@Override
	public void onDisable() {
		Villagers.SaveVillagers();
		IO.freeConnection();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		BaseCommand cmd = commands.get(args[0]);
		if (cmd != null)
			return cmd.execute(sender, args, true);
		else
			return false;
	}
}
