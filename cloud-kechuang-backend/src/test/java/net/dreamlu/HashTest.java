package net.dreamlu;

import com.jfinal.kit.HashKit;

/**
 * @author leilei.li
 * @date 创建时间：2017年6月11日下午11:30:41
 * @Description 
 * @version 1.0
 *
 */

public final class HashTest {
	public HashTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(HashKit.md5("****>>>>>>>*****%%%%"));
		System.out.println(HashKit.md5("admin"));
		
		String str1 = "abca11548be29b56c135f272e8b08333e49";
		String str2 = str1.replace("abca11548be29b56c135f272e8b08333e49", "");
		System.out.println(str2);
		String array[] = str1.split("abc");
		System.out.println(array[0]);
		
		String oldPwd = "";
		oldPwd = oldPwd.replace("a11548be29b56c135f272e8b08333e49", "");
		System.out.println(oldPwd);
	}
}
