package cn.javayong.shardingjdbc5.spring.test;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sql.parser.api.CacheOption;
import org.apache.shardingsphere.sql.parser.api.SQLParserEngine;
import org.apache.shardingsphere.sql.parser.api.SQLStatementVisitorEngine;
import org.apache.shardingsphere.sql.parser.api.visitor.statement.SQLStatementVisitor;
import org.apache.shardingsphere.sql.parser.core.ParseASTNode;
import org.apache.shardingsphere.sql.parser.sql.common.statement.SQLStatement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SQLParseUnitTest {

    @Test
    public void testInsert() {
        boolean useCache = true;
        String sql = "insert into t_order (order_id,user_id,price) values (?,?,?)";

        // 语法树 AST
        CacheOption cacheOption = new CacheOption(128, 1024L);
        SQLParserEngine parserEngine = new SQLParserEngine("MySQL", cacheOption);
        ParseASTNode parseASTNode = parserEngine.parse(sql, useCache);

        // 通过 visit 模式
        SQLStatementVisitorEngine sqlVisitorEngine = new SQLStatementVisitorEngine("MySQL", true);
        SQLStatement sqlStatement = sqlVisitorEngine.visit(parseASTNode);
        System.out.println(sqlStatement);
    }

    @Test
    public void testSelect() {
        boolean useCache = true;
        String sql = "SELECT id, name FROM t_user WHERE status = 'ACTIVE' AND age > 18 AND a = 1";
        // 获取语法树
        CacheOption cacheOption = new CacheOption(128, 1024L);
        SQLParserEngine parserEngine = new SQLParserEngine("MySQL", cacheOption);
        ParseASTNode parseASTNode = parserEngine.parse(sql, useCache);

        SQLStatementVisitorEngine sqlVisitorEngine = new SQLStatementVisitorEngine("MySQL", true);
        SQLStatement sqlStatement = sqlVisitorEngine.visit(parseASTNode);

        System.out.println(sqlStatement);
    }

    @Test
    public void test() {
        //随机字符串100个
        //通过murmurhash处理后再取模。进行分类
        //最后统计每个分类的大小是否均匀
        System.out.println(RandomStringUtils.random(RandomUtils.nextInt(1,10),true,true));
        System.out.println(RandomStringUtils.random(RandomUtils.nextInt(1,10),true,true));
        System.out.println(RandomStringUtils.random(RandomUtils.nextInt(1,10),true,true));
        System.out.println(RandomStringUtils.random(RandomUtils.nextInt(1,10),true,true));
        List<Integer> result = Lists.newArrayList();
        for(int i=0;i<100;i++) {
            String randomStr = RandomStringUtils.random(RandomUtils.nextInt(1, 10), true, true);
            int i1 = MurmurHash3.hash32(randomStr);
            result.add(Math.abs(i1)%5);
        }
        Map<Integer, Long> result1 = result.stream().collect(Collectors.groupingBy(integer -> integer, Collectors.counting()));
        System.out.println(1);
        //MurmurHash3.
    }

}
