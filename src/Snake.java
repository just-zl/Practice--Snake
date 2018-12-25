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
	
	//从头部增加
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
	
	//从尾部增加
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
	
	//蛇身体的每一节
	private class Node {
		int width = Yard.BLOCK_SIZE;
		int height = Yard.BLOCK_SIZE;
		int col,row;  //坐标
		Direction dir = Direction.LEFT;
		Node next = null;
		Node pre = null;
		
		//构造方法
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
	
	//画出整条蛇
	void draw(Graphics g) {
		if(size <= 0) return;
		move();
		for(Node n=head;n!=null;n=n.next) {
			n.draw(g);  //调用Node的draw()方法
		}
	}
	
	//让蛇根据键盘敲击的方向运动
	private void move() {
		addToHead();  //实际上可以只将蛇的尾部添加为蛇头，再删除尾部即可
		deleteFromTail();
		eat();  //吃豆子
		checkDead();  //检查是否撞到边界或自身
	}

	//吃豆子，并产生新的豆子
	public void eat() {
		while((head.row == y.egg.row) && (head.col == y.egg.col)) {
			y.s.addToHead();
			y.score = y.score +5;
			y.egg = new Eggs();
		}
	}
	
	//检查是否撞到边界或自身
	private void checkDead() {
		//撞到边界
		if(head.row<2 ||head.row > Yard.ROWS || head.col < 0 || head.col > Yard.COLS) {
			y.stop();
		}
		//撞到自身
		for(Node n = head.next; n != null; n= n.next) {
			if((head.row == n.row) && (head.col == n.col)) {
				y.stop();
			}
		}
	}

	//删除尾部
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.pre;
		tail.next = null;
	}

	//根据监听到的键盘事件，做出相应的反应
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
