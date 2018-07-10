package com.oraclechain.eosio.utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class MyStringUtils extends StringUtils {
	/**
	 * 回去uuid
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	/**
	 * 获取指定内容的md5值,暂时不加盐
	 * @param source
	 * @return
	 */
	public static String getMD5(String source) {
		if (source==null) {
			return null;
		}
		String MD5 = DigestUtils.md5DigestAsHex(source.getBytes());
		return MD5;
	}
	
	/*public static String getMD5(String source,String salt) {
		if (source==null) {
			return null;
		}
		String MD5=null;
		ByteSource byteSource = ByteSource.Util.bytes(salt);
		MD5 = new SimpleHash("MD5", source, byteSource, 1024).toString();
		return MD5;
		
		//1cha
		//2查
		//1更新
		//2更新
	}
	*/
}
