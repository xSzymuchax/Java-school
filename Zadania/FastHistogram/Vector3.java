import java.util.Vector;

public class Vector3 implements Vector2 {
	Vector<Integer> v = new Vector<>();
	
	@Override
	public int getValue(int idx) {
		return v.get(idx);
	}

	@Override
	public int getSize() {
		return v.size();
	}

	public void generate(int n) {
		for (int i=0;i<n;i++) {
			v.add(i%3);
		}
	}
	
	public void show() {
		for (int i : v)
			System.out.println(i);
	}
}
