import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

//蛇吃的食，小豆子
public class Eggs {

	int width = Yard.BLOCK_SIZE;
	int height = Yard.BLOCK_SIZE;
	int row,col;  //豆子的坐标
	private Color color = Color.GREEN;
	static Random r = new Random();
	Eggs(int row, int col) {
		this.row = row;  //应该是随机生成豆子的位置的
		this.col = col;
	}
	Eggs(){
		this(r.nextInt(Yard.ROWS-2)+2, r.nextInt(Yard.COLS));
	}
	
	//画出豆子
	public void drawEggs(Graphics g) {
		
		Color c = g.getColor();
		
		g.setColor(color);
		g.fillOval(col*Yard.BLOCK_SIZE, row*Yard.BLOCK_SIZE, width, height);
	
		g.setColor(c);
		if(color == Color.GREEN) color = Color.RED;
		else color = Color.RED;
	}
	
}
