package com.example.crypto;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class ImageCryptoUtilTest {

    // 测试图片文件解密
    @Test
    void encryptFile() throws Exception {
        String testRootPath = ImageCryptoUtilTest.class.getResource("/").getFile();
        // 解密
        File encFile = new File(testRootPath + "2023021017081538972.png");
        File decFile = new File(testRootPath + "2023021017081538972-dec.png");
        System.out.println(decFile.getAbsolutePath());
        try (FileInputStream fis = new FileInputStream(encFile);
             FileOutputStream fos = new FileOutputStream(decFile, true)) {
            ImageCryptoUtil.decryptedFile(fis, fos);
        }
    }


}