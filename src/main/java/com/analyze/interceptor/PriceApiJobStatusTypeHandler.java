package com.analyze.interceptor;

import com.analyze.model.PriceApiJobStatus;
import com.analyze.model.PriceApiTopic;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceApiJobStatusTypeHandler extends BaseTypeHandler<PriceApiJobStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, PriceApiJobStatus status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, status.toValue());
    }

    @Override
    public PriceApiJobStatus getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return PriceApiJobStatus.fromString(resultSet.getString(columnName));
    }

    @Override
    public PriceApiJobStatus getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return PriceApiJobStatus.fromString(resultSet.getString(columnIndex));
    }

    @Override
    public PriceApiJobStatus getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return PriceApiJobStatus.fromString(callableStatement.getString(columnIndex));
    }
}
