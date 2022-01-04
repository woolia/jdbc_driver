
public class GList<T> {
	// <T> => Generic 

	private Object[] nums; // 어떤 객체도 올수 있도록 Object형
	private int current;
	
	// 데이터 형은 Object를 기본으로한다.
	// 그런데 들어오는 파라미터 값을 T로 하면 Generic 구조가 됨

	public GList() {
		this.nums = new Object[3];
		this.current = 0;
	}
	
	public void add(T num) { // Object num -> T num
		nums[current] =  num;
		current++;
	}
	
	public void clear() {
		current = 0;
	}
	
	public int size() {
		return current;
	}
	
	public T get(int index) { // 리턴값도 T로 바꿈
		if(current <= index)
			throw new IndexOutOfBoundsException();
	
	//return nums[index];  -> return 할때 (T) 로 casting 해준다. 
	return (T)nums[index]; 
	}
}
