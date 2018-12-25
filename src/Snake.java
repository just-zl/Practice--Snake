import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Snake {
	
	private Node head = null;
	private Node tail = null;
	private int size = 0;
	Node n = new Node(2,Yard.COLS,Direction.LEFT);
	private Yard y;
	Snake(Yard y){
		head = n;
		tail = n;
		size = 1;
		this.y = y;
	}
	
	//��ͷ������
	public void addToHead() {
		Node node = null;
		switch(head.dir) {
		case LEFT :
			node = new Node(head.row, head.col - 1, head.dir);
			break;
		case UP :
			node = new Node(head.row - 1, head.col, head.dir);
			break;
		case RIGHT :
			node = new Node(head.row, head.col + 1, head.dir);
			break;
		case DOWN :
			node = new Node(head.row + 1, head.col, head.dir);
			break;
		}
		node.next = head;
		head.pre = node;
		head = node;
		size ++;
	}
	
	//��β������
	public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case LEFT :
			node = new Node(tail.row, tail.col + 1, tail.dir);
			break;
		case UP :
			node = new Node(tail.row + 1, tail.col, tail.dir);
			break;
		case RIGHT :
			node = new Node(tail.row, tail.col - 1, tail.dir);
			break;
		case DOWN :
			node = new Node(tail.row - 1, tail.col, tail.dir);
			break;
		}
		tail.next = node;
		node.pre = tail;
		tail = node;
		size ++;
	}
	
	//�������ÿһ��
	private class Node {
		int width = Yard.BLOCK_SIZE;
		int height = Yard.BLOCK_SIZE;
		int col,row;  //����
		Direction dir = Direction.LEFT;
		Node next = null;
		Node pre = null;
		
		//���췽��
		Node(int row,int col, Direction dir) {
			this.col = col;
			this.row = row;
			this.dir = dir;
		}
		
		void draw(Graphics g) { 
			Color c = g.getColor();
			g.setColor(Color.black);
			g.fillRect(Yard.BLOCK_SIZE*col, Yard.BLOCK_SIZE*row, width, height);
			g.setColor(c);
		}
	}
	
	//����������
	void draw(Graphics g) {
		if(size <= 0) return;
		move();
		for(Node n=head;n!=null;n=n.next) {
			n.draw(g);  //����Node��draw()����
		}
	}
	
	//���߸��ݼ����û��ķ����˶�
	private void move() {
		addToHead();  //ʵ���Ͽ���ֻ���ߵ�β�����Ϊ��ͷ����ɾ��β������
		deleteFromTail();
		eat();  //�Զ���
		checkDead();  //����Ƿ�ײ���߽������
	}

	//�Զ��ӣ��������µĶ���
	public void eat() {
		while((head.row == y.egg.row) && (head.col == y.egg.col)) {
			y.s.addToHead();
			y.score = y.score +5;
			y.egg = new Eggs();
		}
	}
	
	//����Ƿ�ײ���߽������
	private void checkDead() {
		//ײ���߽�
		if(head.row<2 ||head.row > Yard.ROWS || head.col < 0 || head.col > Yard.COLS) {
			y.stop();
		}
		//ײ������
		for(Node n = head.next; n != null; n= n.next) {
			if((head.row == n.row) && (head.col == n.col)) {
				y.stop();
			}
		}
	}

	//ɾ��β��
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.pre;
		tail.next = null;
	}

	//���ݼ������ļ����¼���������Ӧ�ķ�Ӧ
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_UP:
			if(head.dir != Direction.DOWN)
				head.dir = Direction.UP;
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir != Direction.UP)
				head.dir = Direction.DOWN;
			break;
		case KeyEvent.VK_LEFT:
			if(head.dir != Direction.RIGHT)
				head.dir = Direction.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir != Direction.LEFT)
				head.dir = Direction.RIGHT;
			break;
		case KeyEvent.VK_F2:
			new Yard().launch();
			break;
		case KeyEvent.VK_SPACE:
			y.pause();
			break;
		default:
			break;
		}
	}

}
