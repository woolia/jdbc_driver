
public class generic {

	// Generic�̶�??
	// ���� �÷����� ������ Ưȭ�� Ŭ������ ������ ��� ����� ���ø�
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GList<Integer> list = new GList<>(); // --> <> ���ʸ� ����
		list.add(3);
		list.add(5);
		
		int size = list.size();
		System.out.printf("size : $d \n" , size);
		
		list.add(7);
		int num = list.get(0);
		System.out.printf("num : %d \n" , num);
		
		
	}

}
