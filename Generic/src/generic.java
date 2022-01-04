
public class generic {

	// Generic이란??
	// 범용 컬렉션의 장점과 특화된 클래스의 장점을 모두 겸비한 탬플릿
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GList<Integer> list = new GList<>(); // --> <> 제너릭 구조
		list.add(3);
		list.add(5);
		
		int size = list.size();
		System.out.printf("size : $d \n" , size);
		
		list.add(7);
		int num = list.get(0);
		System.out.printf("num : %d \n" , num);
		
		
	}

}
