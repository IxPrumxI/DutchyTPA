package nl.thedutchmc.dutchytpa;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Tpa extends JavaPlugin {

	public boolean costEnabled = false;

	@Override
	public void onEnable() {
		getCommand("tpa").setExecutor(new CommandHandler(this));
		getCommand("tpaccept").setExecutor(new CommandHandler(this));
		getCommand("tpdeny").setExecutor(new CommandHandler(this));
		getCommand("tpyes").setExecutor(new CommandHandler(this));
		getCommand("tpno").setExecutor(new CommandHandler(this));

		loadConfig();
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		this.costEnabled = getConfig().getBoolean("tpa.cost.enabled");

	}

	public int calculateCost(Player player, Location loc) {
		int cost = 0;
		if (!loc.getWorld().getName().equalsIgnoreCase(player.getWorld().getName())) {
			cost += getConfig().getInt("tpa.cost.dimension-change");
		}else if(this.getConfig().getBoolean("tpa.cost.per_block.enabled")){
			cost += ((int) Math.ceil(player.getLocation().distance(loc) / this.getConfig().getInt("tpa.cost.per_block.distance"))) * this.getConfig().getInt("tpa.cost.per_block.cost");
		} else if(this.getConfig().getBoolean("tpa.cost.per_tpa.enabled")) {
			cost += this.getConfig().getInt("tpa.cost.per_tpa.cost");
		}
		return cost;
	}
}
