package org.hbhk.test.redis;

import java.util.HashSet;
import java.util.Set;

public class RedisTest {

	static String prefix = "MC_CMS_CMSPAGEINSTANCEMANAGER_FINDPUBLISHPAGE";

	public static void main(String[] args) {
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("10.8.12.202:26379");
		sentinels.add("10.8.4.18:26379");
		sentinels.add("10.8.4.19:26379");
		RedisClient redisClient = new RedisClient("mymaster", sentinels);
		System.out.println(redisClient.stringHgetOperate(prefix, "asd"));
	}
}
