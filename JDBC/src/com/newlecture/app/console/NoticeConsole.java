package com.newlecture.app.console;

import java.util.List;
import java.util.Scanner;

import com.newlecture.app.entity.Notice;
import com.newlecture.app.service.NoticeService;

public class NoticeConsole {

	// ����� �����ϱ� ���ؼ��� �����Ͱ� �ʿ���
	// �׷��� ������ ���񽺸� ���� NoticeService�� �ʿ�
	private NoticeService service;
	private int page;
	
	private String searchField; // ���������� ����� ����  inputSearchWord ���� ���� �ް� printNoticeList���� ���Ǿ�� �ϱ� ������
	private String searchWord; 
	
	
	public NoticeConsole() {
		service = new NoticeService();
		page = 1; // �⺻��
		searchField = "TITLE"; // �Է� �ʵ�� �⺻���� TITLE�� �ִ´�. WRITER_ID�� �ְų� REGDATE �� �־ �� �׳� �⺻������ ���������� ��� �Խñ��� �����ֱ� ���� �ʱⰪ��
		searchWord = "";
	}

	public void printNoticeList() {
		
		List<Notice> list = service.getList(page,searchField,searchWord);
		// �Խñ� ������ count �� �Ź� �ҷ��;� �Ѵ�.
		// ����ϴ� ���߿��� �Խñ��� �߰��ǰų� ������ �� �ֱ� ������

		int count = service.getCount(); // notice �� ������ �ǹ� (Ŭ������ NoticeConsole �̶�)
		int lastPage = count / 10; // ���������� 10�� ���� ���� �׷��� ��üī��Ʈ count / 10 �ϸ� ������??  ->  NO
		
		// ���࿡ 101 �����?? �������� 11�������� �Ǿ�� �Ѵ�. �׷��� count/10 �ϸ� 10 �� �ȴ�.
		// ���� �������� ������쿡�� �ϳ��� �� �������Ѿ� �Ѵ�.
		lastPage = count%10 == 0 ? lastPage : lastPage+1; 
		// ���׿����ڸ� ����ؼ� ������ �˻��Ѵ�.
		// 10���� �������� �������� 10�̸� �Խñ��� 10 ,20 ,30... �������� �߰��� �ʿ����. ������ �������� �����ҋ��� 13,25,66 ���̹Ƿ� lastPage �� +1 �� ����� ��� �����͸� �� �� �ִ�.
		
		
		
		System.out.println("--------------------------");
		System.out.printf("<��������> �� %d �Խñ�\n" , count);
		System.out.println("--------------------------");
		
		// �ݺ�
		for(Notice n : list) {
		System.out.printf("%d. %s / %s / %s\n",n.getId(),n.getTitle(),n.getWriter_id(),n.getRegdate());
		}
		
		System.out.println("--------------------------");
		System.out.printf("                     %d/%d pages\n" , page,lastPage);
		
	}

	public int inputNoticeMenu() {
		Scanner sc = new Scanner(System.in);
		int menu = 0;
		
		System.out.print("1.����ȸ / 2.���� / 3.���� / 4.�۾��� / 5.�˻� / 6.���� > ");
		String menu_ = sc.next();
		
		// ���ǰ˻� ���
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
			System.out.println("���� �������� �����ϴ�.");
			System.out.println("=================");
			return;
		}
		page--;
	}

	public void moveNextList() {
		// TODO Auto-generated method stub
		// next�� ��쿡�� ������ �������� �� �� ����

		// ���� lastPage ��� ������ printNoticeList()�� ���������� ����� �Ұ����ϴ�. �׷��� �� ������ ���������� �����ұ�?
		// No �ֳ��ϸ� ���� �߰��߰� ���� �ð������� �����Ͱ� �����ǰų� �߰��ǰų� �� �� �ֱ� �����̴�.
		// ���� lastPage ���� �ٽ� ���ؾ� �� ��Ȯ�� ������ �Ǵ°�
		// �׷��� lastPage ���ϴ� ������ �ٽ� ������

		int count = service.getCount(); 
		int lastPage = count / 10; 
		lastPage = count%10 == 0 ? lastPage : lastPage+1; 
		
		if(page == lastPage) {
			System.out.println("=================");
			System.out.println("���� �������� �����ϴ�.");
			System.out.println("=================");
			return;
		}
		page++;
	}

	public void inputSearchWord() {
		Scanner sc = new Scanner(System.in);
		System.out.println("�˻� ����(title/content/writerId)�߿� �ϳ��� �Է��ϼ���");
		System.out.println(" >> ");
		searchField = sc.nextLine();
		System.out.println("�˻��� >> ");
		searchWord = sc.nextLine(); // nextLine() �ϸ� String ���� ����

	}
}
