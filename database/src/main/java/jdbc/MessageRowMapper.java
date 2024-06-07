package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import message.Message;

import org.springframework.jdbc.core.RowMapper;

public class MessageRowMapper implements RowMapper<Message> {

	@Override
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message m = new Message();
		m.setId(rs.getInt("id"));
		m.setName(rs.getString("name"));
		m.setMessage(rs.getString("message"));
		m.setCreationTime(rs.getTimestamp("creationTime"));
		return m;
	}

}
