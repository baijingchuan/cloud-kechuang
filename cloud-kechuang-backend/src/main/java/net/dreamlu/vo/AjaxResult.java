package net.dreamlu.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.JsonKit;

/**
 * 功能描述: 封装ajax返回
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年7月31日 下午9:50:58
 */
public class AjaxResult<T> implements Serializable{

	private static final long serialVersionUID = 774750022185298023L;

	// 标记成功失败，默认true：成功，false：失败、用于alert，2：失败、用于confirm
	private boolean success = true;

	// 返回的中文消息
	private String msg;
	
	// 返回的编码
	private String code = "1000"; //默认返回成功编码 1000
	
	// 成功时携带的数据
	private T result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	// 校验错误
	public boolean hasError() {
		return this.success != true;
	}

	// 添加错误，用于alertError
	public AjaxResult<T> addError(String code, String msg) {
//		if(code == null){
//			this.code = "";
//		}else{
//			this.code = code;
//		}
		
		this.code = "1001";
		this.result = (T) new JSONObject();
		this.msg = msg;
		this.success = false;
		return this;
	}
	
	// 添加错误，用于alertError
	public AjaxResult<T> addError(String msg) {
		this.code = "1001";
		this.result = (T) new JSONObject();
		this.msg = msg;
		this.success = false;
		return this;
	}

	/**
	 * 用于Confirm的错误信息
	 * @param addConfirmError
	 * @return AjaxResult
	 */
	public AjaxResult<T> addConfirmError(String code, String msg) {
//		if(code == null){
//			this.code = "";
//		}else{
//			this.code = code;
//		}
		this.code = "1001";
		this.result = (T) new JSONObject();
		this.msg = msg;
		this.success = false;
		return this;
	}
	
	/**
	 * 用于Confirm的错误信息
	 * @param addConfirmError
	 * @return AjaxResult
	 */
	public AjaxResult<T> addConfirmError(String msg) {
		this.code = "1001";
		this.result = (T) new JSONObject();
		this.msg = msg;
		this.success = false;
		return this;
	}

	/**
	 * 封装成功时的数据
	 * @param jsonObject
	 * @return AjaxResult
	 */
	public AjaxResult<T> success(T result, String msg) {
		this.success = true;
		this.result = result;
		this.msg = msg;
		return this;
	}

	/**
	 * 封装成功时的数据
	 * @param jsonObject
	 * @return AjaxResult
	 */
	public AjaxResult<T> success(String code, T result, String msg) {
//		if(code == null){
//			this.code = "";
//		}else{
//			this.code = code;
//		}
		
		this.success = true;
		this.result = result;
		this.msg = msg;
		return this;
	}
	@Override
	public String toString() {
		return JsonKit.toJson(this);
	}
}
