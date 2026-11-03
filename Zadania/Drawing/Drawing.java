
public class Drawing implements SimpleDrawing{
	public int[][] my_canvas;
	public int my_x, my_y, my_size;
	boolean canvas_exists = false;
	@Override
	
	/*
	 * Metoda tworzy pobiera obiekt Geometry i tworzy plotno
	 */
	public void setCanvasGeometry(Geometry input) {	
		my_x = input.getInitialFirstCoordinate();
		my_y = input.getInitialSecondCoordinate();
		my_size = input.getSize();
		my_canvas = new int[my_size][my_size];
		canvas_exists = true;
	}

	@Override
	/*
	 * Metoda rysujaca obiekt Segment na plotnie
	 */
	public void draw(Segment segment) {
		int direction = segment.getDirection();
		int len = segment.getLength();
		int color = segment.getColor();
		
		switch (direction)
		{
			case 1: //zwiekszanie w osi x ->
				if (my_x+len-1 >= my_size) len = my_size - my_x;
				for (int i=0;i<len;i++)
				{
					my_canvas[my_x][my_y] = color;
					my_x++;
				}
				my_x--;
				break;
			case 2: //zwiekszanie w osi y ^
				if (my_y+len-1 >= my_size) len = my_size - my_y;
				for (int i=0;i<len;i++)
				{
					my_canvas[my_x][my_y] = color;
					my_y++;
				}
				my_y--;
				break;
			case -1://zmniejszanie w w osi x <-
				if (my_x-len+1 < 0) len = my_x+1;
				for (int i=0;i<len;i++)
				{
					my_canvas[my_x][my_y] = color;
					my_x--;
				}
				my_x++;
				break;
			case -2://zmniejszanie w osi y \/
				if (my_y-len+1 < 0) len = my_y+1;
				for (int i=0;i<len;i++)
				{
					my_canvas[my_x][my_y] = color;
					my_y--;
				}
				my_y++;
				break;
			default:
				break;
		}
	}

	@Override
	/*
	 * Metoda zwracajaca plotno lub null, jesli nie istnieje plotno
	 */
	public int[][] getPainting() {
		if (canvas_exists) return my_canvas;
		return null;
	}

	@Override
	/*
	 * Metoda czyszczaca plotno
	 */
	public void clear() {
		for (int i=0;i<my_size;i++)
		{
			for (int j=0;j<my_size;j++)
				my_canvas[i][j] = 0;
		}
		
	}

}
