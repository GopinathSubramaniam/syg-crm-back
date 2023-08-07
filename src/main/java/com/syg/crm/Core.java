package com.syg.crm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;

import com.fasterxml.jackson.databind.JsonNode;

public class Core {

	public static void main(String[] args) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm z");
		String startDate = simpleDateFormat.format(new Date());
		
		System.out.println(startDate);
	}
}
