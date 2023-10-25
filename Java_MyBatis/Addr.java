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