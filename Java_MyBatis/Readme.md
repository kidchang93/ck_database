# DATABASE PROJECT 📑

### (데이터베이스 프로젝트)

Java 와 Database 를 연결하여
콘솔로 출력하는 프로젝트 입니다.

## 목차

Ⅰ) **기술 스택**

Ⅱ) **주요 기능**

Ⅲ) **콘솔 구현**

Ⅳ) **기능 구현 코드와 RDBMS와의 연관성**

Ⅴ) **프로젝트를 마치며 느낀 점**


## Ⅰ) 기술 스택

### 사용 언어

- Java
- MyBatis
- MySQL
- Workbench

## Ⅱ) 주요 기능

- 데이터를 입출력하는 과정에서
- MyBatis 를 사용하여 JDBC의 대부분의 기능을 수행시키며
- MySQL로 데이터 베이스에 저장 및 관리 하는 RDBMS 이용

## Ⅲ) UX / UI
## add 기능 구현
![add](https://github.com/kidchang93/ck_database/assets/145524731/56ba05f9-93eb-4e7f-98b4-cf01d84a2c89)

## list 기능 구현
![list](https://github.com/kidchang93/ck_database/assets/145524731/0b0126ab-1242-4dba-bbbf-8fb318d1b205)

## update 기능 구현
![update](https://github.com/kidchang93/ck_database/assets/145524731/65333585-4a5c-4dec-973a-7a7da96be181)

## delete 기능 구현
![delete](https://github.com/kidchang93/ck_database/assets/145524731/c5f30a8d-f6a4-4948-b77b-e519b1eca4b1)

## exit 기능 구현
![exit](https://github.com/kidchang93/ck_database/assets/145524731/ab061ef8-1931-4f41-b584-d84d50979512)

## Ⅳ) 기능 구현 코드와 RDBMS와의 연관성

- sql 문으로 workbench 에 테이블을 생성.

```sql
create table t_address (
    id            int             primary key auto_increment,
    name          varchar(20)    not null,
    address       varchar(10)     not null,
    phone         varchar(13)     not null
);
```

## Mybatis를 이용한 RDBMS 연결
- 임시로 username 과 password는 공개함.
- xml 파일은 MyBatis 홈페이지에서 제공하지만
  username 과 password 는 본인이 설정한 값을 적어야한다.
- 역시 폴더명과 파일명 주의해야한다.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <dataSource type="POOLED">
        <!-- 다른 부분 건드릴 필요 없이 이 네부분만 정확하게 잘 적어주면 된다. -->
        <property name="driver" value="org.mariadb.jdbc.Driver"/>
        <property name="url" value="jdbc:mariadb://localhost:3306/addrapp"/>
        <property name="username" value="root"/>
        <property name="password" value="12345"/>
        <!-- #######################################################. -->
      </dataSource>
    </environment>
  </environments>
   
  <mappers>
    <!-- 폴더명/파일명 주의하여 잘 써야함.-->
    <mapper resource="AddrMapper.xml"/>
  </mappers>
   
</configuration>
```

## mapper 설정 (경로와 파일이름 주의해서 작성해야함)
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--xml 파일 경로 & 파일 이름 주의-->
<mapper namespace="AddrMapper">

	<!-- 해당 sql을 메서드와 연결해서 처리. id는 메서드명, resultType 리턴, parameterType 매개변수 -->
	<!--	리턴값과 매개변수는 '형' 이기 때문에 값이 하나만 들어와야 하므로 Addr 이라는 객체(한덩어리)로 받아준다. -->
    <select id="getAddresses" resultType="Addr">
      SELECT *
      FROM t_address
    </select>

    <insert id="insertAddress" parameterType="Addr">
    	INSERT INTO t_address
    	SET `name` = #{name},
    	address = #{address},
    	phone = #{phone}
    </insert >

  	<update id="updateAddress" parameterType="Addr">
  		UPDATE t_address
  		SET `name` = #{name},
    	address = #{address},
    	phone = #{phone}
    	WHERE id = #{id}
  	</update>

  	<delete id="deleteAddress" parameterType="int">
  		DELETE FROM t_address
  		WHERE id = #{id}
  	</delete>

</mapper>
```

### JAVA에서 재밌게 구현한 기술
- Java에서 DB관련된 모든 로직을 다루는 폴더를 생성하고
  그 폴더에 외부 로직들 ( 메서드 , 클래스 ) 의 값을 호출해
  다시 RDBMS 로 넘겨주는 작업이 굉장히 흥미로웠다.
  아직은 MVC 모델에 대해 완벽하게 이해가 되진 않은 상태라 설명이 부족할 수 있다.
  
  아래 해당 코드들에 남겨진 주석들을 보면 대략적으로
  경로들을 유추할 수 있을 것이다.

  
```java
import java.util.ArrayList;
import java.util.Scanner;

// AddrApp 실행문
public class AddrApp {

  public static void main(String[] args) {
		// DBUtill 생성자
		DBUtil db = new DBUtil();
		// 사용자 입력값을 받아오기위한 scanner 라이브러리 생성
		Scanner scan = new Scanner(System.in);
		// db 설정값을 실행.
		db.init();
		// while 문으로 입력값에 대한 true false 값일 경우 해당 값에 맞는 결과값을 출력한다.
		while(true) {
			System.out.print("명령어를 입력해주세요 : ");
			// 입력받은 값을 cmd 라는 변수로 초기화 시켜준다.
			String cmd = scan.nextLine();
			
			if(cmd.equals("exit")) {
				break;
				
			} else if(cmd.equals("add")) {
				System.out.println("========= 주소록 등록 =========");
				System.out.print("이름 : ");
				// 처음 친 이름은 name 이라는 변수에 대입.
				String name = scan.nextLine();
				System.out.print("주소 : ");
				// 두번째로 입력한 값을 address 라는 변수에 대입.
				String address = scan.nextLine();
				System.out.print("전화번호 : ");
				// 세번째로 입력한 값을 phone 이라는 변수에 대입.
				String phone = scan.nextLine();

				// 이 받아온 값들을 한번에 DBUtil 안에 있는 insertAddress에 정보를 입력한다.
				db.insertAddress(name, address, phone);
				
				System.out.println("주소록 등록 완료.");
				System.out.println("============================");

				// list를 쳤을 경우
			} else if(cmd.equals("list")) {
				// addresses 의 정보가 기입되어있는 값들의 배열을
				// addrList 라는 변수로 초기화해준다.
				ArrayList<Addr> addrList = db.getAddresses();
				// 웹뷰 생성자
				WebView wv = new WebView();
				// 리스트에 있는 값들을 웹뷰의 printAddr 에 정보를 기입한다.
				wv.printAddr(addrList);
				
				
			} else if(cmd.equals("update")) {
				System.out.print("몇번 주소록을 수정하시겠습니까 : ");
				int id = Integer.parseInt(scan.nextLine()); 
				System.out.println("========= 주소록 수정 =========");
				System.out.print("이름 : ");
				String name = scan.nextLine();
				System.out.print("주소 : ");
				String address = scan.nextLine();
				System.out.print("전화번호 : ");
				String phone = scan.nextLine();

				// 이 받아온 값들을 한번에 DBUtil 안에 있는 updateAddress에 정보를 입력한다.
				db.updateAddress(id, name, address, phone);
				
				System.out.println("주소록 수정 완료.");
				System.out.println("============================");
				
			} else if(cmd.equals("delete")) {
				System.out.print("몇번 주소록을 삭제하시겠습니까 : ");

				int id = Integer.parseInt(scan.nextLine());
				// deleteAddress 에 id 값이 들어가면 해당 메소드 실행.
				db.deleteAddress(id);

				System.out.println(id + "번 주소록이 삭제되었습니다.");
				System.out.println("==============================");
			}
		}	
	}
}

```

```java
public class Addr {
	// Addr 클래스 생성
	// id 는 int 값으로 초기화와 동시에 선언.
	// name,address,phone 은 stringd으로 동일하게 초기화와 선언.
	// 모두 private 으로 외부에서 접근은 불가능하다.
	private int id;
	private String name;
	private String address;
	private String phone;

	// 같은 이름의 메소드이지만 오버로딩되어 얼마든지 사용 가능하다.
	public Addr(String name, String address, String phone) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	public Addr(int id, String name, String address, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	// Getter = 접근자 메소드로 객체의 속성값을 반환하는 메소드.
	// Setter = 설정자 메소드로 객체의 속성값을 설정하거나 수정하는 메소드.
	// 객체지향 프로그래밍에서 데이터 은닉 및 캡슐화를 지원하는 메소드.
	// private으로 설정된 id와 기타 등등 값들을 직접 접근 하는 것을 방지하는 것으로 쓰임.
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}	
}
```

```java
import java.util.ArrayList;

public interface AddrMapper {
	// 인터페이스 생성

	// void 니까 return 값은 없음.
	// 해당 메소드들을 구현한다.
	public ArrayList<Addr> getAddresses();
	public void insertAddress(Addr addr);
	public void updateAddress(Addr addr);
	public void deleteAddress(int id);
}
```

```java
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
```

## Ⅴ) 프로젝트를 마치며 느낀 점

이 프로젝트를 진행하면서 Web으로 아직 출력할 수 있는 실력은 아니어서 아쉬웠다.
그러나 내가 할 수 있는 범위 내에서 최선을 다한 점은 후회없다.
평소 java 언어를 배우면서 직관적으로 어떤 형태를 본적은 없어서
막막하고 힘들었지만 MVC 모델에대해 한발 다가서니
보진 못해도 머릿속에서 구조가 그려지는게 흥미로웠다.
내가 어떤 일을 수행해야되는지 프로그램이 어떻게 동작이 되는지
컴퓨터는 어떤 명령을 어떻게 받아들이고 다시 리턴하는지
이 구조를 알 수 있는 시간이었고 구조에 대한 어려움을 겪고있다면
해당 프로젝트를 따라해보면서 하나하나 뜯어보고 조립해보는 시간을 가지면
나보다 어쩌면 더 많은 것을 배울 수 있을 것이다.

# Peace Out! 🙌

