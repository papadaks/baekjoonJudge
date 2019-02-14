import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int SHARK_LEVEL=2, SHARK_X, SHARK_Y, FISH_X =100, FISH_Y =100, EAT_COUNT=0, N, TIMER=100, ANSWER=0;
	static boolean hasFood = false; 
	static int[][] map, visited;
	static int[] X_OFF= {1,-1,0,0}, Y_OFF={0,0,1,-1};
	
	static Queue<Fish> queue = new LinkedList<Fish>();
	public static void main(String args[]) {
	  Scanner sc = new Scanner(System.in);
	  
	  N = sc.nextInt();
	  map = new int[N][N];
	  visited = new int[N][N];
	  
	  for(int y=0;y<N;y++){
		  for(int x=0; x<N;x++){
			  map[y][x] = sc.nextInt();
			  if(map[y][x] ==9){
				  SHARK_X = x; SHARK_Y = y; map[y][x] =0;
			  }
		  }
	  }	  	  
	  
	  while(true){		  
		  findFood(SHARK_X,SHARK_Y);
		  if(!hasFood) break;
		  visited = new int[N][N];
		  EAT_COUNT++;
		  if(EAT_COUNT == SHARK_LEVEL) {
			  SHARK_LEVEL++; EAT_COUNT =0;
		  }
		  ANSWER +=TIMER;		  
		  hasFood = false; 
		  SHARK_X=FISH_X;SHARK_Y=FISH_Y;
		  map[FISH_Y][FISH_X]=0;		  
		  FISH_X=100;FISH_Y=100;TIMER=100; 
		  queue = new LinkedList<Fish>();
		  
	  }
	  	  	  
	  System.out.println(ANSWER);	  
	}
	
	static void findFood(int startX, int startY){		
		queue.add(new Fish(startX,startY,0,0));
		visited[startY][startX] = 1;
		int timer=0;
		
		while(!queue.isEmpty()){		
			Fish current = queue.poll();			
			timer=current.time+1;
			if(current.size < SHARK_LEVEL && current.size !=0 && TIMER >= current.time){		
				hasFood = true; 
				if(TIMER == current.time){
					if(FISH_Y > current.y){
						FISH_Y = current.y; FISH_X = current.x; TIMER = current.time;								
					}else{
						if(FISH_Y == current.y && FISH_X > current.x){
							FISH_Y = current.y; FISH_X = current.x; TIMER = current.time;	
						}
					}	
				}else{
					FISH_Y = current.y; FISH_X = current.x; TIMER = current.time;	
				}
								
				continue;
			}
			
			if(!hasFood){
				for(int dir=0;dir<4;dir++){
					int newX = current.x+ X_OFF[dir];
					int newY = current.y+ Y_OFF[dir];
					
					if(newX >=0 && newX < N && newY >=0 && newY < N 
					    && visited[newY][newX] == 0 && map[newY][newX] <= SHARK_LEVEL ){
						visited[newY][newX]=1;								
						queue.add(new Fish(newX,newY,map[newY][newX],timer));
					}
				}	
			}					
		}		
	}
}

class Fish{
	int x,y,size,time;
	Fish(int x, int y, int size,int time){
		this.x=x;
		this.y=y;
		this.size=size;
		this.time=time;
	}
}
