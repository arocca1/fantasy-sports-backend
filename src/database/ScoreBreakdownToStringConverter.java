package database;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fantasyuser.ScoreBreakdown;

@Converter
public class ScoreBreakdownToStringConverter implements AttributeConverter<ScoreBreakdown, String> {
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(ScoreBreakdown attribute) {
		String value = "";
        try {
            value = mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return value;
	}

	@Override
	public ScoreBreakdown convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, ScoreBreakdown.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
