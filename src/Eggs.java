import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

//�߳Ե�ʳ��С����
public class Eggs {

	int width = Yard.BLOCK_SIZE;
	int height = Yard.BLOCK_SIZE;
	int row,col;  //���ӵ�����
	private Color color = Color.GREEN;
	static Random r = new Random();
	Eggs(int row, int col) {
		this.row = row;  //Ӧ����������ɶ��ӵ�λ�õ�
		this.col = col;
	}
	Eggs(){
		this(r.nextInt(Yard.ROWS-2)+2, r.nextInt(Yard.COLS));
	}
	
	//��������
	public void drawEggs(Graphics g) {
		
		Color c = g.getColor();
		
		g.setColor(color);
		g.fillOval(col*Yard.BLOCK_SIZE, row*Yard.BLOCK_SIZE, width, height);
	
		g.setColor(c);
		if(color == Color.GREEN) color = Color.RED;
		else color = Color.RED;
	}
	
}
