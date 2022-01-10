package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

import org.h2.mvstore.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Orders> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return jdbcTemplate.query(
            "SELECT * FROM (SELECT *, row_number() over(order by seq desc) as RNUM FROM orders ORDER BY seq DESC) WHERE rnum > ? and rnum <= ?",
            mapper,
            new Object[]{pageable.getOffset(), pageable.getOffset()+pageable.getSize()}
        );
    }

    @Override
    public Optional<Orders> findById(long id) {
        // TODO Auto-generated method stub
        List<Orders> results = jdbcTemplate.query(
            "SELECT * FROM orders WHERE seq=?",
            mapper,
            id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public Optional<Orders> findByIdAndState(long id, String state) {
        // TODO Auto-generated method stub
        System.out.println(id+" "+state);
        List<Orders> results = jdbcTemplate.query(
            "SELECT * FROM orders WHERE seq=? AND state=? AND review_seq is not null",
            mapper,
            new Object[]{id, state}
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public int accept(long id) {
        // TODO Auto-generated method stub
        return jdbcTemplate.update(
            "UPDATE orders set state=? WHERE seq=? and state='REQUESTED'",
            OrderState.ACCEPTED.getValue(),
            id
        );
    }

    @Override
    public int shipping(long id) {
        // TODO Auto-generated method stub
        return jdbcTemplate.update(
            "UPDATE orders set state=? WHERE seq=? and state='ACCEPTED'",
            OrderState.SHIPPING.getValue(),
            id
        );
    }

    @Override
    public int complete(long id) {
        // TODO Auto-generated method stub
        return jdbcTemplate.update(
            "UPDATE orders set state=?, completed_at=sysdate WHERE seq=? and state='SHIPPING'",
            OrderState.COMPLETED.getValue(),
            id
        );
    }

    @Override
    public int reject(long id, String rejectMsg) {
        // TODO Auto-generated method stub
        return jdbcTemplate.update(
            "UPDATE orders set state=?, reject_msg=?, rejected_at=sysdate WHERE seq=? and state='REQUESTED'",
            OrderState.REJECTED.getValue(),
            rejectMsg,
            id
        );
    }

    @Override
    public int review(long id, long reviewSeq) {
        // TODO Auto-generated method stub
        System.out.println("order review "+id+" / "+reviewSeq);
        return jdbcTemplate.update(
            "UPDATE orders set review_seq=? WHERE seq=? and state='COMPLETED'",
            OrderState.REJECTED.getValue(),
            reviewSeq,
            id
        );
    }

    static RowMapper<Orders> mapper = (rs, rowNum) ->
    new Orders.Builder()
      .seq(rs.getLong("seq"))
      .userSeq(rs.getLong("user_seq"))
      .productSeq(rs.getLong("product_seq"))
      .reviewSeq(rs.getLong("review_seq"))
      .state(rs.getString("state"))
      .requestMsg(rs.getString("request_msg"))
      .rejectMsg(rs.getString("reject_msg"))
      .completedAt(dateTimeOf(rs.getTimestamp("completed_at")))
      .rejectedAt(dateTimeOf(rs.getTimestamp("rejected_at")))
      .createAt(dateTimeOf(rs.getTimestamp("create_at")))
      .build();
    
}
