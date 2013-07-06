package me.TheTioLP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class TDM extends JavaPlugin implements Listener {
public static List<String> Spezialeinheitwaffen = new ArrayList<String>();
public static List<String> Scharfschützewaffen = new ArrayList<String>();
public static List<String> Sprengmeisterwaffen = new ArrayList<String>();
public static List<String> Domemap = new ArrayList<String>();
public static List<String> Domeplayers = new ArrayList<String>();
public static List<String> Killhousemap = new ArrayList<String>();
public static List<String> Killhouseplayers = new ArrayList<String>();
public static List<String> Teleport = new ArrayList<String>();
protected static FileConfiguration config;
public static boolean DomeTeam;	
public String TDMTag = "TDM";
public String noPerm = "Keine Rechte!";
	
@Override
public void onEnable() {
loadConfig();
config = getConfig();
Bukkit.getPluginManager().registerEvents(this, this);
domeupdate();
//Spezialeinheitwaffen
Spezialeinheitwaffen.add("§6Hauptwaffe: MP5");
Spezialeinheitwaffen.add("§6Pistole: USP.45");
Spezialeinheitwaffen.add("§6Extras & Inventar");
Spezialeinheitwaffen.add("§b3x Splittergranaten");
Spezialeinheitwaffen.add("§7Granate mit Explosion.");
Spezialeinheitwaffen.add("§cÜberschallknall");
Spezialeinheitwaffen.add("§7Höherer Explosivwaffenschaden.");
Spezialeinheitwaffen.add("§2Tiefenwirkung");
Spezialeinheitwaffen.add("§7Erhörte Patronendurchschlagskraft.");
Spezialeinheitwaffen.add("§83x Splitter/1x Blendgranate");
//Ende Spezialeinheitwaffen
//Scharfschützewaffen
Scharfschützewaffen.add("§6Hauptwaffe: M40A3");
Scharfschützewaffen.add("§6Pistole: M9");
Scharfschützewaffen.add("§6Extras & Inventar");
Scharfschützewaffen.add("§b3x Blendgranaten");
Scharfschützewaffen.add("§7Granate mit Blendung.");
Scharfschützewaffen.add("§cSpezialfeuerkraft");
Scharfschützewaffen.add("§7Erhögter Pratonenschaden.");
Scharfschützewaffen.add("§2Tiefenwirkung");
Scharfschützewaffen.add("§7Erhörte Patronendurchschlagskraft.");
Scharfschützewaffen.add("§81x Splitter/3x Blendgranate");
//Ende Scharfschützewaffen
//Sprengmeisterwaffen
Sprengmeisterwaffen.add("§6Hauptwaffe: W1200");
Sprengmeisterwaffen.add("§6Pistole: M9");
Sprengmeisterwaffen.add("§6Extras & Inventar");
Sprengmeisterwaffen.add("§b2x Raketenwerfer");
Sprengmeisterwaffen.add("§7Raketenwerfer mit 2 Raketen.");
Sprengmeisterwaffen.add("§cÜberschallknall");
Sprengmeisterwaffen.add("§7Höherer Explosivwaffenschaden.");
Sprengmeisterwaffen.add("§2Extrembedingungen");
Sprengmeisterwaffen.add("§7Schnellere Sprints.");
Sprengmeisterwaffen.add("§81x Splitter/1x Rauchgranate");
//Ende Sprengmeisterwaffen
}
@Override
public void onDisable() {
	
}
@Override
public boolean onCommand(CommandSender sender, Command cmd,String cmdlabel, String[] args) {
final Player p = (Player)sender;	
if(sender instanceof Player){
	//FFA
if(cmdlabel.equalsIgnoreCase("TDM")){ //Anfang FFA Command
	if(p.hasPermission("TDM.TDM")){
		if(args.length == 0){
			p.sendMessage(TDMTag);
			p.sendMessage("§cBefehle:");
			p.sendMessage("§a/TDM join");
			p.sendMessage("§a/TDM leave");
			return true;}
		    if(args.length == 1){
		    	if (args[0].equalsIgnoreCase("setjoin")) {
	                if (p.hasPermission("TDM.setjoin")) {
		    			Location location = p.getLocation();
	    				config.set("possition.join.x", Double.valueOf(location.getX()));
	    				config.set("possition.join.y", Double.valueOf(location.getY()));
	    				config.set("possition.join.z", Double.valueOf(location.getZ()));
	    				config.set("possition.join.yaw", Float.valueOf(location.getYaw()));
	    				config.set("possition.join.pitch", Float.valueOf(location.getPitch()));
	    				config.set("possition.join.world", location.getWorld().getName());
	    				saveConfig();
	    				p.sendMessage(ChatColor.GREEN + "Der Join-Punkt wurde erfolgreich gesetzt.");
		    		return true;}
	        p.sendMessage(noPerm);
		    	return true;}
	            if (args[0].equalsIgnoreCase("setleave")) {
	        	    if (p.hasPermission("TDM.setleave")) {
	        			Location location = p.getLocation();
	        			config.set("possition.leave.x", Double.valueOf(location.getX()));
	        			config.set("possition.leave.y", Double.valueOf(location.getY()));
	        			config.set("possition.leave.z", Double.valueOf(location.getZ()));
	        			config.set("possition.leave.yaw", Float.valueOf(location.getYaw()));
	        			config.set("possition.leave.pitch", Float.valueOf(location.getPitch()));
	        			config.set("possition.leave.world", location.getWorld().getName());
	        			saveConfig();
	        			p.sendMessage(ChatColor.GREEN + "Der Leave-Punkt wurde erfolgreich gesetzt.");
	        	    return true;}
	        p.sendMessage(noPerm);
	            return true;}
		    	if (args[0].equalsIgnoreCase("join")) {
		            if (p.hasPermission("TDM.join")) {
		              if (!isArmor(p)) {
		                World world = Bukkit.getServer().getWorld(config.getString("possition.join.world"));
		                if (world != null) {
		                  Location location = new Location(world, config.getDouble("possition.join.x"), config.getDouble("possition.join.y"), config.getDouble("possition.join.z"));
		                  p.teleport(location);
		                  p.getLocation().setPitch(config.getInt("possition.join.pitch"));
		                  p.getLocation().setYaw(config.getInt("possition.join.yaw"));
		                  ItemStack Dome  = new ItemStack(Material.PAPER);
		                  ItemMeta Domeinfo = Dome.getItemMeta();
		                  Domeinfo.setLore(Domemap);
		                  Domeinfo.setDisplayName("§9Dome");
		                  Dome.setItemMeta(Domeinfo);
		                  p.getInventory().addItem(new ItemStack(Dome));
		                  p.sendMessage(ChatColor.GREEN + "Du bist nun in der Lobby!");  
		                return true;}
		                p.sendMessage(ChatColor.DARK_RED + "Es ist ein Fehler aufgetreten bitte kontaktieren Sie einen Admin.");
		               return true;}
		              p.sendMessage(ChatColor.DARK_GREEN + "Bitte leere dein Inventar!");
		            return true;}
	        p.sendMessage(noPerm);
		        return true;}
		        if (args[0].equalsIgnoreCase("leave")) {
			        if (p.hasPermission("TDM.leave")) {
			        	p.sendMessage(TDMTag + "§6Du kannst dich nun 3 Sekunden nicht bewegen!");
			        	Teleport.add(p.getName());
			        	
			  			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			  	    		public void run() {
					            World world = Bukkit.getServer().getWorld(config.getString("possition.leave.world"));
					            if (world != null) {
					              delInv(p);
					              Location location = new Location(world, config.getDouble("possition.leave.x"), config.getDouble("possition.leave.y"), config.getDouble("possition.leave.z"));
					              p.teleport(location);
					              p.getLocation().setPitch(config.getInt("possition.leave.pitch"));
					              p.getLocation().setYaw(config.getInt("possition.leave.yaw"));
					              getServer().broadcastMessage(TDMTag + ChatColor.RED + p.getName() + ChatColor.AQUA + " ist aus dem TDM gegangen.");
					              p.sendMessage(ChatColor.GREEN + "Du hast die Arena verlassen!");
					              if (Domeplayers.contains(p.getName())) {
					        	        Domeplayers.remove(p.getName());
					              }
					              if (Killhouseplayers.contains(p.getName())) {
					        	        Killhouseplayers.remove(p.getName());
					              }
					              if (Teleport.contains(p.getName())) {
					            	  Teleport.remove(p.getName());
					              }
					            } else {
					            	p.sendMessage(ChatColor.DARK_RED + "Es ist ein Fehler aufgetreten bitte kontaktieren Sie einen Admin.");
					            }
			  	    		}
			  	    		}, 60L);
			        return true;}
	        p.sendMessage(noPerm);
	            return true;}
	        return true;}	        
		        if(args.length == 2){
		        	if (args[0].equalsIgnoreCase("setarena")) {
		    			if (args[1].equalsIgnoreCase("3")) {
		    				if (p.hasPermission("TDM.setarena")) {
		    					Location location = p.getLocation();
		    					config.set("possition.arena3.x", Double.valueOf(location.getX()));
		    					config.set("possition.arena3.y", Double.valueOf(location.getY()));
		    					config.set("possition.arena3.z", Double.valueOf(location.getZ()));
		    					config.set("possition.arena3.yaw", Float.valueOf(location.getYaw()));
		    					config.set("possition.arena3.pitch", Float.valueOf(location.getPitch()));
		    					config.set("possition.arena3.world", location.getWorld().getName());
		    					saveConfig();
		    					p.sendMessage(ChatColor.GREEN + "Die Arena-3 wurde erfolgreich gesetzt.");
		    		        return true;}
	        p.sendMessage(noPerm);
		                return true;}
		    			if (args[1].equalsIgnoreCase("2")) {
		    				if (p.hasPermission("TDM.setarena")) {
		    					Location location = p.getLocation();
		    					config.set("possition.arena2.x", Double.valueOf(location.getX()));
		    					config.set("possition.arena2.y", Double.valueOf(location.getY()));
		    					config.set("possition.arena2.z", Double.valueOf(location.getZ()));
		    					config.set("possition.arena2.yaw", Float.valueOf(location.getYaw()));
		    					config.set("possition.arena2.pitch", Float.valueOf(location.getPitch()));
		    					config.set("possition.arena2.world", location.getWorld().getName());
		    					saveConfig();
		    					p.sendMessage(ChatColor.GREEN + "Die Arena-2 wurde erfolgreich gesetzt.");
		    		        return true;}
	        p.sendMessage(noPerm);
		                return true;}
		    			if (args[1].equalsIgnoreCase("1")) {
		    				if (p.hasPermission("TDM.setarena")) {
		    					Location location = p.getLocation();
		    					config.set("possition.arena1.x", Double.valueOf(location.getX()));
		    					config.set("possition.arena1.y", Double.valueOf(location.getY()));
		    					config.set("possition.arena1.z", Double.valueOf(location.getZ()));
		    					config.set("possition.arena1.yaw", Float.valueOf(location.getYaw()));
		    					config.set("possition.arena1.pitch", Float.valueOf(location.getPitch()));
		    					config.set("possition.arena1.world", location.getWorld().getName());
		    					saveConfig();
		    					p.sendMessage(ChatColor.GREEN + "Die Arena-1 wurde erfolgreich gesetzt.");
		    				}
		    			}
		    			if(args.length == 3){
		    					if(args[0].equalsIgnoreCase("Rot")){
		    						Location location = p.getLocation();
			    				config.set("possition.arena1.Rot.x", Double.valueOf(location.getX()));
			    				config.set("possition.arena1.Rot.y", Double.valueOf(location.getY()));
			    				config.set("possition.arena1.Rot.z", Double.valueOf(location.getZ()));
			    				config.set("possition.arena1.Rot.yaw", Float.valueOf(location.getYaw()));
			    				config.set("possition.arena1.Rot.pitch", Float.valueOf(location.getPitch()));
			    				config.set("possition.arena1.Rot.world", location.getWorld().getName());
			    				saveConfig();	
		    					}
		    					if(args[0].equalsIgnoreCase("Blau")){
		    						Location location = p.getLocation();
			    				config.set("possition.arena1.Blau.x", Double.valueOf(location.getX()));
			    				config.set("possition.arena1.Blau.y", Double.valueOf(location.getY()));
			    				config.set("possition.arena1.Blau.z", Double.valueOf(location.getZ()));
			    				config.set("possition.arena1.Blau.yaw", Float.valueOf(location.getYaw()));
			    				config.set("possition.arena1.Blau.pitch", Float.valueOf(location.getPitch()));
			    				config.set("possition.arena1.Blau.world", location.getWorld().getName());
			    				saveConfig();	
		    					}
		    			}
		        	return true;}
		        return true;}
	return true;}
	p.sendMessage(noPerm);
	return true;}
if(cmdlabel.equalsIgnoreCase("Items")){
if(args.length == 0){
p.sendMessage("/TDM join");	
}
if(args.length > 1){	
}
if(args.length == 1){
if(args[0].equalsIgnoreCase("alle")){
playerremove(p);
ItemStack Dome  = new ItemStack(Material.PAPER);
ItemMeta Domeinfo = Dome.getItemMeta();
Domeinfo.setLore(Domemap);
Domeinfo.setDisplayName("§9Dome");
Dome.setItemMeta(Domeinfo);
p.getInventory().addItem(new ItemStack(Dome));
ItemStack Spezialeinheit  = new ItemStack(Material.PAPER);
ItemMeta Spezialeinheitinfo = Spezialeinheit.getItemMeta();
Spezialeinheitinfo.setLore(Spezialeinheitwaffen);
Spezialeinheitinfo.setDisplayName("§bSpezialeinheit");
Spezialeinheit.setItemMeta(Spezialeinheitinfo);
p.getInventory().addItem(new ItemStack(Spezialeinheit));
ItemStack MP5 = new ItemStack(Material.DIAMOND_AXE);
ItemMeta MP5Meta = MP5.getItemMeta();
MP5Meta.setDisplayName("§8MP5");
MP5.setItemMeta(MP5Meta);
p.getInventory().addItem(new ItemStack(MP5));
ItemStack M40A3 = new ItemStack(Material.BOW);
ItemMeta M40A3Meta = M40A3.getItemMeta();
M40A3Meta.setDisplayName("§8M40A3");
M40A3.setItemMeta(M40A3Meta);
p.getInventory().addItem(new ItemStack(M40A3));
ItemStack Rauchgranate = new ItemStack(Material.SNOW_BALL);
ItemMeta RauchgranateMeta = Rauchgranate.getItemMeta();
RauchgranateMeta.setDisplayName("§0Rauchgranate");
Rauchgranate.setItemMeta(RauchgranateMeta);
p.getInventory().addItem(new ItemStack(Rauchgranate));
ItemStack Splitter = new ItemStack(Material.SNOW_BALL);
ItemMeta SplitterMeta = Splitter.getItemMeta();
SplitterMeta.setDisplayName("§0Splittergranate");
Splitter.setItemMeta(SplitterMeta);
p.getInventory().addItem(new ItemStack(Splitter));
ItemStack SSplitter = new ItemStack(Material.SNOW_BALL);
ItemMeta SSplitterMeta = SSplitter.getItemMeta();
SSplitterMeta.setDisplayName("§0Starke Splittergranate");
SSplitter.setItemMeta(SSplitterMeta);
p.getInventory().addItem(new ItemStack(SSplitter));
}
}	
return true;}
	
	
	
	
} else {
sender.sendMessage("Das sind Spieler Commands");	
}

