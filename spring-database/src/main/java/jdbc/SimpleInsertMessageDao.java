package jdbc;

import java.util.List;

import javax.sql.DataSource;

import message.Message;
import message.MessageDao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class SimpleInsertMessageDao implements MessageDao {

	private MessageDao delegate;
	private SimpleJdbcInsert simpleInsert;

	public SimpleInsertMessageDao(DataSource dataSource) {
		simpleInsert = new SimpleJdbcInsert(dataSource);
		simpleInsert.withTableName("guestmessage")
					.usingColumns("name", "message", "creationTime")
					.setGeneratedKeyName("id");
		
		delegate = new JdbcTemplateMessageDao(dataSource);
	}

	@Override
	public int insert(Message message) {
		/*
		Map<String, Object> values = new HashMap<>();
		values.put("NAME", message.getName());
		values.put("message", message.getMessage());
		values.put("creationTime", new Timestamp(message.getCreationTime().getTime()));
		Number genKey = simpleInsert.executeAndReturnKey(values);
		*/
	    BeanPropertySqlParameterSource paramSource =
	            new BeanPropertySqlParameterSource(message);
	    Number genKey = simpleInsert.executeAndReturnKey(paramSource);
		return genKey.intValue();
	}

	public List<Message> select(int start, int size) {
		return delegate.select(start, size);
	}

	public int counts() {
		return delegate.counts();
	}

	public int delete(int id) {
		return delegate.delete(id);
	}

}
