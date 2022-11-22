import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import java.io.*;
import java.net.*;

public class TicTacTo2 extends Application implements EventHandler <MouseEvent> {

	Label lbl1,lbl2;
	Image i;
	boolean b;
	int c[][] = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
	Image i1= new Image("b.png");
	Image i2= new Image("c.png");
	Image i3 = new Image("z.png");
	TilePane tp;
	VBox v;
	BorderPane bp;
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	Scene scn;
	Stage st;
	int m,n;
	String str;

	ImageView iv[][] = new ImageView[][]{{new ImageView(i1),new ImageView(i1),new ImageView(i1)},{new ImageView(i1),new ImageView(i1),new ImageView(i1)},{new ImageView(i1),new ImageView(i1),new ImageView(i1)}};
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {

		try {
			s=new Socket("LocalHost",10);
		}catch (Exception e) {
			System.out.println("Server is not active");
			System.exit(0) ;
		}

		try{
			//System.out.println(s);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());

		}catch (Exception e) {
			System.out.println(e);
		}

	st = primaryStage;
	lbl1 = new Label("TicTacToe  - Win if you can");
	lbl1.setId("gm");
	lbl2 = new Label("Enjoy");
	
	primaryStage.getIcons().add(new Image("t.png"));
		
		tp = new TilePane();
		tp.setPrefColumns(3);
		tp.setHgap(30);
		tp.setVgap(30);
		tp.setPrefTileWidth(150);
		tp.setPrefTileHeight(150);
		
		for (m=0;m<iv.length;m++) {
			for (n=0;n<iv[m].length;n++) {
				iv[m][n].setOnMousePressed(this);
				tp.getChildren().add(iv[m][n]);
			}
		}
		
		v =new VBox();
		v.getChildren().add(lbl1);
		v.getChildren().add(lbl2);
		v.setAlignment(Pos.CENTER);
		bp =new BorderPane();
		bp.setBottom(tp);
		bp.setTop(v);
		bp.setPadding(new Insets(50));
		bp.setAlignment(tp,Pos.CENTER);

		scn = new Scene(bp,610,700);
		scn.getStylesheets().add("Simple.css");
		primaryStage.setScene(scn);
		primaryStage.setResizable(false);
		primaryStage.setTitle("TIC TAC TOE");
		primaryStage.setOnCloseRequest(e -> 
			{closeClick();}
		);
		primaryStage.show();

		whoWillStart();		

	}

	public void closeClick() {
		boolean confirm;
		confirm = ConfirmationBox.show("Are you sure, you wanna quit..?","Yes", "No");
		if(confirm) {
			try {
				dout.writeUTF("Requested End Of Game");
				dout.flush();
			}catch (Exception e){System.out.println(e);}
			st.close();
			//System.exit(0);
		}
	}

	public void whoWillStart () {
		String p;
		boolean confirm = false;
	
		confirm = ConfirmationBox.show("Do U wanna play first move ?","Yes", "No");
		lbl2.setText("Best of Luck");
		//System.out.println(confirm);
		try {
			dout.writeUTF(Boolean.toString(confirm));
			dout.flush();
			if(!confirm){
				p = din.readUTF();
				//System.out.println(p);
				mark(Integer.parseInt(p),2,i3);
				//j++;
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}

	public void mark(int z,int y,Image img) {

		ImageView im = null;
		switch (z) {

			case 0:
				im = iv[0][0] ;
				c[0][0]=y;
			break;

			case 1:
				im = iv[0][1];
				c[0][1]=y;
			break;

			case 2:
				im = iv[0][2];
				c[0][2]=y;
			break;

			case 3:
				im = iv[1][0];
				c[1][0]=y;
			break;

			case 4:
				im = iv[1][1];
				c[1][1]=y;
			break;
				
			case 5:
				im = iv[1][2];
				c[1][2]=y;
			break;

			case 6:
				im = iv[2][0];
				c[2][0]=y;
			break;

			case 7:
				im = iv[2][1];
				c[2][1]=y;
			break;

			case 8:
				im = iv[2][2];
				c[2][2]=y;
			break;

			default:
					
		}
		im.setImage(img);
	}

	@Override public void handle(MouseEvent e) {
		int a;
		
		try {
			for (m=0;m<iv.length; m++) {
				for (n=0;n<iv[m].length;n++) {
					if (e.getSource()==iv[m][n]) {
						if (iv[m][n].getImage()!=i1) 
							return;
						mark((m*c[m].length+n),1,i2);
						dout.writeUTF(Integer.toString(m*c[m].length+n));
						//System.out.println(Integer.toString(m*c[m].length+n));
						dout.flush();
					}
				}
			}
			str = din.readUTF();
			//System.out.println(str);
			if (str.equals("null")) {
				mark(Integer.parseInt(din.readUTF()),2,i3);
			}
			else {
				gameEnd(str);
				return ;

			}

			str = din.readUTF();
			//System.out.println(str);
			if (!str.equals("null")) {
				gameEnd(str);
			}

		}catch (Exception e1) {
			System.out.println(e1);
		}		
	}

	public void gameEnd(String a) {
		boolean confirm = false;
		confirm = ConfirmationBox.show(a,"New Game", "Exit");
		try {
			//System.out.println(confirm);
			dout.writeUTF(Boolean.toString(confirm));
			dout.flush();
		}catch (Exception e) {
			System.out.println(e);
		}
		if (confirm) {
			c = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
			lbl2.setText("Go On Again");

			for (m=0;m<iv.length;m++) {
				for (n=0;n<iv[m].length;n++) {
					iv[m][n].setImage(i1);
				}
			}
			
			whoWillStart();
		}

		else{
			st.close();
		}
	}
}