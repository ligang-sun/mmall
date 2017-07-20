package com.mmall.util.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * com.fasterxml.jackson.databind.annotation.JsonSerialize<br/>
 * 日期的格式化方式<br/>
 * 
 * @author ligang.sun
 *
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void serialize(Date tmpDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
	        throws IOException, JsonProcessingException {

		if (tmpDate != null) {
			jsonGenerator.writeString(sdf.format(tmpDate));
		} else {
			jsonGenerator.writeNull();
		}
	}

}
