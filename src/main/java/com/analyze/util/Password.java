package com.analyze.util;
import java.security.MessageDigest;

/** *//**
* ��������м��ܺ���֤�ĳ���
* @author joe
*
*/

public class Password{
    
    //ʮ����������ֵ��ַ��ӳ������
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    
    /** *//** ��inputString����     */
    public static String createPassword(String inputString) {
        return encodeByMD5(inputString);
    }
    
    /** *//**
     * ��֤����������Ƿ���ȷ
     * @param password    ��������루���ܺ�������룩
     * @param inputString    ������ַ�
     * @return    ��֤���boolean����
     */
    public static boolean authenticatePassword(String password, String inputString) {
        if(password.equals(encodeByMD5(inputString))) {
            return true;
        } else {
            return false;
        }
    }
    
    /** *//** ���ַ����MD5����     */
    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                //��������ָ���㷨��Ƶ���ϢժҪ
                MessageDigest md = MessageDigest.getInstance("MD5");
                //ʹ��ָ�����ֽ������ժҪ���������£�Ȼ�����ժҪ����
                byte[] results = md.digest(originString.getBytes());
                //���õ����ֽ��������ַ���
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    /** *//**
     * ת���ֽ�����Ϊʮ������ַ�
     * @param b    �ֽ�����
     * @return    ʮ������ַ�
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
    
    /** *//** ��һ���ֽ�ת����ʮ�������ʽ���ַ�     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
    public static void main(String[] args) {
//        String password = Password.createPassword("547734");
//        String password1 = Password.createPassword("������Ŀ�У��кܶ���ڰ�ȫ�Ŀ��ǣ�����ѧϰ���ڼ��ܵ�֪ʶ���Եú���Ҫ�������ϴ�һС���ӣ�ϣ��Դ����");
//        System.out.println( password);
//        System.out.println( password1);
//        System.out.println("8888������ƥ�䣿" + 
//                Password.authenticatePassword(password, inputString));
    	System.out.println(byteToHexString((byte) 17));
    	System.out.println(Integer.toHexString(17));
    }

}