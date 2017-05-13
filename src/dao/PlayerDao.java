package dao;

import domain.Player;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.DBUtils;

/**
 *  Data Access Object
 * 与player相关的数据库操作
 * @author 
 */

public class PlayerDao {
	private Connection conn = DBUtils.getConnection();
        
	public void addRecord(Player player) throws Exception
        {

                String sql = " insert into player(name,time) values(?,?) ";
		PreparedStatement ptmt = (PreparedStatement) 
                conn.prepareStatement(sql);
		ptmt.setString(1, player.getName());
                ptmt.setString(2, player.getTime());
		ptmt.execute();
	}
                
	public List<Player> query() throws Exception
        {
		String sql = "select * from player ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs =	ps.executeQuery();
		List<Player> players = new ArrayList<Player>();
               
		while(rs.next())
                {
                        Player p = new Player();                       
                        p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setTime(rs.getString("time"));
                        players.add(p);
		}
		return players;
	}
}
