package ex1;

import com.newlecture.app.console.NoticeConsole;

public class Program5_paging {

	public static void main(String[] args) {
		
		NoticeConsole console = new NoticeConsole();
		
		EXIT: // 라벨문 해당 break가 여기서 끝남 
		while(true) {
			console.printNoticeList(); // 출력
			int menu = console.inputNoticeMenu(); // 입력
			
			switch(menu) {
				case 1: //상세조회
					break;
				case 2: //이전
					console.movePrevList();
					break;
				case 3: //다음
					console.moveNextList();
					break;
				case 4: //글쓰기
					break;
				case 5: //검색
					console.inputSearchWord();
					break;
				case 6: //종료
					System.out.println("Bye");
					break EXIT;
					// break 라벨문 사용
				default:
					System.out.println("숫자는 1~4까지만 입력가능");
					break;
			}
		}
	}
}
