package me.ext.cmi.manager;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.ulrich.koth.Koth;

public class Files {

	private FileConfiguration language;
	private File pdfile;
	private String name;
	
	public Files(Koth koth, File folder, String path, String name, String resource, String extension) {
		this.name = name;
		
		
		this.pdfile = new File(folder + path, name + "."+extension);
		
		if (!pdfile.exists()) {
			try {
				pdfile.getParentFile().mkdirs();
				pdfile.createNewFile();
				
				if (resource != null) {
					copy(koth.getResource(resource + "."+extension), pdfile);
				}
			} catch (IOException ex) {
				
			}
		}
		
		language = YamlConfiguration.loadConfiguration(pdfile);
	}
	
	public FileConfiguration get() {
		return language;
	}
	
	public File getFile() {
		return pdfile;
	}
	
	public void save() {
		try {
			language.save(pdfile);
		} catch (IOException ex) {
			
		}
	}
	
	public void reload() {
		try {
			language = YamlConfiguration.loadConfiguration(pdfile);
		} catch (Exception ex) {
			
		}
	}
	
	public static void copy(InputStream is, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while ((len=is.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        is.close();
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }
	}
	
	public Files getFileManager() {
		return this;
	}

	public String getName() {
		return name;
	}
}