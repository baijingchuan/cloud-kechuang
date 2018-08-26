package net.dreamlu;

import redis.clients.jedis.Jedis;

/**
 * @author leilei.li
 * @date 创建时间：2017年5月11日下午5:48:01
 * @Description 
 * @version 1.0
 *
 */

public class RedisJava {
	   public static void main(String[] args) {
	      //连接本地的 Redis 服务
	      Jedis jedis = new Jedis("localhost");
	      System.out.println("Connection to server sucessfully");
	      //查看服务是否运行
	      System.out.println("Server is running: "+jedis.ping());
	 }
	}