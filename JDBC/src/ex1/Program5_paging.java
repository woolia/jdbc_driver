package ex1;

import com.newlecture.app.console.NoticeConsole;

public class Program5_paging {

	public static void main(String[] args) {
		
		NoticeConsole console = new NoticeConsole();
		
		EXIT: // �󺧹� �ش� break�� ���⼭ ���� 
		while(true) {
			console.printNoticeList(); // ���
			int menu = console.inputNoticeMenu(); // �Է�
			
			switch(menu) {
				case 1: //����ȸ
					break;
				case 2: //����
					console.movePrevList();
					break;
				case 3: //����
					console.moveNextList();
					break;
				case 4: //�۾���
					break;
				case 5: //�˻�
					console.inputSearchWord();
					break;
				case 6: //����
					System.out.println("Bye");
					break EXIT;
					// break �󺧹� ���
				default:
					System.out.println("���ڴ� 1~4������ �Է°���");
					break;
			}
		}
	}
}
