package ge.tbc.soap.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class DataBaseConfig {

    private static SqlSessionFactory sqlSessionFactory;

    private static SqlSessionFactory getSessionFactory() {
        if (sqlSessionFactory == null) {
            PooledDataSource dataSource = new PooledDataSource();
            dataSource.setDriver("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:tcp://localhost:9093/./companyDB;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE;MODE=MSSQLServer;DATABASE_TO_UPPER=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE");

            Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.addMapper(EmployeeMapper.class);

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        }
        return sqlSessionFactory;
    }

    public static EmployeeMapper employeeMapper() {
        SqlSession sqlSession = getSessionFactory().openSession(true);
        return sqlSession.getMapper(EmployeeMapper.class);
    }
}