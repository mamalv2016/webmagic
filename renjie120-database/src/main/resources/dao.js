{
    dataSource : {
        type : "com.alibaba.druid.pool.DruidDataSource",
        events : {
            depose : 'close'
        },
        fields : {
            driverClassName : "com.mysql.jdbc.Driver",
            url : "jdbc:mysql://127.0.0.1:3306/tongji?useUnicode=true&characterEncoding=UTF8",
            username : "root",
            password : "987425",
            maxWait: 15000, // 若不配置此项,如果数据库未启动,druid会一直等可用连接,卡住启动过程,
            defaultAutoCommit : false // 提高fastInsert的性能
        }
    }
}