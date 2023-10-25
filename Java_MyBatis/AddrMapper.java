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
