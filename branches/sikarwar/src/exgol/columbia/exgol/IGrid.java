package columbia.exgol;

public interface IGrid
{
	int getDimensions();
	int get(String axis);
	void set(String axis, int length);
}
