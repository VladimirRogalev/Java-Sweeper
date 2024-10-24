package sweeper;

public class Game {

	private Bomb bomb;
	private Flag flag;
	private GameState state;

	public GameState getState() {
		return state;
	}

	public Game(int cols, int rows, int bombs) {
		Ranges.setSize(new Coord(cols, rows));
		bomb = new Bomb(bombs);
		flag = new Flag();
	}

	public void start() {
		bomb.start();
		flag.start();
		state = GameState.PLAYED;
	}

	public Box getBox(Coord coord) {
		if (flag.get(coord) == Box.OPENED)
			return bomb.get(coord);
		else
			return flag.get(coord);
	}

	public void pressLeftButton(Coord coord) {
		if (gameOver())
			return;
		openBox(coord);
		checkWinner();
//	flag.setOpenedToBox(coord);

	}

	private void checkWinner() {
		if (state == GameState.PLAYED)
			if (flag.getCountOfClosedBoxes() == bomb.getTotaslBombs())
				state = GameState.WINNER;
	}

	private void openBox(Coord coord) {
		switch (flag.get(coord)) {
		case OPENED:
			setOpenedToClosedBoxesAroundNumber(coord);
			return;
		case FLAGED:
			return;
		case CLOSED:
			switch (bomb.get(coord)) {
			case ZERO:
				openBoxesAround(coord);
				return;
			case BOMB:
				openBombs(coord);
				return;
			default:
				flag.setOpenedToBox(coord);
				return;

			}

		}

	}

	private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
		if (bomb.get(coord) != Box.BOMB)
			if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
				for (Coord around : Ranges.getCoordsAround(coord))
					if (flag.get(around) == Box.CLOSED)
						openBox(around);

	}

	private void openBombs(Coord boombed) {
		state = GameState.BOMBED;
		flag.setBombedToCord(boombed);
		for (Coord coord : Ranges.getAllCoords())
			if (bomb.get(coord) == Box.BOMB)
				flag.setOpenedToClosedBomBox(coord);
			else
				flag.setNobombToFlegedSafeBox(coord);

	}

	private void openBoxesAround(Coord coord) {
		flag.setOpenedToBox(coord);
		for (Coord around : Ranges.getCoordsAround(coord))
			openBox(around);

	}

	public void pressRightButton(Coord coord) {
		if (gameOver())
			return;
		flag.toogleFlagedToBox(coord);

	}

	private boolean gameOver() {
		if (state == GameState.PLAYED)
			return false;
		start();
		return true;
	}

}
