package com.syg.crm.util;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.syg.crm.enums.LeadStatus;

public class Util {

	public static String UPLOAD_MEDIA_FOLDER = "/media";
	public static String UPLOAD_PROD_IMG_FOLDER = Util.UPLOAD_MEDIA_FOLDER + "/image/product";

	public static String getLoggedInUserName(String data) {
		byte[] decodedBytes = Base64.getDecoder().decode(data);
		String decodedVal = new String(decodedBytes);

		String[] vals = decodedVal.split("~");
		String token = "";
		if (vals.length > 3) {
			token = vals[4];
		}

		return token;
	}

	public static Date convert(String date) {
		Date formattedDate = null;
		try {
			if (date != null)
				formattedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate;
	}

	public static String toString(Object obj) throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(obj);
		return json;
	}

	public static String getLeadStatusAbbr(LeadStatus status) {
		String val = "New";
		if (LeadStatus.O.equals(status)) {
			val = "Own";
		} else if (LeadStatus.L.equals(status)) {
			val = "Loss";
		}

		return val;
	}

}
