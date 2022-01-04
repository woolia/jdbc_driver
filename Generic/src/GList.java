
public class GList<T> {
	// <T> => Generic 

	private Object[] nums; // � ��ü�� �ü� �ֵ��� Object��
	private int current;
	
	// ������ ���� Object�� �⺻�����Ѵ�.
	// �׷��� ������ �Ķ���� ���� T�� �ϸ� Generic ������ ��

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
	
	public T get(int index) { // ���ϰ��� T�� �ٲ�
		if(current <= index)
			throw new IndexOutOfBoundsException();
	
	//return nums[index];  -> return �Ҷ� (T) �� casting ���ش�. 
	return (T)nums[index]; 
	}
}
