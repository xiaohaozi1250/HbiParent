package practice.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * Created by xiaohaozi on 2019/3/10.
 */


@Service
public class RedisDemo {

    @Autowired
    RedisTemplate<String,Object> redisTemplate ;

    public  void redisList(){

        String key = "RedisListkey";
        ListOperations<String,Object> lop = redisTemplate.opsForList();

        //初始化redis序列化器
        RedisSerializer<String> serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        // rt.setDefaultSerializer(serializer);

        lop.leftPush(key, "aaa");
        lop.leftPush(key, "bbb");
        long size = lop.size(key); // rt.boundListOps(key).size();
        System.out.println("size: " + size);

        //对应reidis命令 LRANGE RedisListkey 0 10

    }


}
