
package com.whty.cms.common.util;


public class CheckEmpty {
    public static boolean isEmpty(String check) {
        if (check == null || check.length() == 0) {
            return true;
        }

            return false;

    }

    public static boolean isNotEmpty(String check) {
        if (check != null && check.length() > 0) {
            return true;
        }

            return false;

    }
    
    public static String noNull(String str){
		if(CheckEmpty.isEmpty(str)){
			str = "";
		}
		return str;
    }

}
