import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//画布，总的活动范围
public class Yard extends Frame {
	
	Boolean flag = true;  //控制游戏是否继续
	private Boolean pause = false;
	int score = 0;  //所得分数
	
	//PaintThrea paintThread = new PaintThrea();
	public static final int ROWS = 20;
	public static final int COLS = 20;
	public static final int BLOCK_SIZE = 15;
	Snake s = new Snake(this);
	Eggs egg = new Eggs();
	
	Image offScreenImage = null;
	
	public static void main(String[] args){
		new Yard().launch();
	}
	
	void launch() {
		setVisible(true);
		setBounds(300,80,BLOCK_SIZE*COLS,BLOCK_SIZE*ROWS);
		addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
				 setVisible(false);
				 System.exit(0);
			 }
		});
		addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
	}
	
	//撞到边界后停止
	public void stop() {
		flag = false;
		//System.out.println("GAME OVER!");
		
	}

	//暂停和结束暂停
	public void pause() {
		if(pause == false) pause = true;
		else pause = false;
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, COLS*BLOCK_SIZE, ROWS*BLOCK_SIZE);
		/*//画横线
		g.setColor(Color.black);
		for(int i=1;i<ROWS;i++) {
			g.drawLine(0, i*BLOCK_SIZE, COLS*BLOCK_SIZE, i*BLOCK_SIZE);
		}
		//画竖线
		for(int i=1;i<COLS;i++) {
			g.drawLine(i*BLOCK_SIZE,0,i*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		}*/
		
		//显示分数
		g.setColor(Color.yellow);
		g.drawString("Score:"+score, 10, 60);

		g.setColor(c);
		//画蛇
		s.draw(g);
		//画豆子
		egg.drawEggs(g);
		
		//游戏结束后显示游戏结束
		if(flag == false) {
			g.setColor(Color.yellow);
			g.setFont(new Font("宋体",Font.BOLD,30));
			g.drawString("GAME OVER:"+score, 50, 150);
			g.setFont(new Font("宋体",Font.BOLD,15));
			g.drawString("重新开始请按F2", 80, 180);
		}
	}
	
	//TODO 双缓冲，解决闪烁问题？？？？？？？？？？？
	@Override
	public void update(Graphics g) {
		/*如果offScreenImage为null，则创建一个和屏幕上的绘图区域大小一样的缓冲图象，再取得offScreenImage的
		 * Graphics类型 的对象的引用，并将其赋值给gOff，然后对gOff这个内存中的后台图象进行绘制操作，
		 * 完成后将offScreenImage直接绘制到屏幕上*/
		if(offScreenImage == null) {
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0,  null);
	}
	
	//起线程，让蛇动起来
	class PaintThread implements Runnable {
		
		@Override
		public void run() {
			while(flag) {
				if(pause) continue;
				else repaint();
				try {
					Thread.sleep(150);  //可以根据休眠的时长，调整蛇运动的快慢
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//键盘事件监听
	class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
		}
	}
}
