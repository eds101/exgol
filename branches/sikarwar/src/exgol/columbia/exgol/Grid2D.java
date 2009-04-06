package columbia.exgol;

public class Grid2D {
	private int x;
	private int y;

	public Grid2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getDimentions() {
		return 2;
	}

	public int get(String axis) {
		if (axis.equalsIgnoreCase("x"))
			return x;
		if (axis.equalsIgnoreCase("y"))
			return y;
		throw new RuntimeException("Unknow axis: " + axis);
	}
}