return true;}//Ende onCommand
//Config
public void loadConfig() {
	FileConfiguration cfg = this.getConfig();
    cfg.options().copyDefaults(true);
	this.saveConfig();}
//Ende Config

//Listener
@EventHandler
public void onPlayerMove(PlayerMoveEvent e){
Player p = e.getPlayer();
if(p.isSprinting()){
	p.setWalkSpeed((float)0.9);
}
}
@EventHandler
public void onPlayerClick(PlayerInteractEvent e){
Player p = e.getPlayer();
Player player = e.getPlayer();
if((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)){
if((p.getInventory().contains(Material.DIAMOND_AXE)) || (p.getInventory().contains(Material.PAPER)) || (p.getInventory().contains(Material.BOOK)) || (p.getInventory().contains(Material.BOW))){
	
}
}
if((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)){
if((p.getInventory().contains(Material.DIAMOND_AXE)) || (p.getInventory().contains(Material.PAPER)) || (p.getInventory().contains(Material.BOOK)) || (p.getInventory().contains(Material.BOW))){
if (("§9Dome").equals(p.getItemInHand().getItemMeta().getDisplayName())){
	World world = Bukkit.getServer().getWorld(config.getString("possition.arena1.world"));
    if (world != null) {
      delInv(p);
      Location location = new Location(world, config.getDouble("possition.arena1.x"), config.getDouble("possition.arena1.y"), config.getDouble("possition.arena1.z"));
      p.teleport(location);
      p.getLocation().setPitch(config.getInt("possition.arena1.pitch"));
      p.getLocation().setYaw(config.getInt("possition.arena1.yaw"));
      p.sendMessage(TDMTag + " §aDu bist nun in der Arena 1");
      ItemStack Spezialeinheit  = new ItemStack(Material.BOOK);
      ItemMeta Spezialeinheitinfo = Spezialeinheit.getItemMeta();
      Spezialeinheitinfo.setLore(Spezialeinheitwaffen);
      Spezialeinheitinfo.setDisplayName("§bSpezialeinheit");
      Spezialeinheit.setItemMeta(Spezialeinheitinfo);
      ItemStack Klasse  = new ItemStack(Material.PAPER);
      ItemMeta Klassen = Klasse.getItemMeta();
      Klassen.setDisplayName("§a§lKlassen -->");
      Klasse.setItemMeta(Klassen);
      p.getInventory().addItem(new ItemStack(Klasse));
      p.getInventory().addItem(new ItemStack(Spezialeinheit));
      p.updateInventory();
    }
} else if (("§bSpezialeinheit").equals(p.getItemInHand().getItemMeta().getDisplayName())){
	if(DomeTeam){
	    	World world = Bukkit.getServer().getWorld(config.getString("possition.arena1.Rot.world"));
	        if (world != null) {
	        delInv(p);
	        Location locationrot = new Location(world, config.getDouble("possition.arena1.Rot.x"), config.getDouble("possition.arena1.Rot.y"), config.getDouble("possition.arena1.Rot.z"));
	        p.teleport(locationrot);
	        p.getLocation().setPitch(config.getInt("possition.arena1.Rot.pitch"));
	        p.getLocation().setYaw(config.getInt("possition.arena1.Rot.yaw"));
	        p.sendMessage(TDMTag + " §cDu bist nun in Team Rot!");
	        } else {
	        p.sendMessage(ChatColor.DARK_RED + "Es ist ein Fehler aufgetreten bitte kontaktieren Sie einen Admin.");
	        }
	        DomeTeam = false;
	} else {
	    	World worldblau = Bukkit.getServer().getWorld(config.getString("possition.arena1.Blau.world"));
	        if (worldblau != null) {
	        delInv(p);
	        Location locationblau = new Location(worldblau, config.getDouble("possition.arena1.Blau.x"), config.getDouble("possition.arena1.Blau.y"), config.getDouble("possition.arena1.Blau.z"));
	        p.teleport(locationblau);
	        p.getLocation().setPitch(config.getInt("possition.arena1.Blau.pitch"));
	        p.getLocation().setYaw(config.getInt("possition.arena1.Blau.yaw"));
	        p.sendMessage(TDMTag + " §bDu bist nun in Team Blau!");
	        } else {
	        p.sendMessage(ChatColor.DARK_RED + "Es ist ein Fehler aufgetreten bitte kontaktieren Sie einen Admin.");
	        }
	        DomeTeam = true;
	}
} else if (("§8M9").equals(p.getItemInHand().getItemMeta().getDisplayName())){
	if(p.getInventory().contains(Material.SULPHUR)){
		Arrow aro = (Arrow)p.launchProjectile(Arrow.class);
		   aro.setShooter(p);    
		   double powerSetting = 1.0D;
		   powerSetting = ((Double)1.0D);
		   aro.setVelocity(aro.getVelocity().multiply(powerSetting));
		   int fStack = p.getInventory().first(Material.SULPHUR);
		   ItemStack its = player.getInventory().getItem(fStack);
		   aro.setMetadata("infi_arrow", new FixedMetadataValue(this, Boolean.valueOf(true)));
		   if (its.getAmount() > 1){
		   its.setAmount(its.getAmount() -1);
		   p.updateInventory();
		   } else if (its.getAmount() <= 1) {
		   player.getInventory().clear(fStack);
		   p.updateInventory();
		   }
		}
	} else if (("§8MP5").equals(p.getItemInHand().getItemMeta().getDisplayName())){
	if(p.getInventory().contains(Material.ARROW)){
	   Arrow aro = (Arrow)p.launchProjectile(Arrow.class);
	   aro.setShooter(p);    
	   double powerSetting = 1.0D;
	   powerSetting = ((Double)2.0D);
	   aro.setVelocity(aro.getVelocity().multiply(powerSetting));
	   int fStack = p.getInventory().first(Material.ARROW);
	   ItemStack its = player.getInventory().getItem(fStack);
	   aro.setMetadata("infi_arrow", new FixedMetadataValue(this, Boolean.valueOf(true)));
	   if (its.getAmount() > 1){
	   its.setAmount(its.getAmount() -1);
	   p.updateInventory();
	   } else if (its.getAmount() <= 1) {
	   player.getInventory().clear(fStack);
	   p.updateInventory();
	   }
	}
}
}
} else {
return;
}
}
@EventHandler
public void a(PlayerPickupItemEvent e)
{
  if (e.getItem().hasMetadata("infi_arrow"))
    e.setCancelled(true);
  if (e.getItem().hasMetadata("sniper"))
	    e.setCancelled(true);
}
@EventHandler
public void onPlayerShoot(EntityShootBowEvent e){
Player p = (Player)e.getEntity();
if (("§8M40A3").equals(p.getItemInHand().getItemMeta().getDisplayName())){
	if(p.getInventory().contains(Material.ARROW)){
		e.getProjectile().remove();
		Arrow aro = (Arrow)p.launchProjectile(Arrow.class);
		double powerSetting = 1.0D;
		   powerSetting = ((Double)3.0D);
		   aro.setVelocity(aro.getVelocity().multiply(powerSetting));
		   aro.setShooter(p);    
		   aro.setMetadata("sniper_aro", new FixedMetadataValue(this, Boolean.valueOf(true)));
		}
	}

}
@EventHandler
public void onPfeilHit(ProjectileHitEvent e){
Entity p = (Entity)e.getEntity().getShooter();
if(((Player) p).getInventory().contains(Material.SNOW_BALL)){
	if (("§0Starke Splittergranate").equals(((Player) p).getItemInHand().getItemMeta().getDisplayName())){
	Projectile projectile = e.getEntity();
	double X = projectile.getLocation().getX();
	double Y = projectile.getLocation().getY();
	double Z = projectile.getLocation().getZ();
	projectile.getWorld().createExplosion(X, Y, Z, 4);
	} else	if (("§0Splittergranate").equals(((Player) p).getItemInHand().getItemMeta().getDisplayName())){
	Projectile projectile = e.getEntity();
	double X = projectile.getLocation().getX();
	double Y = projectile.getLocation().getY();
	double Z = projectile.getLocation().getZ();
	projectile.getWorld().createExplosion(X, Y, Z, 2);
	} else if (("§0Rauchgranate").equals(((Player) p).getItemInHand().getItemMeta().getDisplayName())){
    Projectile projectile = e.getEntity();
	Location loc = projectile.getLocation();
    final World world = loc.getWorld();
    for (int x = loc.getBlockX() - 5; x < loc.getBlockX() + 5; x++) {
    	for (int y = loc.getBlockY() - 5; y < loc.getBlockY() + 5; y++) { 
    		for (int z = loc.getBlockZ() - 5; z < loc.getBlockZ() + 5; z++) {	
    	          Block block = world.getBlockAt(x, y, z);
    	          if (block.getType() == Material.AIR)
    	          world.playEffect(world.getBlockAt(x, y, z).getLocation(), Effect.SMOKE, BlockFace.UP);
}
}
}
}
}
    		
}
@EventHandler
public void a(EntityDamageByEntityEvent e)
{
  if ((e.getDamager() instanceof Arrow)) {
	  LivingEntity shooter = ((Arrow)e.getDamager()).getShooter();
	if((shooter instanceof Player)){
    Arrow arow = (Arrow)e.getDamager();
    if (arow.hasMetadata("sniper_aro")){
      Random rnd = new Random();
      if (rnd.nextInt(100) < 12) {
    	  double dmgG = e.getDamage();
    	  double dmgN = dmgG * 200.0;  
    	  e.setDamage(dmgN);
    	  ((Player)shooter).sendMessage("Erfolg");
      } else {
          e.setDamage(10.0);
          ((Player)shooter).sendMessage("Kein OneHit");
      }

    }
    else;
  }
	}  
}
//Ende Listener
	
