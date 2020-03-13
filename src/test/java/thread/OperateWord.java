package thread;
/**
 * 处理词的线程
 * @author My
 *
 */
public class OperateWord implements Runnable{
	
	private SearchWord searchWord;

	@Override
	public void run() {
		while (searchWord.getSize()>0) {
			//开始操作
			String currentWord = searchWord.getAword();
			System.out.println("当前处理的关键词为："+currentWord);
			System.out.println("开始处理------------"+currentWord);
			searchWord.getSb().append(currentWord+"---------");
			System.out.println("处理完毕------------"+currentWord);
			System.out.println("准备移除该词---------"+currentWord);
			if (false) {
				//如果这一步失败，那么需要把这个词重新add到集合中去
			}
			System.out.println("移除完毕-------------"+currentWord);
		}
	}

	public SearchWord getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(SearchWord searchWord) {
		this.searchWord = searchWord;
	}

}
