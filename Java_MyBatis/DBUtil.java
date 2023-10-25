import java.io.InputStream;
import java.util.ArrayList;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



public class DBUtil {
	// DBUtil 이라는 클래스를 만들고 DB 작업 ( 데이터가 들어오면 JDBC 로 DBMS와 연동하여
	// 모든 데이터 저장 및 관리 ) 에 관한 모든 로직을 설정.
	// url 값은 workbench에 만든 스키마 명과 동일해야함.
	String url = "jdbc:mariadb://localhost:3306/addrapp";
	// user 와 pass 는 꼭 설정대로 정확하게 써야함.
	String user = "root";
	String pass = "12345";



	// Session 은 이런 자바와 같은 프로그램과 DBMS , JDBC 등 소통하는 것을 말함.
	// Sql 공장을 잇는 다리 정도로 이름을 만들어 변수명을 초기화 한다.

	SqlSessionFactory sqlSessionFactory ;

	// init = DBMS 와 연결이 잘 되었는지 확인해보는 작업
	// try catch 문으로 해당 xml 을 통해서 inputstream의 값이 sqlsessionfactory 로
	// 전달이 잘 되면 catch 문을 건너뛰고 아니면 적어둔 문장 출력.
	// e.printstacktrace 는 문제가 어디서 발생했는지 알려주는 함수.
	public void init() {
		try {
			String resource = "mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		} catch (Exception e) {
			System.out.println("MyBatis 설정 파일 가져오는 중 문제 발생!!");
			e.printStackTrace();
		}
	}

	// 주소를 모두 가져와 나열한 배열 메소드이다.
	// 외부에서 getAddresses 가 호출당하면 해당 업무를 수행
	// openSession 은 마이바티스 라이브러리 함수다.
	// 데이터베이스와 상호작용하기위한 세션을 열기위해 호출됨.
	// 세션을 연결하고 객체를 가져와서 작업이 완료되면 커밋을 한다.
	public ArrayList<Addr> getAddresses() {
		SqlSession session = sqlSessionFactory.openSession();
		AddrMapper mapper = session.getMapper(AddrMapper.class);
		ArrayList<Addr> addrList = mapper.getAddresses();
		
		return addrList;
	}
	
	public void insertAddress(String name, String address, String phone) {
		SqlSession session = sqlSessionFactory.openSession();
		AddrMapper mapper = session.getMapper(AddrMapper.class);
		Addr addr = new Addr(name, address, phone);
		mapper.insertAddress(addr);
		
		session.commit(); // update, delete, insert
	}
	
	public void updateAddress(int id, String name, String address, String phone) {
		SqlSession session = sqlSessionFactory.openSession();
		AddrMapper mapper = session.getMapper(AddrMapper.class);
		Addr addr = new Addr(id, name, address, phone);
		mapper.updateAddress(addr);
		
		session.commit(); // update, delete, insert
	}
	
	public void deleteAddress(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		AddrMapper mapper = session.getMapper(AddrMapper.class);
		mapper.deleteAddress(id);
		
		session.commit(); // update, delete, insert
	}
	
}
