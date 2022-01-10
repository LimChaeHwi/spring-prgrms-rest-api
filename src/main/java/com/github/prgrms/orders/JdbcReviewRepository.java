package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Optional<Review> findById(long id) {
        // TODO Auto-generated method stub
        List<Review> results = jdbcTemplate.query(
            "SELECT * FROM reviews WHERE seq=?",
            mapper,
            id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public int review(long id, String content) {
        // TODO Auto-generated method stub
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "INSERT INTO reviews (user_seq, product_seq, content, create_at) ";
                    sql += " SELECT user_seq, product_seq, ?, sysdate FROM orders WHERE seq = ?";
                PreparedStatement pstmt = con.prepareStatement(
                    sql
                    , new String[]{"SEQ"});
                pstmt.setString(1, content);
                pstmt.setLong(2, id);

                return pstmt;
            }
        },keyHolder);

        return keyHolder.getKey().intValue();
    }
    
    static RowMapper<Review> mapper = (rs, rowNum) ->
    new Review.Builder()
      .seq(rs.getLong("seq"))
      .userSeq(rs.getLong("user_seq"))
      .productSeq(rs.getLong("product_seq"))
      .content(rs.getString("content"))
      .createAt(dateTimeOf(rs.getTimestamp("create_at")))
      .build();
}
