package me.ext.cmi;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.ext.cmi.manager.AddonFiles;
import me.ext.cmi.manager.Implementation;
import me.ulrich.koth.Koth;
import me.ulrich.koth.data.Extension;

public class CMI extends Extension {

	private static CMI extensionCore;
	private static Koth kothCore;
	private Logger logger = null;
	private AddonFiles files;

	@Override
	public void onEnable() {
		extensionCore = this;
		kothCore = (Koth) getInstance();
		this.logger = Logger.getLogger(this.getName());

		this.files = new AddonFiles(this);
		
		if (Bukkit.getPluginManager().isPluginEnabled("CMI")) {

			Plugin tab = Bukkit.getPluginManager().getPlugin("CMI");

			if(this.files.getConfig().getBoolean("Config.use_this")) {
				if(tab!=null) {
					if(!kothCore.getKothAPI().hasHoloImplemented()) {
						Implementation impl = new Implementation();
						kothCore.getKothAPI().addHoloImplement(impl);
						kothCore.getLogger().info(" - CMI ("+tab.getDescription().getVersion()+") [USING]");
						kothCore.getExtensionEnabledList().put(this, true);
						return;
					}
				}
			}
		}
		kothCore.getExtensionEnabledList().put(this, false);		
	}


	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	public static CMI getExtensionCore() {
		return extensionCore;
	}

	public static Koth getKothCore() {
		return kothCore;
	}


	public Logger getLogger() {
		return logger;
	}


	public AddonFiles getFiles() {
		return files;
	}



}
