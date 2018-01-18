package com.etree.rts.utils;

import java.io.File;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etree.rts.constant.Constants;

public class FileUtils implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static void createDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}

	public static boolean containsCaseInsensitive(String s, List<String> l) {
		return l.stream().anyMatch(x -> x.equalsIgnoreCase(s));
	}


	public static byte[] encryptOrDecrypt(byte[] bytes, String password, boolean encrypt) throws Exception {
		try {
			int mode = encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
			DESKeySpec keySpec = new DESKeySpec(password.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_DES);
			SecretKey key = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error("Exception in encryptOrDecrypt", e);
		}
		return bytes;
	}

}
