package com.example.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Security;
/**
 * 图片文件加解密
 */
public class ImageCryptoUtil {

    private static byte[] keyBytes = "f5d965df75336270".getBytes(StandardCharsets.UTF_8);
    private static byte[] ivBytes = "97b60394abc2fbe1".getBytes(StandardCharsets.UTF_8);

    private ImageCryptoUtil() {
    }

    /**
     * 文件加密
     *
     * @param fis    原始文件读取流
     * @param fos    加密文件输出流
     */
    public static void encryptFile(FileInputStream fis, FileOutputStream fos) throws Exception{
        // 获取加密算法
        Cipher cipher = getCipher(keyBytes, ivBytes, Cipher.ENCRYPT_MODE);

        // 构造加密流并输出
        try (CipherInputStream cis = new CipherInputStream(fis, cipher)) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
        }
    }

    /**
     * 文件解密
     *
     * @param fis    加密文件输入流
     * @param fos    解密文件输出流
     */
    public static void decryptedFile(FileInputStream fis, FileOutputStream fos) throws Exception{
        // 获取解密算法
        Cipher cipher = getCipher(keyBytes, ivBytes, Cipher.DECRYPT_MODE);

        // 构造解密流并输出
        try (CipherInputStream cis = new CipherInputStream(fis, cipher)) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
        }
    }


    /**
     * 构造加密/解密算法
     * <p>
     * AES/CBC/PKCS7Padding 密码反馈模式
     *
     * @param encKeyBytes 加密密钥
     * @param ivBytes     加密向量
     * @param encryptMode 加密/解密
     */
    private static Cipher getCipher(byte[] encKeyBytes, byte[] ivBytes, int encryptMode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(encKeyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(encryptMode, secretKeySpec, iv);
        return cipher;
    }

    static  {
        //如果是PKCS7Padding填充方式，则必须加上下面这行
        Security.addProvider(new BouncyCastleProvider());
    }
}
