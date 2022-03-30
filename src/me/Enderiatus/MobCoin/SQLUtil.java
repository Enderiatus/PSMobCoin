package me.Enderiatus.MobCoin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;


public class SQLUtil {
	
	String dbname = "datas";
	Connection connection;
	public String SQLiteCreateCoinTable = "CREATE TABLE IF NOT EXISTS PlayerCoins (" +
            "`player` varchar(32) NOT NULL," + 
            "`coins` int(11) NOT NULL," +
            "PRIMARY KEY (`player`)" +  
            ");";
	
	File dataFolder;
	
	Main plugin;
	public SQLUtil(Main plugin) {
		this.plugin = plugin;
		dataFolder = new File(plugin.getDataFolder(), dbname+".db");
	}
	
	 
	
	
	
	
	
	
	public Connection getSQL() {
		return connection;
	}
	
	public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Dosya yazma hatasi: "+dbname+".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }
	
	public void loadSQL() {
		connection = getSQLConnection();
        try {
            Statement s = (Statement) connection.createStatement();
            s.executeUpdate(SQLiteCreateCoinTable);
            s.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}






	public Integer getMobCoin(String name) {
		try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement("SELECT * FROM PlayerCoins WHERE player=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				PreparedStatement in = (PreparedStatement) connection
						.prepareStatement("INSERT INTO PlayerCoins(player,coins) VALUES('"+name+"', '0')");
				in.executeUpdate();
				in.close();
				return 0;
			}
			int coins = rs.getInt("coins");
			ps.close();
			rs.close();
			connection.close();
			return coins;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void setMobCoin(String name) {
		try {
			 Class.forName("org.sqlite.JDBC");
	            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement("UPDATE PlayerCoins SET coins=? WHERE player=?");
			ps.setInt(1, plugin.mobCoins.get(name));
			ps.setString(2, name);
			ps.executeUpdate();
			ps.close();
			connection.close();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	

}
