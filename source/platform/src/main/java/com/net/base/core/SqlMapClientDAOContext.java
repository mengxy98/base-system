package com.net.base.core;

import com.ibatis.sqlmap.client.SqlMapClient;
import javax.sql.DataSource;


public class SqlMapClientDAOContext
{
  private SqlMapClient sqlMapClient;
  private DataSource dataSource;

  public SqlMapClient getSqlMapClient()
  {
    return this.sqlMapClient;
  }
  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  public DataSource getDataSource() {
    return this.dataSource;
  }
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
