package com.analyze.interceptor;

import com.analyze.model.PriceApiTopic;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceApiTopicTypeHandler extends BaseTypeHandler<PriceApiTopic> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, PriceApiTopic topic, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, topic.getValue());
    }

    @Override
    public PriceApiTopic getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return PriceApiTopic.fromString(resultSet.getString(columnName));
    }

    @Override
    public PriceApiTopic getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return PriceApiTopic.fromString(resultSet.getString(columnIndex));
    }

    @Override
    public PriceApiTopic getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return PriceApiTopic.fromString(callableStatement.getString(columnIndex));
    }
}
