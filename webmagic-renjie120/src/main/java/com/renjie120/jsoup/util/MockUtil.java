package com.renjie120.jsoup.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 包机切位的航班过滤工具类.
 * 
 * @author Administrator
 * 
 */
public class MockUtil {
	/**
	 * 删除一个文件.
	 * 
	 * @param filename
	 *            文件名.
	 * @throws IOException
	 */
	public static void deleteFile(String filename) {
		File file = new File(filename);
		file.deleteOnExit();
	}

	/**
	 * 复制文件.
	 * 
	 * @param srcFileName
	 * @param desFileName
	 */
	public static void copyFile(String srcFileName, String desFileName) {
		try {
			FileChannel srcChannel = new FileInputStream(srcFileName)
					.getChannel();

			FileChannel dstChannel = new FileOutputStream(desFileName)
					.getChannel();

			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			srcChannel.close();
			dstChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对指定文件添加字符串内容.
	 * 
	 * @param fileName
	 * @param contant
	 */
	public static void appendToFile(String fileName, String contant) {
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName,
					true)));
			out.print(contant);
			out.close();
		} catch (IOException e) {
			System.out.println("读写文件出现异常！");
		} catch (Exception e) {
			System.out.println("出现异常");
		}
	}

	/**
	 * 读取文件为字节数组
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFile(String filename) throws IOException {
		File file = new File(filename);
		if (filename == null || filename.equals("")) {
			throw new NullPointerException("无效的文件路径");
		}
		long len = file.length();
		byte[] bytes = new byte[(int) len];
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(file));
		int r = bufferedInputStream.read(bytes);
		if (r != len)
			throw new IOException("读取文件不正确");
		bufferedInputStream.close();
		return bytes;
	}

	/**
	 * 将字节数组写入文件
	 * 
	 * @param data
	 *            byte[]
	 * @throws IOException
	 */
	public static void writeFile(byte[] data, String filename)
			throws IOException {
		File file = new File(filename);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				new FileOutputStream(file));
		bufferedOutputStream.write(data);
		bufferedOutputStream.close();
	}

	/**
	 * 将字符数组转换为字节数组
	 * 
	 * @param chars
	 * @return
	 */
	public byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);

		return bb.array();
	}

	/**
	 * 字节数组转换为字符数组
	 * 
	 * @param bytes
	 * @return
	 */
	public char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}

	/**
	 * 读取指定文件的内容，返回文本字符串
	 * 
	 * @param fileName
	 *            文件名
	 * @param linkChar
	 *            换行符号
	 * @return
	 */
	public static String readFile(String fileName, String linkChar) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in;
		String result = "";
		try {
			// 定义文件读的数据流
			in = new BufferedReader(new FileReader(fileName));
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s);
				// 换行符号默认是13!!
				if (linkChar == null || "".equals(linkChar))
					sb.append((char) 13);
				else
					sb.append(linkChar);
			}
			in.close();
			int i = linkChar.length();
			result = sb.toString();
			result = result.subSequence(0, sb.length() - i).toString();
		} catch (FileNotFoundException e) {
			System.out.println("找不到文件" + fileName + "!");
			throw new Exception("文件找不到！");
		} catch (IOException e) {
			System.out.println("出现异常！");
			throw new Exception("文件找不到！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出现异常！");
			throw new Exception("文件找不到！");
		} finally {
			return result;
		}
	}

	/**
	 * 将指定文件中的内容已每行转换为字符串数组
	 * 
	 * @param fileName
	 * @return
	 */
	public static String[] readFileToStrArr(String fileName) {
		BufferedReader in;
		ArrayList list = new ArrayList();
		String[] result = null;
		try {
			// 定义文件读的数据流
			in = new BufferedReader(new FileReader(fileName));
			String s;
			while ((s = in.readLine()) != null) {
				list.add(s);
			}
			result = new String[list.size()];
			Iterator it = list.iterator();
			int index = 0;
			while (it.hasNext()) {
				result[index++] = it.next().toString();
			}
			return result;
		} catch (FileNotFoundException e) {
			System.out.println("找不到文件！");
			throw new Exception("文件找不到！");
		} catch (IOException e) {
			System.out.println("出现异常！");
			throw new Exception("文件找不到！");
		} finally {
			return result;
		}
	}

	/**
	 * 将字符串写进文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param contant
	 *            要写入文件的字符串
	 */
	public static void writeFile(String fileName, String contant) {
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			out.print(contant);
			out.close();
		} catch (IOException e) {
			System.out.println("读写文件出现异常！");
		} catch (Exception e) {
			System.out.println("出现异常");
		}
	}

	/**
	 * 字符串转换为字符数组
	 * 
	 * @param str
	 * @return
	 */
	public static char[] strToChars(String str) {
		try {
			byte[] temp;
			temp = str.getBytes(System.getProperty("file.encoding"));
			int len = temp.length;
			char[] oldStrbyte = new char[len];
			for (int i = 0; i < len; i++) {
				char hh = (char) temp[i];
				if (temp[i] < 0) {
					hh = (char) (temp[i] + 256);
				}
				oldStrbyte[i] = hh;
			}
			return oldStrbyte;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
