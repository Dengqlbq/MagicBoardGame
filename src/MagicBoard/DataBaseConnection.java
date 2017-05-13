package MagicBoard;

import dao.PlayerDao;
import domain.Player;
import java.sql.*;
/**
 * 数据库连接
 * @author 
 */
public class DataBaseConnection {


	public void dbWrite(String name, String time) throws ClassNotFoundException,
                                                              SQLException,
                                                              Exception 
        {
            Player p = new Player();
            p.setName(name);
            p.setTime(time);
            new PlayerDao().addRecord(p);
	}

}
