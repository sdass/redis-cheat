package com.subra;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

// public abstract class Application {
public class Application {

	@Bean public RedisConnectionFactory redisConnectionFactory(){
		JedisConnectionFactory cf = new JedisConnectionFactory();
		cf.setHostName("localhost"); cf.setPort(6379);
		cf.afterPropertiesSet();
		return cf;
	}
	
	@Bean public RedisTemplate redisTemplate(){
		//RedisTemplate<String, String> rt = new RedisTemplate<String, String>();
		RedisTemplate<String, Object> rt = new RedisTemplate<String, Object>();
		
		rt.setConnectionFactory(redisConnectionFactory());
		return rt;		
	}
	
	public static enum StringSerializer implements RedisSerializer<String> {
		INSTANCE;

		@Override
		public String deserialize(byte[] arg0) throws SerializationException {
			if (arg0.length > 0) {
				return new String(arg0);
			} else {
				return null;
			}
		}

		@Override
		public byte[] serialize(String arg0) throws SerializationException {

			if (arg0.length() == 0) {
				return null;
			} else {
				return arg0.getBytes();
			}
		}

	}
	
	public static enum LongSerializer implements RedisSerializer<Long>{
		INSTANCE;
		@Override
		public Long deserialize(byte[] arg0) throws SerializationException {
			if (arg0.length == 0){
				return null;
			}else {
			return Long.parseLong(new String(arg0));
			}
		}

		@Override
		public byte[] serialize(Long arg0) throws SerializationException {
			if (arg0 == null) 
			{
				return null;
			}else{
				return arg0.toString().getBytes();
			}			
		}
		
	}//enum
	
	
	  public static enum IntSerializer implements RedisSerializer<Integer> {
		    INSTANCE;

		    @Override public byte[] serialize( Integer i ) throws SerializationException {
		      if ( null != i ) {
		        return i.toString().getBytes();
		      } else {
		        return new byte[0];
		      }
		    }

		    @Override public Integer deserialize( byte[] bytes ) throws SerializationException {
		      if ( bytes.length > 0 ) {
		        return Integer.parseInt( new String( bytes ) );
		      } else {
		        return null;
		      }
		    }
		  }	
}//class ends


