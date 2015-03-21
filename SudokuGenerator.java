import java.util.*;
import java.io.*;

public class SudokuGenerator
{
	public static int[][] grid = new int[9][9];
	public static int[][] ref = {{1, 1, 1, 2, 2, 2, 3, 3, 3}, {1, 1, 1, 2, 2, 2, 3, 3, 3},{1, 1, 1, 2, 2, 2, 3, 3, 3}, {4, 4, 4, 5, 5, 5, 6, 6, 6}, {4, 4, 4, 5, 5, 5, 6, 6, 6}, {4, 4, 4, 5, 5, 5, 6, 6, 6}, {7, 7, 7, 8, 8, 8, 9, 9, 9}, {7, 7, 7, 8, 8, 8, 9, 9, 9}, {7, 7, 7, 8, 8, 8, 9, 9, 9}};
	public static boolean check = true;
	public static int difficulty;
	
	public static void main(String[] args) throws IOException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("SudokuPuzzle.out")));
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose your difficulty: 1, 2, or 3.");
		System.out.println("1. Easy");
		System.out.println("2. Medium");
		System.out.println("3. Hard");
		System.out.print("Choice: ");
		difficulty = sc.nextInt();
		
		//generate the complete maze
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				grid[i][j] = -1;
			}
		}
		
		create(0, 0);
		print(out);
		out.close();
		System.exit(0);
	}
	
	//take a point, run through its possible values, guess and recurse
	public static void create(int y, int x)
	{
		boolean[] bank = getPoss(y, x);
		int[] rand = randBank();
		for (int i = 0; i < 9; i++)
		{		
			if (bank[rand[i]])
			{
				if (!check) return;
				grid[y][x] = rand[i];
				int newY = y;
				int newX = x;
				do
				{
					newX = (newX + 1) % 9;
					if (newX == 0)
					{
						newY++;
					}
					if (newY == 9)
					{
						check = false;
						return;
					}
				} while (grid[newY][newX] != -1);
				if (!check) return;
				if (check)
				{
					//System.out.println("Trying: " + newY + " " + newX);
					create(newY, newX);
				}
			}
		}
		if (!check) return;
		grid[y][x] = -1;
	}
	
	//print the grid
	public static void print(PrintWriter out) throws IOException
	{
		int rCount = 0;
		out.println("-------------");
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				out.print("|");
				int cCount = 0;
				for (int k = 0; k < 3; k++)
				{
					for (int l = 0; l < 3; l++)
					{
						if (grid[rCount][cCount] == -1)
						{
							out.print(" ");
						}
						else
						{
							out.print(grid[rCount][cCount] + 1);
						}
						cCount++;
					}
					out.print("|");
				}
				out.println("");
				rCount++;
			}
			out.println("-------------");
		}
	}
	
	//returns a boolean array that has the possible numbers you can use
	public static boolean[] getPoss(int y, int x)
	{
		//create an array of possible numbers
		boolean[] bank = {true, true, true, true, true, true, true, true, true};
		//go through the row and take out used numbers
		for (int k = 0; k < 9; k++)
		{
			if (grid[y][k] != -1)
			{
				bank[grid[y][k]] = false;
			}
		}
		//go through the column
		for (int k = 0; k < 9; k++)
		{
			if (grid[k][x] != -1)
			{
				bank[grid[k][x]] = false;
			}
		}
		//go through its companions in its grid square
		for (int k = 0; k < 9; k++)
		{
			for (int l = 0; l < 9; l++)
			{
				if (ref[k][l] == ref[y][x])
				{
					if (grid[k][l] != -1)
					{
						bank[grid[k][l]] = false;
					}
				}
			}
		}
		return bank;
	}
	
	//creates random # pairings for bank
	public static int[] randBank()
	{
		int[] x = new int[9];
		boolean[] y = new boolean[9];
		Random r = new Random();
		for (int i = 0; i < 9; i++)
		{
			y[i] = true;
		}
		for (int i = 0; i < 9; i++)
		{
			int z;
			do
			{
				z = r.nextInt(9);
			} while (!y[z]);
			x[i] = z;
			y[z] = false;
		}
		return x;
	}
}