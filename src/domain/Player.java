/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * 玩家实体类，对应数据库中的player表
 * @author 
 */
public class Player 
{
    private int id;
    private String name;
    private String time;

    public Player() 
    {
    }
    public Player( String name, String time) 
    {
        this.name = name;
        this.time = time;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time) 
    {
        this.time = time;
    }
    
}
