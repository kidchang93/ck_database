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
