package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import message.Message;
import message.MessageDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcTemplateMessageDao2 implements MessageDao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplateMessageDao2(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<Message> messageRowMapper = new MessageRowMapper();

	@Override
	public List<Message> select(final int start, final int size) {
//		List<Message> messages = jdbcTemplate.query(
//				new PreparedStatementCreator() {
//					@Override
//					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//						PreparedStatement pstmt = con.prepareStatement("select * from guestmessage order by id desc limit ?, ?");
//						pstmt.setInt(1, start);
//						pstmt.setInt(2, size);
//						return pstmt;
//					}
//				},
//				messageRowMapper);
		List<Message> messages = jdbcTemplate.query(
				"select * from guestmessage order by id desc limit ?, ?",
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, start);
						ps.setInt(2, size);
					}
				},
				messageRowMapper);
		return messages;
	}

	@Override
	public int counts() {
		return jdbcTemplate.queryForObject(
				"select count(*) from guestmessage",
				Integer.class);
	}

	@Override
	public int insert(final Message message) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstmt = conn
						.prepareStatement(
								"insert into guestmessage (name, message, creationTime) values (?,?,?)",
								new String[] { "id" });
				pstmt.setString(1, message.getName());
				pstmt.setString(2, message.getMessage());
				pstmt.setTimestamp(3, new Timestamp(message.getCreationTime()
						.getTime()));
				return pstmt;
			}
		}, keyHolder);
		Number idNum = keyHolder.getKey();
		return idNum.intValue();
	}

	@Override
	public int delete(int id) {
		return jdbcTemplate.update("delete from guestmessage where id = ?", id);
	}

}
