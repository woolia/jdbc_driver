package com.newlecture.app.console;

import java.util.List;
import java.util.Scanner;

import com.newlecture.app.entity.Notice;
import com.newlecture.app.service.NoticeService;

public class NoticeConsole {

	// 출력을 구현하기 위해서는 데이터가 필요함
	// 그래서 데이터 서비스를 위한 NoticeService가 필요
	private NoticeService service;
	private int page;
	
	private String searchField; // 전역변수로 만드는 이유  inputSearchWord 에서 값을 받고 printNoticeList에서 사용되어야 하기 때문에
	private String searchWord; 
	
	
	public NoticeConsole() {
		service = new NoticeService();
		page = 1; // 기본값
		searchField = "TITLE"; // 입력 필드로 기본값을 TITLE를 넣는다. WRITER_ID를 넣거나 REGDATE 를 넣어도 됨 그냥 기본적으로 실행했을때 모든 게시글을 보여주기 위한 초기값임
		searchWord = "";
	}

	public void printNoticeList() {
		
		List<Notice> list = service.getList(page,searchField,searchWord);
		// 게시글 개수인 count 는 매번 불러와야 한다.
		// 출력하는 도중에도 게시글이 추가되거나 삭제될 수 있기 때문에

		int count = service.getCount(); // notice 의 갯수를 의미 (클래스가 NoticeConsole 이라서)
		int lastPage = count / 10; // 한페이지당 10개 글이 있음 그래서 전체카운트 count / 10 하면 맞을까??  ->  NO
		
		// 만약에 101 개라면?? 논리적으로 11페이지가 되어야 한다. 그런데 count/10 하면 10 이 된다.
		// 따라서 나머지가 있을경우에는 하나를 꼭 증가시켜야 한다.
		lastPage = count%10 == 0 ? lastPage : lastPage+1; 
		// 삼항연산자를 사용해서 조건을 검사한다.
		// 10으로 나눴을때 나머지가 10이면 게시글은 10 ,20 ,30... 페이지를 추가할 필요없다. 하지만 나머지가 존재할떄는 13,25,66 등이므로 lastPage 에 +1 을 해줘야 모든 데이터를 볼 수 있다.
		
		
		
		System.out.println("--------------------------");
		System.out.printf("<공지사항> 총 %d 게시글\n" , count);
		System.out.println("--------------------------");
		
		// 반복
		for(Notice n : list) {
		System.out.printf("%d. %s / %s / %s\n",n.getId(),n.getTitle(),n.getWriter_id(),n.getRegdate());
		}
		
		System.out.println("--------------------------");
		System.out.printf("                     %d/%d pages\n" , page,lastPage);
		
	}

	public int inputNoticeMenu() {
		Scanner sc = new Scanner(System.in);
		int menu = 0;
		
		System.out.print("1.상세조회 / 2.이전 / 3.다음 / 4.글쓰기 / 5.검색 / 6.종료 > ");
		String menu_ = sc.next();
		
		// 조건검사 사용
		try {
			menu = Integer.parseInt(menu_);

		}catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return menu;
	}

	public void movePrevList() {
		// TODO Auto-generated method stub
		if(page == 1) {
			System.out.println("=================");
			System.out.println("이전 페이지가 없습니다.");
			System.out.println("=================");
			return;
		}
		page--;
	}

	public void moveNextList() {
		// TODO Auto-generated method stub
		// next의 경우에는 마지막 페이지를 알 수 없음

		// 지금 lastPage 라는 변수가 printNoticeList()의 지역변수라서 사용이 불가능하다. 그러면 이 변수를 전역변수로 뺴야할까?
		// No 왜냐하면 로직 중간중간 사이 시간에서도 데이터가 삭제되거나 추가되거나 할 수 있기 때문이다.
		// 따라서 lastPage 값을 다시 구해야 더 정확한 로직이 되는것
		// 그래서 lastPage 구하는 로직을 다시 가져옴

		int count = service.getCount(); 
		int lastPage = count / 10; 
		lastPage = count%10 == 0 ? lastPage : lastPage+1; 
		
		if(page == lastPage) {
			System.out.println("=================");
			System.out.println("다음 페이지가 없습니다.");
			System.out.println("=================");
			return;
		}
		page++;
	}

	public void inputSearchWord() {
		Scanner sc = new Scanner(System.in);
		System.out.println("검색 범주(title/content/writerId)중에 하나를 입력하세요");
		System.out.println(" >> ");
		searchField = sc.nextLine();
		System.out.println("검색어 >> ");
		searchWord = sc.nextLine(); // nextLine() 하면 String 값을 받음

	}
}
