
public enum Direction {
	LEFT, RIGHT, UP, DOWN;
	public boolean notOpposite(Direction direction) {
		if(this==LEFT && direction==RIGHT)
			return false;
		if(this==RIGHT && direction==LEFT)
			return false;
		if(this==UP && direction==DOWN)
			return false;
		if(this==DOWN && direction==UP)
			return false;
		return true;
	}
}
