package com.jspmyadmin.framework.web.logic;

import com.jspmyadmin.framework.exception.EncodingException;
import org.apache.commons.codec.EncoderException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface EncodeHelper {

	/**
	 * 
	 * @param data
	 * @return
	 * @throws EncodingException
	 */
	public String encrypt(String data) throws EncodingException;

	/**
	 * 
	 * @param encryptedData
	 * @return
	 * @throws EncodingException
	 */
	public String decrypt(String encryptedData) throws EncodingException;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateToken();

	/**
	 * 
	 * @param session
	 * @throws EncoderException
	 */
	public void generateKey(HttpSession session);

	/**
	 * 
	 * @param data
	 * @return
	 * @throws EncodingException
	 * @throws Exception
	 */
	public String encode(String data) throws EncodingException;

	/**
	 * 
	 * @param data
	 * @return
	 * @throws EncodingException
	 * @throws Exception
	 */
	public String decode(String data) throws EncodingException;

	/**
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public String encodeInstall(String data) throws IOException;

	/**
	 * 
	 * @param data
	 * @return
	 * @throws EncodingException
	 * @throws UnsupportedEncodingException
	 */
	public String decodeInstall(String data) throws EncodingException, UnsupportedEncodingException;
}
