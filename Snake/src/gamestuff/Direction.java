package gamestuff;

public enum Direction {
	north, south, east, west;

	public Direction opposite() {
		if (this == north)
			return south;
		if (this == south)
			return north;
		if (this == east)
			return west;
		return east;
	}

	public int getRowValue() {
		if (this == north)
			return -1;
		if (this == south)
			return 1;
		return 0;
	}

	public int getColumnValue() {
		if (this == west)
			return -1;
		if (this == east)
			return 1;
		return 0;
	}
}
