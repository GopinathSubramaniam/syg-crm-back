package com.syg.crm;

import com.syg.crm.model.User;
import com.syg.crm.util.Util;

public class Core {

	public static void main(String[] args) throws Exception {

		User u = new User();
		u.setEmail("gopiwrld@gmail.com");
		
		System.out.println(Util.toString(u));
	}
}
