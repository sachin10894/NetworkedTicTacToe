import java.io.*;
import java.net.*;

public class MyServer{
	ServerSocket ss;
	Socket so;
	DataInputStream dis;
	DataOutputStream dos;
	int c[][] = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
	int s[][] = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
	int i,m,n ;

	public MyServer(){
		try{
			System.out.println("Server Started");
			ss = new ServerSocket(10);
			so=ss.accept();
			//System.out.println(s);
			System.out.println("Client Connected");
			dis = new DataInputStream(so.getInputStream());
			dos = new DataOutputStream(so.getOutputStream());

			perform();
		}catch (Exception e) {
			System.out.println(e);
		}
	}

	public void perform() throws IOException {

		String str,s1,w;
		int r;
		
		try {
			do{
				i = 0;
				 w= dis.readUTF();
				 //System.out.println(w);
				if(w.equals("false")){ 
					i++;
					r = (int)(9*Math.random());
					mark(r,2);
					//System.out.println(r+" "+i);
					dos.writeUTF(Integer.toString(r));
					dos.flush();
				}
				while (i<9) {
					//user turn
					i++;
					str = dis.readUTF();
					if (str.equals("Requested End Of Game")) {
						return;
					}
					//System.out.println(str+" "+i);
					mark(Integer.parseInt(str),1);
					if(check()!=4)
						break;
					//Comp's turn
					i++;
					w = Integer.toString(nxtStep());
					//System.out.println(w+" "+i);
					dos.writeUTF(w);
					dos.flush();
					if(check()!=4)
						break;
				} 
				w=dis.readUTF();
				//System.out.println(w);
			}while(w.equals("true"));
		}catch (Exception e) {
		System.out.println(e);
	}
	}


	public static void main(String[] args) {
		new MyServer();
	}

	public int nxtStep(){

		//first trick abolished
		if ((c[0][0]==2)&&(c[1][1]==1)&&(c[2][2]==1)&&(c[0][1]==0)&&(c[0][2]==0)&&(c[1][0]==0)&&(c[1][2]==0)&&(c[2][0]==0)&&(c[2][1]==0)) {
			c[0][2]=2;
			mark(2,2);return 2;
		}

		//if mid box is epmty for 2nd or 3rd step 

			if((i==2)||(i==3)){
				if(c[1][1]==0){c[1][1]=2;mark(4,2);return 4;}
			}

		//for comp's complete 2 step n 3rd winning step

			int z=-1,k=0;
			
			// horizontal
			for (m=0;m<c.length;m++) {
				z=-1;k=0;
				for (n=0;n<c[m].length;n++) {
					if(c[m][n]==2){k++;}
					else if (c[m][n]==0) {z=(m*c[m].length)+n;}
				}
				if((z!=-1)&&(k==2)){mark(z,2);return z;}
			}

			//for vertical
			for (n=0;n<c[0].length;n++) {
				z=-1;k=0;
				for (m=0;m<c.length;m++) {
					if(c[m][n]==2){k++;}
					else if (c[m][n]==0) {z=(m*c[m].length)+n;}
				}
				if((z!=-1)&&(k==2)){mark(z,2);return z;}
			}

			//for Diagonal1
			z=-1;k=0;
			for (m=0;m<c.length;m++) {
				if(c[m][m]==2){k++;}
				else if (c[m][m]==0) {z=(m*c[m].length)+m;}
			}
			if((z!=-1)&&(k==2)){mark(z,2);return z;}


			//for Diagonal2
			k=0;z=-1;
			for (m=0;m<c.length;m++) {
				if(c[m][c.length-m-1]==2){k++;}
				else if (c[m][c.length-m-1]==0) {z=(m*c[m].length)+c.length-m-1;}
			}
			if((z!=-1)&&(k==2)){mark(z,2);return z;}


		//for not letting user win after 2 successful step
			for (m=0;m<c.length;m++) {
				z=-1;k=0;
				for (n=0;n<c[m].length;n++) {
					if(c[m][n]==1){k++;}
					else if (c[m][n]==0) {z=(m*c[m].length)+n;}
				}
				if((z!=-1)&&(k==2)){mark(z,2);return z;}
			}

			//for vertical
			for (n=0;n<c[0].length;n++) {
				z=-1;k=0;
				for (m=0;m<c.length;m++) {
					if(c[m][n]==1){k++;}
					else if (c[m][n]==0) {z=(m*c[m].length)+n;}
				}
				if((z!=-1)&&(k==2)){mark(z,2);return z;}
			}

			//for Diagonal1
			k=0;z=-1;
			for (m=0;m<c.length;m++) {
				if(c[m][m]==1){k++;}
				else if (c[m][m]==0) {z=(m*c[m].length)+m;}
			}
			if((z!=-1)&&(k==2)){mark(z,2);return z;}


			//for Diagonal2
			k=0;z=-1;
			for (m=0;m<c.length;m++) {
				if(c[m][c.length-m-1]==1){k++;}
				else if (c[m][c.length-m-1]==0) {z=(m*c[m].length)+c.length-m-1;}
			}
			if((z!=-1)&&(k==2)){mark(z,2);return z;}	

			//second trick abolished
		Labl:
		if ((c[0][1]==1)&&(c[1][0]==1)&&(c[0][0]==0)) {
			c[0][0]=2;mark(0,2);return 0;
		}

		if ((c[0][1]==1)&&(c[1][2]==1)&&(c[0][2]==0)) {
			c[0][2]=2;mark(2,2);return 2;
		}

		if ((c[1][0]==1)&&(c[2][1]==1)&&(c[2][0]==0)) {
			c[2][0]=2;mark(6,2);return 6;
		} 

		if ((c[2][1]==1)&&(c[1][2]==1)&&(c[2][2]==0)) {
			c[2][2]=2;mark(8,2);return 8;
		}

		//labl finish

			//marking probable space by weight
			
			for (m=0;m<c.length;m++) {
				for (n=0;n<c.length;n++) {
					if(c[m][n]==1){s[0][n]--;s[1][n]--;s[2][n]--;s[m][0]--;s[m][1]--;s[m][2]--;}
					else if (c[m][n]==2) {s[0][n]++;s[1][n]++;s[2][n]++;s[m][0]++;s[m][1]++;s[m][2]++;}
				}
			}

			//marking already filled space
			for (m=0;m<c.length;m++) {
				for (n=0;n<c.length;n++) {
					if((c[m][n]==1)||(c[m][n]==2)) {s[m][n]=-10;}
				}
			}

			//getting the place with more weightage
			int o=-5;
			for (m=0;m<c.length;m++) {
				for (n=0;n<c.length;n++) {
					if (o<s[m][n]) {
						o=s[m][n];
						z=(m*c[m].length)+n;
					}
				}
			}
			mark(z,2);
			s= new int[][]{{0,0,0},{0,0,0},{0,0,0}};
			return z;
	}

