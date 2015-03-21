import java.util.*;
import java.io.*;

public class SudokuSolver
{
	public static int[][] grid = new int[9][9];
	public static int[][] ref = {{1, 1, 1, 2, 2, 2, 3, 3, 3}, {1, 1, 1, 2, 2, 2, 3, 3, 3},{1, 1, 1, 2, 2, 2, 3, 3, 3}, {4, 4, 4, 5, 5, 5, 6, 6, 6}, {4, 4, 4, 5, 5, 5, 6, 6, 6}, {4, 4, 4, 5, 5, 5, 6, 6, 6}, {7, 7, 7, 8, 8, 8, 9, 9, 9}, {7, 7, 7, 8, 8, 8, 9, 9, 9}, {7, 7, 7, 8, 8, 8, 9, 9, 9}};
	public static boolean check = true;
	
	public static void main(String[] args) throws IOException
	{
		//read the input
		BufferedReader f = new BufferedReader(new FileReader("input.txt"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("SudokuSolved.out")));
		for (int i = 0; i < 9; i++)
		{
			String line = f.readLine();
			for (int j = 0; j < 9; j++)
			{
				if (line.charAt(j) == '-')
				{
					grid[i][j] = -1;
				}
				else
				{
					grid[i][j] = Character.getNumericValue(line.charAt(j)) - 1;
				}
			}
		}
		
		print(out);
		out.println();
		
		//find the first point
		int y = 0;
		int x = 0;
		while (grid[y][x] != -1)
		{
			x = (x + 1) % 9;
			if (x == 0)
			{
				y++;
			}
		}
		
		//run recursion
		create(y, x);
		
		print(out);
		
		out.close();
		System.exit(0);
	}
	
	//take a point, run through its possible values, guess and recurse
	public static void create(int y, int x)
	{
		boolean[] bank = getPoss(y, x);
		for (int i = 0; i < 9; i++)
		{
			if (!check) return;
			if (bank[i])
			{
				grid[y][x] = i;
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
	
	//print the grid
	public static void print(PrintWriter out)
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
}