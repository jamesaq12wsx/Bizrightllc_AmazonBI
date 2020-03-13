package  com.analyze.util;

import com.analyze.util.StringHelper;
/**
 * 截取淘宝、天猫的商品id
 * @author xiaobai
 * @time 2016-11-22 11:24:03
 * 
 */
public class ProdIDUtil {
	 public static String getGoodsId(String url) {
			String goodsId = null;
			if (url.contains("?id=")) {
				goodsId = StringHelper.getResultByReg(url, "\\?id=(\\d+)");
			}else if(url.contains("&id=")) {
				goodsId = StringHelper.getResultByReg(url, "&id=(\\d+)");
			}else  if(url.contains("id=")){
				goodsId = StringHelper.getResultByReg(url, "id=(\\d+)");
			}else{
				goodsId = StringHelper.getResultByReg(url, "(\\d+)");

			}
			return goodsId;
		}
	public static void main(String[] args) {
	
//	System.out.println(getGoodsId("https://detail.tmall.com/item.htm?spm=a220o.1000855.1998025129.1.kAQKTG&abtest=_AB-LR32-PR32&pvid=65de3771-4f54-44db-90aa-a41b4077dc50&pos=1&abbucket=_AB-M32_B14&acm=03054.1003.1.1539344&id=547229026879&scm=1007.12144.81309.23864_0&skuId=3312549525482"));
	System.out.println(getGoodsId("https://detail.tmall.com/item.htm?id=528626569241&sku_properties=10004:709990523;12304035:3222910"));
	}
}
