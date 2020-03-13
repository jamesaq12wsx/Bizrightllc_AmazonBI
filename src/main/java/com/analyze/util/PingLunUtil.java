package com.analyze.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 评论分析
 */
public final class PingLunUtil {
    
	public static String pinglun(String  str){
     //  String str ="/wd/98#很/d/57#;/wf/52#好/n/44#的/ude1/43#！/wt/29#质量/n/22#喜欢/vi/18#衣服/n/16#也/d/14#买/v/12#了/y/12#不/d/11#穿/v/11#还/d/11#。/wj/9#?/ww/9#特别/d/8#好评/n/8#了/ule/8#不错/a/8#舒服/a/8#吧/y/8#到/v/8#看/v/7#收到/v/6#是/vshi/6#这么/rz/6#宝贝/n/6#丝/n/6#裙子/n/6#来/vf/6#挺/d/6#大爱/nr2/5#好看/a/5#便宜/a/5#值/v/5#蕾/ng/5#价格/n/5#就/d/5#会/v/4#辣/a/4#鸡/n/4#这个/rz/4#可以/v/4#真/a/4#非常/d/4#又/d/4#起来/vf/4#下次/t/4#值得/v/4#仙/ng/4#漂亮/a/4#没/d/4#满意/v/3#搭/v/3#而且/c/3#和/cc/3#一样/uyy/3#东西/nr2/3#长/a/3#购买/v/3#反正/d/3#真的/d/3#错/v/3#啊/y/3#以后/f/3#着/uzhe/3#超级/b/3#物美价廉/vl/3#都/d/3#布料/n/3#什么/ry/3#快/a/2#拍/v/2#哦/e/2#一次/性行为/2#搭配/v/2#优惠/vn/2#太/d/2#下手/vi/2#人/n/2#够/v/2#在/p/2#图片/n/2#洗/v/2#我/rr/2#推荐/v/2#货/n/2#毛衣/n/2#价钱/n/2#上/f/2#一点/mq/2#上/vf/2#美美/d/2#客服/n/2#棒/ng/2#一分/t/2#本来/d/2#想/v/2#抱/v/2#赞/vg/2#hellip/n/2#快/d/2#购物/vi/2#呦/e/2#快递/v/2#很快/d/2#做/v/2#&/w/2#纱/n/2#摸/v/2#玩意/n/2#差/a/2#谁/ry/2#说/v/2#怎么/ryv/2#呀/y/2#返/v/1#现/tg/1#美/b/1#眉/n/1#考虑/v/1#扎/v/1#美/a/1#哒哒/o/1#开心/a/1#19.9/m/1#白/a/1#质优价廉/nl/1#热情/an/1#耐心/an/1#搭配/vn/1#刚/d/1#收/v/1#觉得/v/1#宽松/a/1#修身/vi/1#不过/c/1#看到/v/1#后/f/1#态度/n/1#满满/z/1#哈哈哈/o/1#柔软/a/1#老板/n/1#抓紧/v/1#时间/n/1#光顾/v/1#物流/n/1#描述/vn/1#有/vyou/1#事/n/1#耽误/v/1#怎么样/ryv/1#评价/v/1#晚/a/1#大爱蕾/nr/1#今天/t/1#面料/n/1#下身/n/1#那些/rz/1#十几/m/1#滑/v/1#手/n/1#对得起/v/1#白色/n/1#块/q/1#再/d/1#钱/n/1#更/d/1#能/v/1#美/ng/1#同事/n/1#两/m/1#条/q/1#物美/nz/1#哒/n/1#配/v/1#粉色/n/1#赶紧/d/1#自己/rr/1#想象/vn/1#之中/f/1#价值/n/1#谢谢/v/1#下手/n/1#店家/n/1#细/a/1#这/rzv/1#长度/n/1#正好/z/1#露出/v/1#质感/n/1#因为/c/1#本人/rr/1#比较/d/1#胖/a/1#出/vf/1#效果/n/1#将/d/1#样式/n/1#宝宝/n/1#啦/y/1#给/p/1#五星/n/1#超/h/1#值/n/1#肯定/vn/1#精致/a/1#看看/v/1#想法/n/1#第一/m/1#次/qv/1#淘/v/1#价位/n/1#赚/v/1#物超/nr2/1#一定/d/1#三/m/1#天/qt/1#从没/d/1#见/v/1#过/uguo/1#负责/v/1#多/m/1#只是/d/1#拍照/vi/1#所/usuo/1#便宜/v/1#们/k/1#室友/n/1#合适/a/1#打/v/1#底/f/1#没有/d/1#发现/v/1#破/v/1#洞/n/1#实惠/an/1#做工/n/1#一般/uyy/1#知道/v/1#用/v/1#里面/f/1#黑心/n/1#料子/n/1#就/p/1#皮肤/n/1#过敏/vn/1#浑身/n/1#起/vf/1#包/v/1#手感/n/1#？/ww/1#连接/v/1#处/vi/1#地方/n/1#尤为/d/1#明显/a/1#时候/n/1#肯定/d/1#脏/a/1#还是/c/1#建议/n/1#不要/d/1#价/n/1#1.71/m/1#重/q/1#垃圾/n/1#卖家/n/1#推卸/v/1#责任/n/1#120/m/1#直接/ad/1#扔/v/1#痒/a/1#死/v/1#腿/n/1#后悔/v/1#腕/ng/1#贵/a/1#刷/v/1#单/d/1#刷/o/1#过敏/vi/1#严重/a/1#痛苦/an/1#中/f/1#坑/n/1#爹/n/1#简直/d/1#一个/mq/1#星/n/1#给/v/1#期望/vn/1#想到/v/1#还有/v/1#试穿/v/1#真真/nr/1#极/d/1#";
        String[] s = str.split("\\#");
        List<Map<String,Object>> lista=new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> listv=new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> listn=new ArrayList<Map<String,Object>>();
        Map<Object,List<Map<String,Object>>> map=new HashMap<Object,List<Map<String,Object>>>();
        Map<String,Object> mapa=new HashMap<String, Object>();
        Map<String,Object> mapv=new HashMap<String, Object>();
        Map<String,Object> mapn=new HashMap<String, Object>();
        for (int i = 0; i < s.length; i++) {
        	String ss=s[i];
        	ss=ss.replace("/", ":");   
        	//System.out.println(ss);
        	String a[] = ss.split(":");
        	if(Integer.valueOf(a[2])>=1){
        		if(a[1].equals("n")){
           			mapn.put(a[0], a[2]);                		
            	}else if(a[1].equals("a")||a[1].equals("ad")){
            		mapa.put(a[0], a[2]);            			
            	}else if(a[1].equals("v")){
            		mapv.put(a[0], a[2]);            			
            	}
        	}
        }
        listn.add(mapn);
        lista.add(mapa);
        listv.add(mapv);
        map.put("a", lista);
        map.put("v", listv);
        map.put("n", listn);
        //System.out.println(JSON.toJSON(map)); 
        return JSON.toJSON(map).toString();
    }
    
}
