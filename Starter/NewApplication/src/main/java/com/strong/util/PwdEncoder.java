//package com.strong.util;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.Mac;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
//@SuppressWarnings("restriction")
//public abstract class PwdEncoder {  
//    public static final String KEY_SHA = "SHA";  
//    public static final String KEY_MD5 = "MD5";  
//  
//    /** 
//     * MAC算法可选以下多种算法 
//     *  
//     * <pre> 
//     * HmacMD5  
//     * HmacSHA1  
//     * HmacSHA256  
//     * HmacSHA384  
//     * HmacSHA512 
//     * </pre> 
//     */  
//    public static final String KEY_MAC = "HmacMD5";  
//  
//    /** 
//     * BASE64解密 
//     *  
//     * @param key 
//     * @return 
//     * @throws Exception 
//     */  
//    public static byte[] decryptBASE64(String key) throws Exception {  
//        return (new BASE64Decoder()).decodeBuffer(key);  
//    }  
//  
//    /** 
//     * BASE64加密 
//     *  
//     * @param key 
//     * @return 
//     * @throws Exception 
//     */  
//    public static String encryptBASE64(byte[] key) throws Exception {  
//        return (new BASE64Encoder()).encodeBuffer(key);  
//    }  
//  
//    /** 
//     * MD5加密 
//     *  
//     * @param data 
//     * @return 
//     * @throws Exception 
//     */  
//    public static byte[] encryptMD5(byte[] data) throws Exception {  
//  
//        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);  
//        md5.update(data);  
//  
//        return md5.digest();  
//  
//    }  
//  
//    /** 
//     * SHA加密  - SHA encrypt  (Secure Hash Algorithm，安全散列算法)
//     *  
//     * @param data 
//     * @return 
//     * @throws Exception 
//     */  
//    public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException {  
//  
//        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
//        sha.update(data);  
//  
//        return sha.digest();  
//  
//    }  
//  
//    /** 
//     * 初始化HMAC密钥  - init the HMAC secret key
//     *  
//     * @return 
//     * @throws Exception 
//     */  
//    public static String initMacKey() throws Exception {  
//        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);  
//  
//        SecretKey secretKey = keyGenerator.generateKey();  
//        return encryptBASE64(secretKey.getEncoded());  
//    }  
//  
//    /** 
//     * HMAC加密  - HMAC encrypt (Hash Message Authentication Code，散列消息鉴别码)
//     *  
//     * @param data 
//     * @param key 
//     * @return 
//     * @throws Exception 
//     */  
//    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {  
//    	//according to the given byte array to generate a secret key, the second param is the encrypt algorithm name
//        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);  // 据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
//      
//        Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
//        mac.init(secretKey);  
//  
//        return mac.doFinal(data);  
//  
//    }  
//    
//    /** 
//     * SHA+HMAC encrypt 
//     *  SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串，
//     *  通常用来校验数据在传输过程中是否被修改。其中HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。
//     *  单向加密的用途主要是为了校验数据在传输过程中是否被修改。 
//     * @param data 
//     * @param key 
//     * @return 
//     * @throws Exception 
//     */  
//    public static String encryptSHA2MAC(String name,String pass) throws Exception{
//    	//mix the password and username, username is only  like password-{username}
//    	String passStr = pass+"-{"+name+"}";//将密码和用户名混合，用户名唯一   password-{username} 
//    	
//    	byte inputData[] = passStr.getBytes();
//		BigInteger sha = new BigInteger(PwdEncoder.encryptSHA(inputData));//SHA加密 - SHA encrypt
//		String shaPass = sha.toString(32);
//		
//		byte shaData[]  = shaPass.getBytes();
//		BigInteger mac = new BigInteger(PwdEncoder.encryptHMAC(shaData, shaPass));//HMAC再次加密 HMAC encrypt again
//    	return mac.toString(16);
//    }
//}  
