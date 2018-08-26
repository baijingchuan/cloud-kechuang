package net.dreamlu.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * @author leilei.li
 * @date 创建时间：2017年10月16日下午2:45:31
 * @Description 
 * @version 1.0
 *
 */
public class CloudDecode {

	private static CloudDecode instance;
	private static Prop pro = null;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public CloudDecode() {
		pro = PropKit.use("cloud.properties");
	}
	
	public static synchronized CloudDecode getInstance(){
		if(instance == null){
			instance = new CloudDecode();
		}
		return instance;
	}
	
	public String getCodeValue(String key){
		String returnValue = "";
		try {
			if ((key == null) || (key.trim().equals(""))) {
				returnValue = "";
			} else {
				returnValue = pro.get(key);
			}
		} catch (Exception e) {
			logger.info("返回码 key=" + key + e.getMessage());
			e.printStackTrace();
		}
			return returnValue;
		}

}
