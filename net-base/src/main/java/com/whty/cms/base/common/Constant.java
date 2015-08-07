package com.whty.cms.base.common;

/**
 *  系统常量
 * @ClassName: Constant
 * @author liyang
 * @date 2015-4-18
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class Constant {


	public interface BaseModulesConstant{
		/**
		 * 根节点
		 */
		public final static String ROOT = "0";
	}

	/**
	 * 菜单类型
	 * @author dzm
	 *
	 */
	public interface LeafTypeConstant{
		/**
		 * 菜单父节点
		 */
		public static final short LEAFTYPEPARENT=0;

		/**
		 * 菜单子节点
		 */
		public static final short LEAFTYPESON=1;

		/**
		 * 菜单按钮节点
		 */
		public static final short LEAFTYPEBUTTON=2;
	}


	/**
	 * 用户信息
	 * @author dzm
	 *
	 */
	public interface BaseUsersConstant{
		public final static String USERID = "userId";
		public final static String USERNAME = "userName";
		public final static String AUTH = "md5Auth";
		public final static String SERVICETICKET = "ServiceTicket";
		/**
		 * 锁定
		 */
		public final static String LOCKED = "1";

		public final static String UC_LOCKED = "0";
	}



	public  static final String SAVESTATUSADD="add";

	public static final String SAVESTATUSUPDATE="update";

	//菜单父节点
	public static final short LEAFTYPEPARENT=0;

	//菜单子节点
	public static final short LEAFTYPESON=1;

	//菜单按钮节点
	public static final short LEAFTYPEBUTTON=2;

	//固件版本预上线
	public static final String  PREONLINE = "00";
	//固件版本上线
	public static final String ONLINE = "01";
	//固件版本下线
	public static final String OFFLINE = "02";

	/**
	 * 按天统计
	 * */
	public static final String BYDAY = "1";

	/**
	 * 按月统计
	 */
	public static final String BYMONTH = "2";

	/**
	 * 按季统计
	 */
	public static final String BYSEASON = "3";

	/**
	 * 按年统计
	 */
	public static final String BYYEAR = "4";


	//用户名Cookie Name
	public static final String CAS_COOKIE_NAME = "CASLOGINUSER";
	//用户名Cookie Name
	public static final String CAS_COOKIE_AUTH = "CASAUTHINFO";

}