//Methoden
public void domeupdate(){
	this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

		public void run() {
		Domemap.clear();
		Domemap.add("§7Spieleranzahl: " + Domeplayers.size() + "/50");
		}
		}, 0L, 10L);
}
public void playerremove(Player p){
	int removed = 0;
	for (World Welten : getServer().getWorlds()){
		for(Entity enit : Welten.getEntities()){
			if(enit instanceof Projectile){
				enit.remove();
				removed++;
			}
		}
	}
	p.sendMessage(TDMTag + "§9Es wurden §4" + new Integer(removed).toString() + " §9Pfeile gelöscht.");
}
private boolean isArmor(Player pl) {
	  boolean content = false;

	  for (ItemStack item : pl.getInventory().getContents()) {
	    if ((item != null) && 
	      (item.getType() != Material.AIR)) {
	      content = true;
	    }

	  }

	  for (ItemStack item : pl.getInventory().getArmorContents()) {
	    if ((item != null) && 
	      (item.getType() != Material.AIR)) {
	      content = true;
	    }

	  }

	  return content;
	}

	private void delInv(Player pl) {
	  pl.getInventory().clear();
	  pl.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
	  pl.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
	  pl.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
	  pl.getInventory().setBoots(new ItemStack(Material.AIR, 1));}	
//Ende Methoden
	
	
	

}//Ende