	public int check() {

		try {
			if (((c[0][0]==1)&&(c[0][1]==1)&&(c[0][2]==1))||((c[1][0]==1)&&(c[1][1]==1)&&(c[1][2]==1))||((c[2][0]==1)&&(c[2][1]==1)&&(c[2][2]==1))||((c[0][0]==1)&&(c[1][1]==1)&&(c[2][2]==1))||((c[0][2]==1)&&(c[1][1]==1)&&(c[2][0]==1))||((c[0][0]==1)&&(c[1][0]==1)&&(c[2][0]==1))||((c[0][1]==1)&&(c[1][1]==1)&&(c[2][1]==1))||((c[0][2]==1)&&(c[1][2]==1)&&(c[2][2]==1))) {
				dos.writeUTF("Cross Wins");dos.flush();/*System.out.println("Cross Wins");*/c= new int[][]{{0,0,0},{0,0,0},{0,0,0}}; return 1;
			}else if (((c[0][0]==2)&&(c[0][1]==2)&&(c[0][2]==2))||((c[1][0]==2)&&(c[1][1]==2)&&(c[1][2]==2))||((c[2][0]==2)&&(c[2][1]==2)&&(c[2][2]==2))||((c[0][0]==2)&&(c[1][1]==2)&&(c[2][2]==2))||((c[0][2]==2)&&(c[1][1]==2)&&(c[2][0]==2))||((c[0][0]==2)&&(c[1][0]==2)&&(c[2][0]==2))||((c[0][1]==2)&&(c[1][1]==2)&&(c[2][1]==2))||((c[0][2]==2)&&(c[1][2]==2)&&(c[2][2]==2))) {
				dos.writeUTF("Zero Wins");dos.flush();/*System.out.println("Zero Wins");*/c= new int[][]{{0,0,0},{0,0,0},{0,0,0}};return 2 ;
			}else if (i==9) {
				dos.writeUTF("Nobody Wins");dos.flush();/*System.out.println("Nobody Wins");*/c= new int[][]{{0,0,0},{0,0,0},{0,0,0}};return 3 ;
			}
			dos.writeUTF("null");
			//System.out.println("Nobody Wins");
			dos.flush();
		}catch (Exception e) {
			System.out.println(e);
		}
		return 4;	
	}

	public void mark(int z,int y) {
		
		switch (z) {
			case 0:
				c[0][0]=y;
			break;

			case 1:
				c[0][1]=y;
			break;

			case 2:
				c[0][2]=y;
			break;

			case 3:
				c[1][0]=y;
			break;

			case 4:
				c[1][1]=y;
			break;
				
			case 5:
				c[1][2]=y;
			break;

			case 6:
				c[2][0]=y;
			break;

			case 7:
				c[2][1]=y;
			break;

			case 8:
				c[2][2]=y;
			break;

			default:	
		}
	}	
}