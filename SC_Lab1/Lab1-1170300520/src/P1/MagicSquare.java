package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.NumberFormatException;

/**
 * 检测矩阵是否为Magic Square
 * 
 * @author GuoZiyang
 * @date 2019/2/25
 */
public class MagicSquare {
	public static void main(String[] args) throws IOException {
		for (int i = 1; i <= 5; i++) {
			System.out.println("以下检测矩阵" + i + ".txt");
			System.out.println(isLegalMagicSquare(new File("").getCanonicalPath() + "/src/P1/txt/" + i + ".txt"));
			System.out.println();
			System.out.println();
			/*
			 * 休眠50毫秒以防止输出混乱
			 */
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("要求2：以下测试generateMagicSquare()方法：");
		if (generateMagicSquare(5)) {
			System.out.println();
			System.out.println();
			System.out.println("以下检测矩阵6.txt");
			System.out.println(isLegalMagicSquare(new File("").getCanonicalPath() + "/src/P1/txt/6.txt"));
		}

	}

	/**
	 * 检测给定的文件中的矩阵是否为Magic Square
	 * 
	 * @param fileName 矩阵文件的路径字符串
	 * @return 该文件中的矩阵是否为Magic Square
	 */
	public static boolean isLegalMagicSquare(String fileName) {
		List<List<Integer>> square = readFromFile(fileName);
		if (square == null) {
			return false;
		} else if (!isValidSquare(square)) {
			System.out.println("该矩阵不是方阵！");
			return false;
		} else if (!isMagicSquare(square)) {
			System.out.println("该矩阵不是Magic Square");
			return false;
		}
		return true;
	}

	/**
	 * 从给定的路径中读取矩阵并存入二维List
	 * 
	 * @param fileName 矩阵文件路径字符串
	 * @return 存储矩阵的二维List
	 */
	public static List<List<Integer>> readFromFile(String fileName) {
		BufferedReader bufferedReader = null;
		List<List<Integer>> result = new ArrayList<>();
		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				List<Integer> lineList = convertTointList(Arrays.asList(line.split("\t")));
				result.add(lineList);
			}
			return result;
		} catch (IOException e) {
			System.out.println("读入文件错误！请检查文件路径！");
			e.printStackTrace();
			return null;
		} catch (NumberFormatException e1) {
			System.out.println("文件数字不规范！请检查文件！");
			e1.printStackTrace();
			return null;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将字符串List转化为整型的List
	 * 
	 * @param list 待转化的字符串List
	 * @return 转化后的整型List
	 * @throws NumberFormatException 数字不符合规范异常
	 */
	public static List<Integer> convertTointList(List<String> list) throws NumberFormatException {
		List<Integer> result = new ArrayList<>();
		for (String string : list) {
			if (string == null || string == "") {
				continue;
			}
			if(Integer.parseInt(string) < 0) {
				throw new NumberFormatException();
			}
			result.add(Integer.parseInt(string));
		}
		return result;
	}

	/**
	 * 检测该矩阵是否为方阵
	 * 
	 * @param square 待检测的矩阵
	 * @return 是否为方阵
	 */
	public static Boolean isValidSquare(List<List<Integer>> square) {
		int lineNumber = square.size();
		for (List<Integer> line : square) {
			if (line.size() != lineNumber) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检测该矩阵是否为Magic Square
	 * 
	 * @param square 待检测的矩阵
	 * @return 该矩阵是否为Magic Square
	 */
	public static Boolean isMagicSquare(List<List<Integer>> square) {
		int magicNumber = 0;
		int line = square.size();
		int tempNumber = 0;
		List<Integer> firstLine = square.get(0);
		for (int firstLineNumber : firstLine) {
			magicNumber += firstLineNumber;
		}
		for (List<Integer> list : square) {
			for (int lineNumber : list) {
				tempNumber += lineNumber;
			}
			if (tempNumber != magicNumber) {
				return false;
			}
			tempNumber = 0;
		}
		for (int row = 0; row < line; row++) {
			for (int col = 0; col < line; col++) {
				tempNumber += square.get(col).get(row);
			}
			if (tempNumber != magicNumber) {
				return false;
			}
			tempNumber = 0;
		}
		for (int row = 0; row < line; row++) {
			tempNumber += square.get(row).get(row);
		}
		if (tempNumber != magicNumber) {
			return false;
		}
		tempNumber = 0;
		for (int row = 0; row < line; row++) {
			tempNumber += square.get(row).get(line - 1 - row);
		}
		if (tempNumber != magicNumber) {
			return false;
		}
		return true;
	}

	/**
	 * 要求2，产生一个Magic Square
	 * 
	 * @param n 矩阵的规模
	 * @return 返回
	 */
	public static boolean generateMagicSquare(int n) {
		int magic[][];
		try {
			magic = new int[n][n];
			int row = 0, col = n / 2, i, square = n * n;
			for (i = 1; i <= square; i++) {
				magic[row][col] = i;
				//若当前写入的数字是n的倍数，则向下一行写入
				if (i % n == 0)
					row++;
				else {
					//若当前行数为0，则跳到最后一行
					if (row == 0)
						row = n - 1;
					//否则向上一行写入
					else
						row--;
					//若当前列数为最后一列，则跳到第一列
					if (col == (n - 1))
						col = 0;
					//否则向下一列写入
					else
						col++;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("请输入偶数值！");
			e.printStackTrace();
			System.out.println("false");
			return false;
		} catch (NegativeArraySizeException e) {
			System.out.println("请输入正整数！");
			e.printStackTrace();
			System.out.println("false");
			return false;
		}
		writeToFile(magic);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
			System.out.println();
		}
		return true;
	}

	/**
	 * 将一个矩阵写入txt/6.txt文件
	 * 
	 * @param square 待写入的二维数组
	 */
	public static void writeToFile(int[][] square) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File("").getCanonicalPath() + "/src/P1/txt/6.txt"));
			for (int row = 0; row < square.length; row++) {
				for (int col = 0; col < square.length; col++) {
					writer.write(String.valueOf(square[row][col]));
					if (col != square.length - 1) {
						writer.write("\t");
					}
				}
				if (row != square.length - 1) {
					writer.newLine();
				}
			}
			writer.flush();
		} catch (IOException e) {
			System.out.println("写入文件失败！");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e2) {
					System.out.println("输出流关闭失败！");
					e2.printStackTrace();
				}
			}
		}
	}
}
