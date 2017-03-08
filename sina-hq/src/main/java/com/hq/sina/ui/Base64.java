package com.hq.sina.ui;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/*
 * @author by wanglin
 */
public class Base64 {
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptBASE64(String key) throws Exception {
		return new String((new BASE64Decoder()).decodeBuffer(key));
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
	
	public static void main(String[] args)
	{
		String s="hebpasc@2020";
		try {
			System.out.println(Base64.encryptBASE64(s.getBytes()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(Base64.decryptBASE64("aGVicGFzY0AyMDIw"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
