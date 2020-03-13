import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 手动创建数据源
 * @author Administrator
 *
 */
public class TestCreateDataSource {
		public static void main(String[] args) {
			DruidDataSource dataSource = new DruidDataSource();
			
			dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			dataSource.setUsername("EMuser"); 
			dataSource.setPassword("EMData2017gogo");
			dataSource.setUrl("jdbc:sqlserver://60.190.139.227:56779;databaseName=EMUSER"); 
			dataSource.setInitialSize(5); 
			dataSource.setMinIdle(1); 
			dataSource.setMaxActive(20);
			dataSource.setRemoveAbandoned(true);
			dataSource.setRemoveAbandonedTimeout(180);
			dataSource.setLogAbandoned(false);
			dataSource.setTimeBetweenEvictionRunsMillis(60000);
			dataSource.setName("1");
			
			try {
				System.out.println(dataSource.getConnection());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
