import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 操作CRUD，第一个参数：指定statement，规则：命名空间 + “.” + statementId
        // 第二个参数：指定传入sql的参数：这里是用户id
        // sqlSession.selectOne("mapper.selectAll");
//        DemoDAO mapper = sqlSession.getMapper(DemoDAO.class);
//        for (int i=0;i<100;i++) {
//            DemoBO demoBO = new DemoBO();
//            demoBO.setId(i);
//            demoBO.setId2("name "+i);
//            mapper.insertObj(demoBO);
//        }

//        for (int i=0;i<100;i++){
//            DemoBO demoBO = mapper.selectById(new Integer(i));
//            System.out.println(demoBO.getId() +" " +demoBO.getId2());
//        }
//
//        for (int i=0;i<100;i++){
//            mapper.updateById2(i, "namexxx" +i);
//        }
//
//        for (int i=0;i<100;i++){
//            DemoBO demoBO = mapper.selectById(i);
//            System.out.println(demoBO.getId() +" " +demoBO.getId2());
//        }

//        for (int i=0;i<100;i++){
////            List<DemoBO> demoBOS = mapper.selectEqualsId(i);
////            System.out.println("e "+ demoBOS.size());
//            List<DemoBO> name = mapper.selectLikeId2("name");
//            System.out.println("l" + name.size());
//        }
//
//        for (int i=0;i<100;i++){
//            mapper.deleteById(i);
//        }


    }

}
