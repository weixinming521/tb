package com.travel.core.conversion;


import org.springframework.core.convert.converter.Converter;

/**
 * 去掉前后空格   
 *
 */
public class CustomTrimConverter implements Converter<String, String>{

	//转换过程 
	public String convert(String source) {
		// TODO Auto-generated method stub
		try {
			if(null != source){
				source = source.trim();  // ""
				if(!"".equals(source)){
					return source;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
