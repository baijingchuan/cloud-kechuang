package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;





/**
 * 
 * @ClassName: MemberMoney 
 * @Description: 用户钱包model
 * @author ycj
 * @date 2017年11月7日 下午5:31:21 
 *
 */
@SuppressWarnings("serial")
public class MemberMoney extends Model<MemberMoney> {

	public static final MemberMoney dao = new MemberMoney();
	
	
	

	
	public static final String baseSql = " user_id, total_income, withdraw, transaction_password ";

	// user_id: 用户ID
	// total_income: 账户余额
	// withdraw 提现金额
	// transaction_password 交易密码

	/**
	 * 设置钱包密码
	 * @param transactionPassword
	 * @param userId
	 */
	public int setTransactionPassword( String transactionPassword, long userId) {
		String sql = "update  yx_member_money set transaction_password= ?  WHERE user_id= ? ";
		return Db.update(sql, transactionPassword, userId);
	}
	

	
	

}
