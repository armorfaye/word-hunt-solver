import java.util.*;
import java.io.*;

public class Main{

	static int[][] DIR = {
		{ -1, 0 },
		{ -1, 1 },
		{ 0, 1 },
		{ 1, 1 },
		{ 1, 0 },
		{ 1, -1 },
		{ 0, -1 },
		{ -1, -1 },
	};

	static int gridsize; 
	static Character[][] grid;
	static boolean[][] visited;
	static List<Integer> movements = new ArrayList<Integer>(); 

	static String[] ARROWS = {"↑", "↗", "→", "↘", "↓", "↙", "←", "↖"};
	static TrieNode prefix = new TrieNode(); 

	static class Word implements Comparable<Word>{
		public String word;
		public int startX, startY; 
		public List<Integer> movement; 

		public Word(String word, int startX, int startY, List<Integer> movements){   
			movement = new ArrayList<Integer>(); 
			this.word = word; 
			this.startX = startX; 
			this.startY = startY; 
			movement = new ArrayList<Integer>(); 
			for(int i : movements){
				movement.add(i); 
			}
		}

		public int compareTo(Word y){
			if(word.length() == y.word.length()){
				if(word.equals(y.word)){
					return 0; 
				}
				return 1; 
			}
			return Integer.compare(word.length(), y.word.length());
		}

		public void printMovements(){
			for(int move : movement){
				System.out.print(" " + ARROWS[move]); 
			}
		}
	}

	static Set<Word> results = new TreeSet<Word>(); 

	public static void dfs(int sx, int sy, int x, int y, String word, TrieNode curNode){
		word += grid[x][y];

		if(word.length() >=3 && curNode.isWord){
			results.add(new Word(word, sx, sy, movements));
		}

		for(int i=0; i<8; i++){
			int nx = x + DIR[i][0];
			int ny = y + DIR[i][1]; 

			if (nx < 0 || ny < 0 || nx >= gridsize || ny >= gridsize)
				continue;
			if (!curNode.has_child(grid[nx][ny]))
				continue;
			if (visited[nx][ny])
				continue;

			movements.add(i); 
			visited[nx][ny] = true;
			dfs(sx, sy, nx, ny, word, curNode.get_child(grid[nx][ny]));
			visited[nx][ny] = false;
			movements.remove(movements.size()-1); 
		}
	}

	public static void main(String[] args) throws IOException{

		BufferedReader r = new BufferedReader(new FileReader("wordlist.txt")); 
		String word; 
		while((word = r.readLine()) != null){
			TrieNode prevNode = prefix; 
			for(int i=0; i<word.length(); i++){
				prevNode = prevNode.add_or_get_child(word.charAt(i)); 
			}
			prevNode.isWord = true; 
		}

		r.close(); 

		r = new BufferedReader(new FileReader("input.txt")); 

		String s = r.readLine(); 
		gridsize = s.length(); 
		grid = new Character[gridsize][gridsize];
		visited = new boolean[gridsize][gridsize]; 

		for(int i=0; i<gridsize; i++){
			for(int j=0; j<gridsize; j++){
				grid[i][j] = s.charAt(j); 
			}
			s = r.readLine(); 
		}

		r.close(); 

		for(int i=0; i<gridsize; i++){
			for(int j=0; j<gridsize; j++){
				visited[i][j] = true; 
				dfs(i,j, i, j, "", prefix.get_child(grid[i][j]));
				visited[i][j] = false; 
			}
		}

		for (Word w : results) {
			System.out.print(w.word + " [" + Integer.toString(w.startY+1) + ", " + Integer.toString(gridsize-w.startX) + "]");
			w.printMovements();
			System.out.println("");
		}
	}
}
