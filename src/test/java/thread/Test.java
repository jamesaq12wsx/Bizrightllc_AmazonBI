package thread;

import java.util.HashSet;
import java.util.Set;

public class Test {
	public static void main(String[] args) {
		
		
		SearchWord searchWord = new SearchWord();
		Set<String> words = new HashSet<String>();
		words.add("世界");
		words.add("你");
		words.add("好");
		words.add("，");
		words.add("我");
		words.add("来自");
		words.add("火星");
		words.add("!");
		searchWord.setWords(words);

		OperateWord ow = new OperateWord();
		ow.setSearchWord(searchWord);
		
		//开启多线程
		Thread thread1 = new Thread(ow);
		Thread thread2 = new Thread(ow);
		Thread thread3 = new Thread(ow);
		Thread thread4 = new Thread(ow);
		
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		
		try {
			Thread.sleep(5000);
			
			System.out.println(searchWord.getSb().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
