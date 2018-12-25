import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//�������ܵĻ��Χ
public class Yard extends Frame {
	
	Boolean flag = true;  //������Ϸ�Ƿ����
	private Boolean pause = false;
	int score = 0;  //���÷���
	
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
	
	//ײ���߽��ֹͣ
	public void stop() {
		flag = false;
		//System.out.println("GAME OVER!");
		
	}

	//��ͣ�ͽ�����ͣ
	public void pause() {
		if(pause == false) pause = true;
		else pause = false;
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, COLS*BLOCK_SIZE, ROWS*BLOCK_SIZE);
		/*//������
		g.setColor(Color.black);
		for(int i=1;i<ROWS;i++) {
			g.drawLine(0, i*BLOCK_SIZE, COLS*BLOCK_SIZE, i*BLOCK_SIZE);
		}
		//������
		for(int i=1;i<COLS;i++) {
			g.drawLine(i*BLOCK_SIZE,0,i*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		}*/
		
		//��ʾ����
		g.setColor(Color.yellow);
		g.drawString("Score:"+score, 10, 60);

		g.setColor(c);
		//����
		s.draw(g);
		//������
		egg.drawEggs(g);
		
		//��Ϸ��������ʾ��Ϸ����
		if(flag == false) {
			g.setColor(Color.yellow);
			g.setFont(new Font("����",Font.BOLD,30));
			g.drawString("GAME OVER:"+score, 50, 150);
			g.setFont(new Font("����",Font.BOLD,15));
			g.drawString("���¿�ʼ�밴F2", 80, 180);
		}
	}
	
	//TODO ˫���壬�����˸���⣿��������������������
	@Override
	public void update(Graphics g) {
		/*���offScreenImageΪnull���򴴽�һ������Ļ�ϵĻ�ͼ�����Сһ���Ļ���ͼ����ȡ��offScreenImage��
		 * Graphics���� �Ķ�������ã������丳ֵ��gOff��Ȼ���gOff����ڴ��еĺ�̨ͼ����л��Ʋ�����
		 * ��ɺ�offScreenImageֱ�ӻ��Ƶ���Ļ��*/
		if(offScreenImage == null) {
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0,  null);
	}
	
	//���̣߳����߶�����
	class PaintThread implements Runnable {
		
		@Override
		public void run() {
			while(flag) {
				if(pause) continue;
				else repaint();
				try {
					Thread.sleep(150);  //���Ը������ߵ�ʱ�����������˶��Ŀ���
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//�����¼�����
	class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
		}
	}
}
