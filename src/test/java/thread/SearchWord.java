package thread;

import java.util.Set;

public class SearchWord {
		private Set<String> words = null;
		
		private StringBuffer sb = new StringBuffer();

		public StringBuffer getSb() {
			return sb;
		}

		public void setSb(StringBuffer sb) {
			this.sb = sb;
		}

		public Set<String> getWords() {
			return words;
		}

		public void setWords(Set<String> words) {
			this.words = words;
		}
		
		public int getSize(){
			return words.size();
		}

		/**
		 * 获取第一个词 这块要加一个多线程同步
		 */
		public synchronized String getAword(){
			String currentword = words.toArray()[0].toString();
			words.remove(currentword);
			return currentword;
		}
		
		/**
		 * 把这个词从集合中删除
		 * @param currentWord
		 */
		public synchronized void removeCurrentWord(String currentWord){
			words.remove(currentWord);
		}
		
}
