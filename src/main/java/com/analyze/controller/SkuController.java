package com.analyze.controller;

import cn.hutool.core.util.ObjectUtil;
import com.analyze.dao.ProductLabelMapper;
import com.analyze.dao.SkuCommonInfoMapper;
import com.analyze.dao.SkuInfoMapper;
import com.analyze.dao.SkuManageMapper;
import com.analyze.excelExport.CommonExport;
import com.analyze.model.ProductLabel;
import com.analyze.model.SkuInfo;
import com.analyze.service.InventroryAnalysisService;
import com.analyze.service.SkuAnalysisService;
import com.analyze.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;

/**
 * 2018-04-18
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/sku")
public class SkuController extends BaseController {
	private final Logger log = LoggerFactory.getLogger(SkuController.class);

	@Autowired
	private SkuInfoMapper skuInfoMapper;
	@Autowired
	private SkuCommonInfoMapper skuCommonInfoMapper;

	@Autowired
	private ProductLabelMapper productLabelMapper;

	@Autowired
	private SkuManageMapper skuManageMapper;

	@Autowired
	@Qualifier("InventroryAnalysisServiceForEndPosImpl")
	private InventroryAnalysisService inventroryAnalysisServiceForEndPosImpl ;

	@Autowired
	@Qualifier("SkuAnalysisServiceImpl")
	private SkuAnalysisService skuAnalysisServiceImpl ;

	@RequestMapping(value = "/Amazon_SKUAnalysis_SkuSearch", method = RequestMethod.GET)
	@ResponseBody
	public Object Amazon_SKUAnalysis_SkuSearch(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_SKUAnalysis_SkuSearch(getRequestParams(request));

			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuInfoMapper.Amazon_SKUAnalysis_SkuSearch(map);
			resultMap.put("total", total);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);

		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/loadLabel", method = RequestMethod.POST)
	@ResponseBody
	public Object loadLabel(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> mp = getRequestParams(request);
			List<Object> labellist = productLabelMapper.getProductLabelByasin(mp);
			resultMap.put("data", labellist);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/search_price", method = RequestMethod.POST)
	@ResponseBody
	public Object search_price(HttpServletRequest request, Integer isMain) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> mp = getRequestParams(request);
			Map<Object, Object> m = new HashMap<>();
			List<Object> arrL = new ArrayList<>();
			if (isMain == 0) {
				List<Object> list = skuInfoMapper.seachAsinMe(mp);

				for (Object o : list) {
					Map map = (Map) o;

					Object l = skuInfoMapper.getAsinMe_price(map.get("compareOurAsin"));
					arrL.add(l);
				}

			} else {
				List<Object> list = skuInfoMapper.seachAsin(mp);

				for (Object o : list) {
					Map map = (Map) o;
					Object l = skuInfoMapper.getAsinMe_price(map.get("asin"));
					arrL.add(l);
				}
			}

			resultMap.put("data", arrL);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}

		return resultMap;
	}

	/*
	 * @RequestMapping(value = "/load", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Object load(HttpServletRequest request) {
	 * Map<String, Object> resultMap = new HashMap<String, Object>(); try {
	 * Map<String, Object> mp = getRequestParams(request); List<Object> lt =
	 * null; if (Integer.parseInt(mp.get("table_price_s").toString()) == 2) {
	 * Long count = skuInfoMapper.seachWhether(mp); if (count == 1) { lt =
	 * skuInfoMapper.seachAsin(mp); } if (count == 0) { lt =
	 * skuInfoMapper.seachAsinMe(mp); } } if
	 * (Integer.parseInt(mp.get("table_price_s").toString()) == 1) { lt =
	 * skuInfoMapper.seachAsin(mp); } if
	 * (Integer.parseInt(mp.get("table_price_s").toString()) == 0) { lt =
	 * skuInfoMapper.seachAsinMe(mp); } resultMap.put("data", lt);
	 * resultMap.put(STATUS, SUCCESS); } catch (Exception e) {
	 * resultMap.put(STATUS, FAIL); resultMap.put(MSG, e.getMessage()); }
	 * 
	 * return resultMap; }
	 */
	@RequestMapping(value = "/loadJP", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJP(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> mp = getRequestParams(request);
			List<Object> lt = null;
			if (Integer.parseInt(mp.get("type").toString()) == 2) {
				Long count = skuInfoMapper.seachWhether(mp);
				if (count == 1) {
					lt = skuInfoMapper.seachAsin(mp);
				}
				if (count == 0) {
					lt = skuInfoMapper.seachAsinMe(mp);
				}
			}
			if (Integer.parseInt(mp.get("type").toString()) == 1) {
				lt = skuInfoMapper.seachAsin(mp);
			}
			if (Integer.parseInt(mp.get("type").toString()) == 0) {
				lt = skuInfoMapper.seachAsinMe(mp);
			}
			resultMap.put("data", lt);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_SkuSearch_Details", method = RequestMethod.GET)
	@ResponseBody
	public Object Amazon_SKUAnalysis_SkuSearch_Details(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuInfoMapper.Amazon_SKUAnalysis_SkuSearch_Details(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getProductLabel", method = RequestMethod.GET)
	@ResponseBody
	public Object getProductLabel(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = productLabelMapper.getProductLabel();
			List<Object> addlist = productLabelMapper.getProductLabelByasin(getRequestParams(request));

			resultMap.put("data", list);
			resultMap.put("data_exist", addlist);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/deleteProductLabel", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteProductLabel(HttpServletRequest request, Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int i = productLabelMapper.deleteByPrimaryKey(id);
			if (i > 0) {
				List<Object> list = productLabelMapper.getProductLabel();
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			} else {
				resultMap.put(STATUS, FAIL);
				resultMap.put(MSG, "删除失败！");
			}

		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/addProductLabel", method = RequestMethod.POST)
	@ResponseBody
	public Object addProductLabel(HttpServletRequest request, ProductLabel productLabel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = productLabelMapper.selectByLabelName(productLabel.getLabelname());
			if (count > 0) {
				resultMap.put(STATUS, FAIL);
				resultMap.put(MSG, "已经存在此标签！");
				return resultMap;
			}
			productLabel.setCreatedate(new Date());
			productLabel.setUsername(request.getSession().getAttribute("username").toString());
			int i = productLabelMapper.insertSelective(productLabel);
			if (i > 0) {
				List<Object> list = productLabelMapper.getProductLabel();
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			} else {
				resultMap.put(STATUS, FAIL);
				resultMap.put(MSG, "添加失败！");
			}

		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/checkAsinLabel", method = RequestMethod.POST)
	@ResponseBody
	public Object checkAsinLabel(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = productLabelMapper.selectTableAsinLabel(getRequestParams(request));
			if (count > 0) {
				resultMap.put(STATUS, FAIL);
				resultMap.put(MSG, "已经存在此标签！");
				return resultMap;
			} else {
				resultMap.put(STATUS, SUCCESS);

			}

		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/addAsinLabel", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Object addAsinLabel(HttpServletRequest request, String labelName, String asin) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("asin", asin);
			int count = 0;
			String[] str = labelName.split(",");
			for (String l : str) {
				if (l.equals("")) {
					continue;
				}
				map.put("labelName", l);
				int i = productLabelMapper.addAsinLabel(map);
				count = count + i;
			}
			resultMap.put(STATUS, SUCCESS);
			resultMap.put(MSG, "选择" + (labelName.split(",").length - 1) + "个,成功添加" + count + "个!");
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/addLabel", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Object addLabel(HttpServletRequest request, String labelName, String asin) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int i = productLabelMapper.addAsinLabel(getRequestParams(request));
			resultMap.put(STATUS, i);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/delLabel", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Object delLabel(HttpServletRequest request, String labelName, String asin) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int i = productLabelMapper.delAsinLabel(getRequestParams(request));
			resultMap.put(STATUS, i);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/delAsinLabel", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Object delAsinLabel(HttpServletRequest request, String labelName, String asin) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("asin", asin);
			int count = 0;
			String[] str = labelName.split(",");
			for (String l : str) {
				if (l.equals("")) {
					continue;
				}
				map.put("labelName", l);
				int i = productLabelMapper.delAsinLabel(map);
				count = count + i;
			}
			List<Object> list = productLabelMapper.getProductLabelByasin(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
			resultMap.put(MSG, "选择" + (labelName.split(",").length - 1) + "个,成功删除" + count + "个!");
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getProductLabelByasin", method = RequestMethod.POST)
	@ResponseBody
	public Object getProductLabelByasin(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = productLabelMapper.getProductLabelByasin(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 查询产品的产品线
	 * @param request
	 * @return
     */
	@RequestMapping(value = "/getProductLineInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object getProductLineInfo(HttpServletRequest request) {
		try {
			List<Object> list = skuInfoMapper.Amazon_MerchandiseInfo_ProductLineInfo(getRequestParams(request));
			return list;
		} catch (Exception e) {
			return "[{fail:\""+e.getMessage()+"\"}]";
		}
	}


	@RequestMapping(value = "/Amazon_SKUAnalysis_SkuSearch_downLoad", method = RequestMethod.GET)
	public void Amazon_SKUAnalysis_SkuSearch_downLoad(HttpServletRequest request, HttpServletResponse response) {

		try {

			JSONArray ja = new JSONArray();
			List<Object> list = skuInfoMapper.Amazon_SKUAnalysis_SkuSearch(getRequestParams(request));
			Map<String, Object> mp = getRequestParams(request);

			for (Object o : list) {
				JSONObject job = JSONObject.fromObject(o);
				System.out.println(job.get("asin"));
				mp.put("asin", job.get("asin"));
				if (Integer.parseInt(mp.get("type").toString()) == 2) {
					Long count = skuInfoMapper.seachWhether(mp);
					if (count == 1) {
						List<Object> lt = skuInfoMapper.seachAsin(mp);
						String str = "";
						int countx = 0;
						for (Object x : lt) {
							JSONObject jbb = JSONObject.fromObject(x);
							if (countx == 0) {
								countx++;
								str = jbb.get("compareOurAsin") == null ? (String) jbb.get("asin")
										: (String) jbb.get("compareOurAsin");
							} else {
								str = str + "," + (jbb.get("compareOurAsin") == null ? (String) jbb.get("asin")
										: (String) jbb.get("compareOurAsin"));
							}
						}
						job.put("dsAsin", str);
					}
					if (count == 0) {
						List<Object> lt = skuInfoMapper.seachAsin(mp);
						String str = "";
						int countx = 0;
						for (Object x : lt) {
							JSONObject jbb = JSONObject.fromObject(x);
							if (countx == 0) {
								countx++;
								str = jbb.get("compareOurAsin") == null ? (String) jbb.get("asin")
										: (String) jbb.get("compareOurAsin");
							} else {
								str = str + "," + (jbb.get("compareOurAsin") == null ? (String) jbb.get("asin")
										: (String) jbb.get("compareOurAsin"));
							}
						}
						job.put("dsAsin", str);
					}
				}
				if (Integer.parseInt(mp.get("type").toString()) == 1) {
					List<Object> lt = skuInfoMapper.seachAsin(mp);
					String str = "";
					int countx = 0;
					for (Object x : lt) {
						JSONObject jbb = JSONObject.fromObject(x);
						if (countx == 0) {
							countx++;
							str = jbb.get("compareOurAsin") == null ? (String) jbb.get("asin")
									: (String) jbb.get("compareOurAsin");
						} else {
							str = str + "," + (jbb.get("compareOurAsin") == null ? (String) jbb.get("asin")
									: (String) jbb.get("compareOurAsin"));
						}
					}
					job.put("dsAsin", str);
				}
				if (Integer.parseInt(mp.get("type").toString()) == 0) {
					List<Object> lt = skuInfoMapper.seachAsin(mp);
					String str = "";
					int countx = 0;
					for (Object x : lt) {
						JSONObject jbb = JSONObject.fromObject(x);
						if (countx == 0) {
							countx++;
							str = jbb.get("compareOurAsin") == null ? (String) jbb.get("asin")
									: (String) jbb.get("compareOurAsin");
						} else {
							str = str + "," + (jbb.get("compareOurAsin") == null ? (String) jbb.get("asin")
									: (String) jbb.get("compareOurAsin"));
						}
					}
					job.put("dsAsin", str);
				}
				List<Object> labellist = productLabelMapper.getProductLabelByasin(mp);
				String stringLabel = "";
				int countx = 0;
				for (Object x : labellist) {
					JSONObject jbb = JSONObject.fromObject(x);

					if (countx == 0) {
						stringLabel = jbb.get("labelName").toString();
					} else {
						stringLabel = stringLabel + "," + jbb.get("labelName").toString();
					}
					countx++;
				}
				job.put("label", stringLabel);

				ja.add(job);
			}
			List<Map<Object, Object>> list1 = (List) ja;

			// Map<String, Object> paramMap = getRequestParams(request);
			// List<Map<Object, Object>> list =
			// skuInfoMapper.Amazon_SKUAnalysis_SkuSearch_downLoad(paramMap);

			CommonExport ce = new CommonExport();
			String[] keys = { "label", "asin", "sku_imageUrl", "sku_productTitle", "sku", "sku_price",
					"yestday_sku_price", "main_rwNum", "sku_increasedRwNum", "sku_soldBy", "sku_isPrime", "dsAsin" };
			String[] columnNames = { "标签", "asin", "图片URL", "名称", "sku", "价格", "昨天价格", "总评论数", "新增评论数", "buy box",
					"是否是prime(1:是,0:否)", "asin(我的/竞品)" };
			ce.execute1(response, list1, keys, columnNames, "sku列表下载");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_buyboxofferAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_buyboxofferAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_buyboxofferAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_buyboxofferAnalysis_CategoryRank", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_buyboxofferAnalysis_CategoryRank(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_buyboxofferAnalysis_CategoryRank(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_buyboxofferChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_buyboxofferChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_buyboxofferChange(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_offernum", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_offernum(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String asin = getRequestParams(request).get("asin").toString();
			List<Object> list = skuCommonInfoMapper.Amazon_SKUAnalysis_Operation_offernum(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_offerlist", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_offerlist(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String asin = getRequestParams(request).get("asin").toString();
			List<Object> list = skuCommonInfoMapper.Amazon_SKUAnalysis_Operation_offerlist(asin);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_offerTotal", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_offerTotal(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper.Amazon_SKUAnalysis_Operation_offerTotal(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_offerChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_offerChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper.Amazon_SKUAnalysis_Operation_offerChange(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis_PriceTrend", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis_PriceTrend(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis_PriceTrend(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_pinglun", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_pinglun(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_pinglun(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_avaStar", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_avaStar(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_avaStar(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_InsertavaStar", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_InsertavaStar(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_InsertavaStar(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_MonitoringOverview_CategoryRankings_new", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_CategoryRankings_new(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuCommonInfoMapper
					.Amazon_MonitoringOverview_CategoryRankings_new(getRequestParams(request));

			resultMap.put("data", list);

			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_MonitoringOverview_CategoryRankings_new(map);
			resultMap.put("total", total);

			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_MonitoringOverview_CategoryRankings_Return", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_CategoryRankings_Return(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuCommonInfoMapper
					.Amazon_MonitoringOverview_CategoryRankings_Return(getRequestParams(request));

			resultMap.put("data", list);

			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_MonitoringOverview_CategoryRankings_Trigger", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_CategoryRankings_Trigger(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuCommonInfoMapper
					.Amazon_MonitoringOverview_CategoryRankings_Trigger(getRequestParams(request));

			resultMap.put("data", list);

			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_NewComments_Trigger", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_Monitoring_Intelligence_NewComments_Trigger(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			List<Object> list = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_NewComments_Trigger(map);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_NewComments_Trigger(map);
			resultMap.put("data", list);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			List<Object> list = skuCommonInfoMapper.Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails(map);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails(map);
			resultMap.put("data", list);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			List<Object> list = skuCommonInfoMapper.Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat(map);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper
					.Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat(map);
			resultMap.put("data", list);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat_downLoad", method = RequestMethod.GET)
	public void Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat_downLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<Object, Object>> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat_downLoad(paramMap);
			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			String[] keys = { "sku_rwNum", "xinzeng_pinglun", "day_sku_fiveStarNum", "day_sku_fourStarNum",
					"day_sku_threeStarNum", "day_sku_twoStarNum", "day_sku_oneStarNum", "main_avaStar",
					"year_month_day" };
			String[] columnNames = { "总评论", "新增评论", "新增5星", "新增4星", "新增3星", "新增2星", "新增1星", "总得分", "时间" };
			ce.execute1(response, list, keys, columnNames, "评论统计");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails_downLoad", method = RequestMethod.GET)
	public void Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails_downLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<Object, Object>> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails_downLoad(paramMap);
			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			String[] keys = { "customerName", "inserttime", "star", "helpefulNum", "reviewTitle", "reviewContent",
					 "verifiedPurchase" };
			String[] columnNames = { "用户名", "评论日期", "星", "是否有帮助", "标题", "内容",  "是否购买" };
			ce.execute1(response, list, keys, columnNames, (paramMap.get("asin").toString()==""?"AllSKU":paramMap.get("asin").toString())+"_评论详情_"+new Date().getTime());
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(value = "/seachAsin", method = RequestMethod.POST)
	@ResponseBody
	public Object seachAsin(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			if (Integer.parseInt(map.get("type").toString()) == 2) {
				Long count = skuInfoMapper.seachWhether(getRequestParams(request));
				if (count == 1) {
					List<Object> list = skuInfoMapper.seachAsin(getRequestParams(request));
					resultMap.put("data", list);
					resultMap.put(STATUS, SUCCESS);
				}
				if (count == 0) {
					List<Object> list = skuInfoMapper.seachAsinMe(getRequestParams(request));
					resultMap.put("data", list);
					resultMap.put(STATUS, SUCCESS);
				}
			}
			if (Integer.parseInt(map.get("type").toString()) == 1) {
				List<Object> list = skuInfoMapper.seachAsin(getRequestParams(request));
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			}
			if (Integer.parseInt(map.get("type").toString()) == 0) {
				List<Object> list = skuInfoMapper.seachAsinMe(getRequestParams(request));
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			}
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/zhtai", method = RequestMethod.POST)
	@ResponseBody
	public Object zhtai(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Long count = skuInfoMapper.seachWhether(getRequestParams(request));
			resultMap.put("data", count);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getasinList", method = RequestMethod.POST)
	@ResponseBody
	public Object getasinList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			if (Integer.parseInt(map.get("type").toString()) == 1) {
				List<Object> list = skuInfoMapper.seachAsin(getRequestParams(request));
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			}
			if (Integer.parseInt(map.get("type").toString()) == 0) {
				List<Object> list = skuInfoMapper.seachAsinMe(getRequestParams(request));
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			}
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_SalesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_SalesAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_SalesAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_TotalSalesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_TotalSalesAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_TotalSalesAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_HotSaleList_Channel", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_HotSaleList_Channel(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_HotSaleList_Channel(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_HotSaleList", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_HotSaleList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_HotSaleList(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_CategorySelect", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_CategorySelect(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			Map<String, Object> map = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_CategorySelect(getRequestParams(request));
			map.put("category", JSONObject.fromObject(list.get(0)).get("category"));
			List<Object> list1 = skuInfoMapper.Amazon_SalesAnalysis_CategorySalesAnalysisDate(map);
			/*
			 * for(Object s:list){ map.put("category",
			 * JSONObject.fromObject(s).get("category")); List<Object> list1 =
			 * skuInfoMapper.Amazon_SalesAnalysis_CategorySalesAnalysisDate(map)
			 * ; resultMap1.put(s, list1); }
			 */
			resultMap.put("data", list1);
			resultMap.put("category", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_CategorySalesAnalysisDate", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_CategorySalesAnalysisDate(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_CategorySalesAnalysisDate(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_BrandSalesAnalysisDate", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_BrandSalesAnalysisDate(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_BrandSalesAnalysisDate(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_BrandSalesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_BrandSalesAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_BrandSalesAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_SkuInformation", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_SkuInformation(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_SkuInformation(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_SkuSalesTrend", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_SkuSalesTrend(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_SkuSalesTrend(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_SkuRegionSalesTrendDate", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_SkuRegionSalesTrendDate(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_SkuRegionSalesTrendDate(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_SkuRegionSalesTrend", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_SkuRegionSalesTrend(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_SkuRegionSalesTrend(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_CategorySalesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_CategorySalesAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_CategorySalesAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuInfoMapper.Amazon_SalesAnalysis_CategorySalesAnalysis(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SalesAnalysis_HotRanking", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SalesAnalysis_HotRanking(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_SalesAnalysis_HotRanking(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuInfoMapper.Amazon_SalesAnalysis_HotRanking(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getSelect_HotRanking", method = RequestMethod.POST)
	@ResponseBody
	public Object getSelect_HotRanking(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list1 = skuInfoMapper.Amazon_SalesAnalysis_HotRanking_Select(1);
			List<Object> list2 = skuInfoMapper.Amazon_SalesAnalysis_HotRanking_Select(2);
			List<Object> list3 = skuInfoMapper.Amazon_SalesAnalysis_HotRanking_Select(3);
			resultMap.put("BrandName", list1);
			resultMap.put("category", list2);
			resultMap.put("channel", list3);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_UserAnalysis_HousetimeSalesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_UserAnalysis_HousetimeSalesAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_UserAnalysis_HousetimeSalesAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_UserAnalysis_RegionSalesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_UserAnalysis_RegionSalesAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_UserAnalysis_RegionSalesAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_UserAnalysis_CategorySalesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_UserAnalysis_CategorySalesAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			List<Object> list = skuInfoMapper.Amazon_UserAnalysis_CategorySalesAnalysis(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_PriceChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_Monitoring_Intelligence_PriceChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_PriceChange(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_PriceChange(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_TitleChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_Monitoring_Intelligence_TitleChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_TitleChange(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_TitleChange(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_RankingChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_Monitoring_Intelligence_RankingChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_RankingChange(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			map.put("px", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_RankingChange(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_NewComments", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_Monitoring_Intelligence_NewComments(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_NewComments(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_NewComments(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_PictureChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_Monitoring_Intelligence_PictureChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_PictureChange(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_PictureChange(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_CategoryRankings", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_Monitoring_Intelligence_CategoryRankings(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_CategoryRankings(getRequestParams(request));
			resultMap.put("data", list);
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			List<Object> total = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_CategoryRankings(map);
			resultMap.put("total", total);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_MonitoringRankings", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_MonitoringRankings(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper.Amazon_SKUAnalysis_MonitoringRankings(getRequestParams(request));
			resultMap.put("data", list);

			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getTabTotal", method = RequestMethod.POST)
	@ResponseBody
	public Object getTabTotal(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getRequestParams(request);
			map.put("page", 0);
			map.put("px", 0);
			List<Object> PriceChange = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_PriceChange(map);
			List<Object> TitleChange = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_TitleChange(map);
			List<Object> NewComments = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_NewComments(map);
			List<Object> PictureChange = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_PictureChange(map);
			List<Object> RankingChange = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_RankingChange(map);
			List<Object> CategoryRank = skuCommonInfoMapper.Amazon_Monitoring_Intelligence_CategoryRankings(map);
			resultMap.put("PriceChange", PriceChange);
			resultMap.put("TitleChange", TitleChange);
			resultMap.put("NewComments", NewComments);
			resultMap.put("PictureChange", PictureChange);
			resultMap.put("RankingChange", RankingChange);
			resultMap.put("CategoryRank", CategoryRank);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_PriceChange_downLoad", method = RequestMethod.GET)
	public void Amazon_Monitoring_Intelligence_PriceChange_downLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<Object, Object>> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_PriceChange_downLoad(paramMap);
			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			String[] keys = { "sku_imageUrl", "SKU", "sku_productTitle", "asin", "new_price", "yestday_price" };
			String[] columnNames = { "图片", "SKU", "标题", "ASIN", "新价格", "旧价格" };
			ce.execute1(response, list, keys, columnNames, "price change");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_TitleChange_downLoad", method = RequestMethod.GET)
	public void Amazon_Monitoring_Intelligence_TitleChange_downLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<Object, Object>> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_TitleChange_downLoad(paramMap);
			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			String[] keys = { "sku_imageUrl", "new_title", "yesterday_title", "asin", "SKU" };
			String[] columnNames = { "图片", "新标题", "旧标题", "ASIN", "SKU" };
			ce.execute1(response, list, keys, columnNames, "Title Change");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_RankingChange_downLoad", method = RequestMethod.GET)
	public void Amazon_Monitoring_Intelligence_RankingChange_downLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<Object, Object>> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_RankingChange_downLoad(paramMap);
			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			String[] keys = { "sku_imageUrl", "sku_productTitle", "asin", "SKU", "new_rank", "yestday_rank",
					"paiming_change" };
			String[] columnNames = { "图片", "标题", "ASIN", "SKU", "新排名", "旧排名", "排名变化" };
			ce.execute1(response, list, keys, columnNames, "Ranking Change");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_NewComments_downLoad", method = RequestMethod.GET)
	public void Amazon_Monitoring_Intelligence_NewComments_downLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<Object, Object>> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_NewComments_downLoad(paramMap);
			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			String[] keys = { "sku_imageUrl", "sku_productTitle", "asin", "SKU", "NewComments" };
			String[] columnNames = { "图片", "标题", "ASIN", "SKU", "新增评论数" };
			ce.execute1(response, list, keys, columnNames, "New Review");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_PictureChange_downLoad", method = RequestMethod.GET)
	public void Amazon_Monitoring_Intelligence_PictureChange_downLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<Object, Object>> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_PictureChange_downLoad(paramMap);
			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			String[] keys = { "yestday_sku_imageUrl", "new_sku_imageUrl", "SKU", "sku_productTitle", "asin" };
			String[] columnNames = { "原图片", "新图片", "SKU", "标题", "asin" };
			ce.execute1(response, list, keys, columnNames, "Picture Change");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(value = "/Amazon_Monitoring_Intelligence_CategoryRankings_downLoad", method = RequestMethod.GET)
	public void Amazon_Monitoring_Intelligence_CategoryRankings_downLoad(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<Object, Object>> list = skuCommonInfoMapper
					.Amazon_Monitoring_Intelligence_CategoryRankings_downLoad(paramMap);
			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			String[] keys = { "sku_imageUrl", "sku_productTitle", "categoryName", "now_rank", "yestday_rank", "type" };
			String[] columnNames = { "原图片", "标题", "类目", "当前排名", "前一天排名", "asin", "类型" };
			ce.execute1(response, list, keys, columnNames, "category rank");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * 库存分析-库存管理-列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Amazon_InventoryAnalysis_InventoryManagement", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_InventoryAnalysis_InventoryManagement(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = skuCommonInfoMapper
					.Amazon_InventoryAnalysis_InventoryManagement(getRequestParams(request));

			// 重新组装数据
			List<String> dayList = new ArrayList<String>();
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : list) {
				String day = map.get("inserttime").toString();
				if (!dayList.contains(day)) {
					dayList.add(day);
				}
			}

			int index = 0;
			Map<String, Object> mymap = null;
			for (String currentday : dayList) {
				index = 0;
				mymap = new HashMap<String, Object>();
				for (Map<String, Object> map : list) {
					if (index >= 4) {
						continue;
					}
					if (currentday.equals(map.get("inserttime").toString())) {
						// 把这条记录加入到新的集合中
						if (map.get("source").equals("FBA")) {
							// FBA
							mymap.put("inserttime", currentday);
							mymap.put("FBA_xiaoliang",
									map.get("FBA_xiaoliang") == null || map.get("FBA_xiaoliang").toString().equals("-")
											? "" : map.get("FBA_xiaoliang").toString());
							mymap.put("FBA_huodong",
									map.get("FBA_huodong") == null || map.get("FBA_huodong").toString().equals("-") ? ""
											: map.get("FBA_huodong").toString());
							mymap.put("FBA_other",
									map.get("FBA_other") == null || map.get("FBA_other").toString().equals("-") ? ""
											: map.get("FBA_other").toString());
						}
						if (map.get("source").equals("Vendor")) {
							// Vendor
							mymap.put("Vendor_xiaoliang",
									map.get("Vendor_xiaoliang") == null
											|| map.get("Vendor_xiaoliang").toString().equals("-") ? ""
													: map.get("Vendor_xiaoliang").toString());
							mymap.put("Vendor_huodong",
									map.get("Vendor_huodong") == null
											|| map.get("Vendor_huodong").toString().equals("-") ? ""
													: map.get("Vendor_huodong").toString());
							mymap.put("Vendor_other",
									map.get("Vendor_other") == null || map.get("Vendor_other").toString().equals("-")
											? "" : map.get("Vendor_other").toString());
						}
						if (map.get("source").equals("US")) {
							// US
							mymap.put("US_xiaoliang",
									map.get("US_xiaoliang") == null || map.get("US_xiaoliang").toString().equals("-")
											? "" : map.get("US_xiaoliang").toString());
							mymap.put("US_huodong",
									map.get("US_huodong") == null || map.get("US_huodong").toString().equals("-") ? ""
											: map.get("US_huodong").toString());
							mymap.put("US_other",
									map.get("US_other") == null || map.get("US_other").toString().equals("-") ? ""
											: map.get("US_other").toString());
						}

						if (map.get("source").equals("CN")) {
							// US
							mymap.put("CN_xiaoliang",
									map.get("CN_xiaoliang") == null || map.get("CN_xiaoliang").toString().equals("-")
											? "" : map.get("CN_xiaoliang").toString());
							mymap.put("CN_huodong",
									map.get("CN_huodong") == null || map.get("CN_huodong").toString().equals("-") ? ""
											: map.get("CN_huodong").toString());
							mymap.put("CN_other",
									map.get("CN_other") == null || map.get("CN_other").toString().equals("-") ? ""
											: map.get("CN_other").toString());
						}

						// 判断当天日期是否超过今天，需要做标记。好确定是否需要预测 1-预测，0-过去式
						if ((TimeUtil.dateToStamp2(currentday)
								- TimeUtil.dateToStamp2(TimeUtil.getDayBefore(0))) >= 0) {
							mymap.put("isYuCe", "1");
						} else {
							mymap.put("isYuCe", "0");
						}

						index++;
					}
				}

				// 加入之前再判断
				if (mymap.get("isYuCe").equals("1")) {
					if (!"".equals(mymap.get("FBA_huodong").toString())
							|| !"".equals(mymap.get("FBA_other").toString())) {
						// 其中有一个不为空，那么就是已经人工干预过的
						mymap.put("FBA_hasganyu", "1");
					} else {
						mymap.put("FBA_hasganyu", "0");
					}

					if (!"".equals(mymap.get("Vendor_huodong").toString())
							|| !"".equals(mymap.get("Vendor_other").toString())) {
						// 其中有一个不为空，那么就是已经人工干预过的
						mymap.put("Vendor_hasganyu", "1");
					} else {
						mymap.put("Vendor_hasganyu", "0");
					}

					if (!"".equals(mymap.get("US_huodong").toString())
							|| !"".equals(mymap.get("US_other").toString())) {
						// 其中有一个不为空，那么就是已经人工干预过的
						mymap.put("US_hasganyu", "1");
					} else {
						mymap.put("US_hasganyu", "0");
					}

					if (!"".equals(mymap.get("CN_huodong").toString())
							|| !"".equals(mymap.get("CN_other").toString())) {
						// 其中有一个不为空，那么就是已经人工干预过的
						mymap.put("CN_hasganyu", "1");
					} else {
						mymap.put("CN_hasganyu", "0");
					}
				}

				dataList.add(mymap);

			}

			resultMap.put("data", dataList);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 库存分析-库存管理-列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Amazon_InventoryAnalysis_InventoryManagement2", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_InventoryAnalysis_InventoryManagement2(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = skuCommonInfoMapper
					.Amazon_InventoryAnalysis_InventoryManagement(getRequestParams(request));

			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_InventoryAnalysis_Forecast_Purchase", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_InventoryAnalysis_Forecast_Purchase(HttpServletRequest request, Integer page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper.Amazon_InventoryAnalysis_Forecast_Purchase(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 库存分析-库存管理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/set_salesInfo_user", method = RequestMethod.POST)
	@ResponseBody
	public Object set_salesInfo_user(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			Map<String, Object> paramMap = getRequestParams(request);

			if (paramMap.get("source").equals("FBA")) {
				paramMap.put("Vendor_huodong", "");
				paramMap.put("Vendor_other", "");
				paramMap.put("US_huodong", "");
				paramMap.put("US_other", "");
				paramMap.put("CN_huodong", "");
				paramMap.put("CN_other", "");
			}
			if (paramMap.get("source").equals("Vendor")) {
				paramMap.put("FBA_huodong", "");
				paramMap.put("FBA_other", "");
				paramMap.put("US_huodong", "");
				paramMap.put("US_other", "");
				paramMap.put("CN_huodong", "");
				paramMap.put("CN_other", "");
			}
			if (paramMap.get("source").equals("US")) {
				paramMap.put("FBA_huodong", "");
				paramMap.put("FBA_other", "");
				paramMap.put("Vendor_huodong", "");
				paramMap.put("Vendor_other", "");
				paramMap.put("CN_huodong", "");
				paramMap.put("CN_other", "");
			}
			if (paramMap.get("source").equals("CN")) {
				paramMap.put("FBA_huodong", "");
				paramMap.put("FBA_other", "");
				paramMap.put("Vendor_huodong", "");
				paramMap.put("Vendor_other", "");
				paramMap.put("US_huodong", "");
				paramMap.put("US_other", "");
			}

			Map<String, Object> data = skuCommonInfoMapper.select_salesInfo_user(paramMap);
			if (data == null) {
				// 那么新增一行记录
				skuCommonInfoMapper.insert_salesInfo_user(paramMap);
			} else {
				// 那么更新记录
				skuCommonInfoMapper.update_salesInfo_user(paramMap);
			}

			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/addskumanage", method = RequestMethod.POST)
	@ResponseBody
	public Object addskumanage(HttpServletRequest request, Integer isMain, String asin, String sku, String groupSKU,
			String category, String AmzBrand, String dsasins, Integer isOurBrand) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String[] ds_asin = dsasins.split(",");
			int count = 0;
			Map<String, Object> paramMap = getRequestParams(request);
			paramMap.put("insertTime", new Date());
			for (String s : ds_asin) {
				paramMap.put("compareOurAsin", s);
				int i = skuInfoMapper.addSku(paramMap);
				count = count + i;
			}
			resultMap.put("data", "成功添加" + count + "个！");
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getSkumanage", method = RequestMethod.POST)
	@ResponseBody
	public Object getSkumanage(HttpServletRequest request, Integer page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			paramMap.put("index", page * 20);
			paramMap.put("page", page);
			List<Object> list = skuInfoMapper.getSkumanage(paramMap);
			Long count = skuInfoMapper.getCount(paramMap);
			resultMap.put("data", list);
			resultMap.put("num", count);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/delsku", method = RequestMethod.POST)
	@ResponseBody
	public Object delsku(HttpServletRequest request, Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			paramMap.put("userid", request.getSession().getAttribute("userid").toString());
			int i = skuInfoMapper.deleteByPrimaryKey(id);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/setSku_Asin", method = RequestMethod.POST)
	@ResponseBody
	public Object setSku_Asin(HttpServletRequest request, Long id, String dsasins) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			paramMap.put("userid", request.getSession().getAttribute("userid").toString());
			SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey2(id);
			List<Object> list = skuInfoMapper.getSkuinfo(paramMap);

			SkuInfo sk = new SkuInfo();
			String[] ds_asin = dsasins.split(",");
			int count = 0;
			for (String s : ds_asin) {
				sk.setAsin(s);
				sk.setCompareourasin(paramMap.get("asin").toString());
				sk.setIsmain("0");

				// 判断该条记录是否已经存在，若存在则跳过
				Map<String, Object> m = skuInfoMapper.checkHas(sk);
				if (m == null) {
					// 不存在才添加
					int i = skuInfoMapper.insertSelective(sk);
					count = count + i;
				}

			}
			resultMap.put("data", "成功添加" + count + "个！");
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_SalesAnalysis_PlatformSalesAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_SalesAnalysis_PlatformSalesAnalysis(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_SalesAnalysis_PlatformSalesAnalysis(getRequestParams(request));
			resultMap.put("data", list);

			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_SalesAnalysis_Trend", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_SalesAnalysis_Trend(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_SalesAnalysis_Trend(getRequestParams(request));
			resultMap.put("data", list);

			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_DetailedData", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_SKUAnalysis_Operation_DetailedData(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_DetailedData(getRequestParams(request));
			resultMap.put("data", list);

			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/Amazon_SKUAnalysis_Operation_DetailedData_dowload", method = RequestMethod.GET)
	public void Amazon_SKUAnalysis_Operation_DetailedData_dowload(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper
					.Amazon_SKUAnalysis_Operation_DetailedData_dowload(paramMap);
			Map<String, Object> m = list.get(0);
			String[] keys = new String[150];
			String[] columnNames = new String[150];
			columnNames[0] = "时间";
			columnNames[1] = "总计";
			keys[0] = "datatime";
			keys[1] = "xiaoliang";
			int count = 2, count1 = 2;
			for (Entry<String, Object> entry : m.entrySet()) {
				if (!entry.getKey().equals("datatime") && !entry.getKey().equals("xiaoliang")) {
					keys[count++] = entry.getKey();
					columnNames[count1++] = entry.getKey();
				}

			}

			// List<Map<String, Object>> list =
			// monitorService.querySaleSku(egoodsId, skuId, statTime, endtime);
			CommonExport ce = new CommonExport();
			// String[] keys =
			// {"sku_rwNum","xinzeng_pinglun","day_sku_fiveStarNum","day_sku_fourStarNum","day_sku_threeStarNum","day_sku_twoStarNum","day_sku_oneStarNum","main_avaStar","year_month_day"};
			// String[] columnNames = { "总评论" ,
			// "新增评论","新增5星","新增4星","新增3星","新增2星","新增1星","总得分","时间"};
			ce.execute(response, list, keys, columnNames, "销量分析-详细数据下载");
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	
	@RequestMapping(value = "/selectProductListByLabel", method = RequestMethod.POST)
	@ResponseBody
	public Object selectProductListByLabel(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = skuCommonInfoMapper.selectProductListByLabel(getRequestParams(request));
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/getCategoryInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object getCategoryInfo(HttpServletRequest request, Integer page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.getCategoryInfo(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/getCategoryInfo_parent", method = RequestMethod.POST)
	@ResponseBody
	public Object getCategoryInfo_parent(HttpServletRequest request, String parentName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			paramMap.put("parentName", parentName);
			List<Object> list = skuInfoMapper.getCategoryInfo_parent(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/updateParentCategory", method = RequestMethod.POST)
	@ResponseBody
	public Object updateParentCategory(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			HttpSession session=request.getSession();
			String accountType=session.getAttribute("accountType").toString();
			if(Integer.valueOf(accountType)==1){
				Map<String, Object> paramMap = getRequestParams(request);
				int i = skuInfoMapper.updateParentCategory(paramMap);
				resultMap.put("data", i);
				resultMap.put(STATUS, SUCCESS);
			}else{
				resultMap.put(STATUS, FAIL);
				resultMap.put(MSG, "No authority！");
			}
			
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/deleteparentCategory", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteparentCategory(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			HttpSession session=request.getSession();
			String accountType=session.getAttribute("accountType").toString();
			if(Integer.valueOf(accountType)==1){
				Map<String, Object> paramMap = getRequestParams(request);
				int i = skuInfoMapper.deleteparentCategory(paramMap);
				resultMap.put("data", i);
				resultMap.put(STATUS, SUCCESS);
			}else{
				resultMap.put(STATUS, FAIL);
				resultMap.put(MSG, "No authority！");
			}
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	@ResponseBody
	public Object addCategory(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			HttpSession session=request.getSession();
			String accountType=session.getAttribute("accountType").toString();
			if(Integer.valueOf(accountType)==1){
				Map<String, Object> paramMap = getRequestParams(request);
				int i = skuInfoMapper.addCategory(paramMap);
				resultMap.put("data", i);
				resultMap.put(STATUS, SUCCESS);
			}else{
				resultMap.put(STATUS, FAIL);
				resultMap.put(MSG, "No authority！");
			}
			
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/Amazon_MonitoringOverview_SKU_Brand", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_SKU_Brand(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_SKU_Brand(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_SKU", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_SKU(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_SKU(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_SKU_Jingpin", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_SKU_Jingpin(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_SKU_Jingpin(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_PriceChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_PriceChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_PriceChange(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_TitleChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_TitleChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_TitleChange(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/getSkuCompareInfoByAsin", method = RequestMethod.POST)
	@ResponseBody
	public Object getSkuCompareInfoByAsin(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.getSkuCompareInfoByAsin(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_RankingChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_RankingChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_RankingChange(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_NewReview", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_NewReview(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_NewReview(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_PictureChange", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_PictureChange(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_PictureChange(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_Category", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_Category(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_Category(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_PriceOverview", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_PriceOverview(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.Amazon_MonitoringOverview_PriceOverview(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 获取最佳卖家树型目录
	 * @param request
	 * @return
     */
	@RequestMapping(value = "/Amazon_MonitoringOverview_BestSellerCategory", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_BestSellerCategory(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			Map<String, Object> retMap = skuAnalysisServiceImpl.Amazon_MonitoringOverview_BestSellerCategory(paramMap);
			resultMap.put("data", retMap.get("data"));
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 获取最佳卖家Top数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Amazon_MonitoringOverview_BestSellerTop", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_BestSellerTop(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			Map<String, Object> retMap = skuAnalysisServiceImpl.Amazon_MonitoringOverview_BestSellerTop(paramMap);
			resultMap.put("data", retMap.get("data"));
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 获取最佳卖家Top详细数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Amazon_MonitoringOverview_BestSellerTopDetail", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_BestSellerTopDetail(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			Map<String, Object> retMap = skuAnalysisServiceImpl.Amazon_MonitoringOverview_BestSellerTopDetail(paramMap);
			resultMap.put("data", retMap.get("data"));
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 更新卖家目录信息(目前应用场景只允许更新是否监控字段)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Amazon_MonitoringOverview_UpdBestSellerCategory", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_UpdBestSellerCategory(HttpServletRequest request,String newRow,String oldRow) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);

			JSONObject newData=JSONObject.fromObject(newRow);
			JSONObject oldData=JSONObject.fromObject(oldRow);
			// 新数据
			paramMap.put("CategoryId", newData.get("CategoryId"));
			paramMap.put("CategoryName", newData.get("CategoryName"));
			paramMap.put("ChildNum", newData.get("ChildNum"));
			paramMap.put("ParentId", newData.get("ParentId"));
			paramMap.put("SearchKey", newData.get("SearchKey"));
			paramMap.put("CategoryLevel", newData.get("CategoryLevel"));
			paramMap.put("IsMonitoring", newData.get("IsMonitoring"));
			// 旧数据
			paramMap.put("oldCategoryId", oldData.get("CategoryId"));
			paramMap.put("oldCategoryName", oldData.get("CategoryName"));
			paramMap.put("oldChildNum", oldData.get("ChildNum"));
			paramMap.put("oldParentId", oldData.get("ParentId"));
			paramMap.put("oldSearchKey", oldData.get("SearchKey"));
			paramMap.put("oldCategoryLevel", oldData.get("CategoryLevel"));
			paramMap.put("oldIsMonitoring", oldData.get("IsMonitoring"));


			int result = skuAnalysisServiceImpl.Amazon_MonitoringOverview_UpdBestSellerCategory(paramMap);
			resultMap.put("data", result);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/new_getLable", method = RequestMethod.POST)
	@ResponseBody
	public Object new_getLable(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Object> l=new ArrayList<>();
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list = skuInfoMapper.new_getLable(paramMap);
			for(Object s:list){
				@SuppressWarnings("unchecked")
				Map<String, Object> m=(Map<String, Object>) s;
				paramMap.put("labelName", m.get("labelName"));
				List<Object> list1 = skuInfoMapper.product_getLable(paramMap);
				if(list1.size()==0){
					Map<String, Object> o=new HashMap<String, Object>();
					o.put("labelName", m.get("labelName"));
					o.put("num", 0);
					l.add(o);
				}else{
					l.add(list1.get(0));
				}
				
			}
			resultMap.put("data", l);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/new_addlable", method = RequestMethod.POST)
	@ResponseBody
	public Object new_addlable(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.new_addlable(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/new_deletelabel", method = RequestMethod.POST)
	@ResponseBody
	public Object new_deletelabel(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.new_deletelabel(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/new_editlabel", method = RequestMethod.POST)
	@ResponseBody
	public Object new_editlabel(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.new_editasinlabel(paramMap);
			int i1 = skuInfoMapper.new_editproductlabel(paramMap);
			resultMap.put("asinlabel_count", i);
			resultMap.put("productlabel_count", i1);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/productAddLable", method = RequestMethod.POST)
	@ResponseBody
	public Object productAddLable(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.productAddLable(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/productdelLable", method = RequestMethod.POST)
	@ResponseBody
	public Object productdelLable(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.productdelLable(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/getSkuInfo_new", method = RequestMethod.POST)
	@ResponseBody
	public Object getSkuInfo_new(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list= skuInfoMapper.Amazon_MonitoringOverview_Product(paramMap);
			return list;
		} catch (Exception e) {
			return "[{fail:\""+e.getMessage()+"\"}]";
		}
	}
	@RequestMapping(value = "/getParentCategoryInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object getParentCategoryInfo(HttpServletRequest request, String parentName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			paramMap.put("parentName", parentName);
			List<Object> list = skuInfoMapper.getCategoryInfo_parent(paramMap);
			return list;
		} catch (Exception e) {
			return "[{fail:\""+e.getMessage()+"\"}]";
		}
	}
	
	
	@RequestMapping(value = "/updateProduct_skuInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object updateProduct_skuInfo(HttpServletRequest request,String newRow,String oldRow) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			JSONObject newData=JSONObject.fromObject(newRow);
			JSONObject oldData=JSONObject.fromObject(oldRow);
			paramMap.put("ourCode", newData.get("ourCode"));
			paramMap.put("groupLeadSku", newData.get("groupLeadSku"));
			paramMap.put("asin", newData.get("asin"));
			paramMap.put("name", newData.get("name"));
			paramMap.put("productManager_id", newData.get("productManager_id"));
			paramMap.put("productManager", newData.get("productManager"));
			paramMap.put("RootCategory", newData.get("RootCategory"));
			paramMap.put("SubCategory", newData.get("SubCategory"));
			paramMap.put("ownbrand", newData.get("ownbrand"));
			paramMap.put("ProductLine", ObjectUtil.isEmpty(newData.get("ProductLine"))?"":newData.get("ProductLine"));
			//old
			paramMap.put("oldData_ourCode", oldData.get("ourCode"));
			paramMap.put("oldData_groupLeadSku", oldData.get("groupLeadSku"));
			paramMap.put("oldData_asin", oldData.get("asin"));
			paramMap.put("oldData_name", oldData.get("name"));
			paramMap.put("oldData_productManager_id", oldData.get("productManager_id"));
			paramMap.put("oldData_productManager", oldData.get("productManager"));
			paramMap.put("oldData_RootCategory", oldData.get("RootCategory"));
			paramMap.put("oldData_SubCategory", oldData.get("SubCategory"));
			paramMap.put("oldData_ownbrand", oldData.get("ownbrand"));
			paramMap.put("oldData_ProductLine", oldData.get("ProductLine"));


			int i = skuInfoMapper.updateProduct_skuInfo(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/insertProduct_skuInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object insertProduct_skuInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.insertProduct_skuInfo(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/addSkuCompareInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object addSkuCompareInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.addSkuCompareInfo(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/Amazon_MonitoringOverview_ProductJingping", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_ProductJingping(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list= skuInfoMapper.Amazon_MonitoringOverview_ProductJingping(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	

	@RequestMapping(value = "/Amazon_MonitoringOverview_SKU_SimilarProducts", method = RequestMethod.POST)
	@ResponseBody
	public Object Amazon_MonitoringOverview_SKU_SimilarProducts(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list= skuInfoMapper.Amazon_MonitoringOverview_SKU_SimilarProducts(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	

	@RequestMapping(value = "/getOwnBrand", method = RequestMethod.POST)
	@ResponseBody
	public Object getOwnBrand(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list= skuInfoMapper.getOwnBrand(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/getOwnBrand_product", method = RequestMethod.POST)
	@ResponseBody
	public Object getOwnBrand_product(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Object> list= skuInfoMapper.getOwnBrand(paramMap);
			return list;
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
			return resultMap;
		}
	}
	@RequestMapping(value = "/addOwnBrand", method = RequestMethod.POST)
	@ResponseBody
	public Object addOwnBrand(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.addOwnBrand(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/delOwnBrand", method = RequestMethod.POST)
	@ResponseBody
	public Object delOwnBrand(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			int i = skuInfoMapper.delOwnBrand(paramMap);
			resultMap.put("data", i);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	
	
	
	
	@RequestMapping(value = "/update_Delta_LA_Info")
	@ResponseBody
	public Object update_Delta_LA_Info(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			skuCommonInfoMapper.update_Delta_LA_Info(paramMap);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/update_Delta_Vendor_Info")
	@ResponseBody
	public Object update_Delta_Vendor_Info(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			skuCommonInfoMapper.update_Delta_Vendor_Info(paramMap);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/update_sku_CNLeadTime_map")
	@ResponseBody
	public Object update_sku_CNLeadTime_map(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			skuCommonInfoMapper.update_sku_CNLeadTime_map(paramMap);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/select_sku_CNLeadTime_map")
	@ResponseBody
	public Object select_sku_CNLeadTime_map(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper.select_sku_CNLeadTime_map(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	
	/**
	 * 获取sku其他  单品 套装信息，还有基础设置数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSkuSomeInfo")
	@ResponseBody
	public Object getSkuSomeInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list1 = skuCommonInfoMapper.select_other_Delta(paramMap);
			List<Map<String, Object>> list2 = skuCommonInfoMapper.select_danpin_Delta(paramMap);
			List<Map<String, Object>> list3 = skuCommonInfoMapper.select_taozhuang_Delta(paramMap);
			List<Map<String, Object>> list4 = skuCommonInfoMapper.gettaozhuangAlert(paramMap);
			List<Map<String, Object>> skuSetInfoList = skuCommonInfoMapper.select_sku_CNLeadTime_map(paramMap);
			
			Map<String, Object> skuBaseInfo = skuCommonInfoMapper.selectSkuBaseInfo(paramMap);
			
			resultMap.put("list1", list1);
			resultMap.put("list2", list2);
			resultMap.put("list3", list3);
			resultMap.put("list4", list4);
			resultMap.put("skuSetInfo", skuSetInfoList);
			resultMap.put("skuBaseInfo", skuBaseInfo);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	
	/**
	 * 更新sku其他  单品  yuce_num
	 * @param request   type   yuce_num   sku  yuce_month
	 * @return
	 */
	@RequestMapping(value = "/update_Delta")
	@ResponseBody
	public Object update_Delta(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);//
			String type = paramMap.get("type").toString();
			if (type.equals("其他")) {
				skuCommonInfoMapper.update_Delta_LA_Info(paramMap);
			}else if(type.equals("单品")){
				skuCommonInfoMapper.update_Delta_Vendor_Info(paramMap);
			}
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 获取Sku历史数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSkuData_History")
	@ResponseBody
	public Object getSkuData_History(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper.Amazon_InventoryAnalysis_Forecast_History(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 获取Sku预测数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSkuData_Forecast")
	@ResponseBody
	public Object getSkuData_Forecast(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper.Amazon_InventoryAnalysis_Forecast_SkuCombo(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 人工新增订单   inputQty=2&PODate=2019-05-28&EstimateReceiveDate=2019-05-28
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insert_LA_PO_Info")
	@ResponseBody
	public Object insert_LA_PO_Info(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			skuCommonInfoMapper.insert_LA_PO_Info(paramMap);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	
	
	
	
	/**
	 * e_deltaOverview 页面，查询维护sku每个月份的指标
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getskusetInfo")
	@ResponseBody
	public Object getskusetInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			
			String type = paramMap.get("type").toString();
			List<Map<String, Object>> list = null;
			if (type.equals("其他")) {
				list = skuCommonInfoMapper.select_other_Delta(paramMap);
			}else if (type.equals("单品")) {
				list = skuCommonInfoMapper.select_danpin_Delta(paramMap);
			}
			
			List<String> skus = new ArrayList<String>();//sku集合
			//准备对list进行行转列
			for (Map<String, Object> map : list) {
				String sku = map.get("sku").toString();
				if (!skus.contains(sku)) {
					skus.add(sku);
				}
			}
			
			List<Map<String, Object>> newlist = new ArrayList<Map<String,Object>>();
			Map<String, Object> mymap = null;
			for (String currentsku : skus) {
				mymap=new HashMap<String,Object>();
				int count=0;
				for (Map<String, Object> map : list) {
					String sku = map.get("sku").toString();
					String yuce_month = map.get("yuce_month").toString();
					String yuce_num = map.get("yuce_num").toString();
					String month_en = map.get("month_en").toString();
					if (currentsku.equals(sku)) {
						count++;
						mymap.put("sku", currentsku);
//						mymap.put(yuce_month, yuce_month);
						mymap.put("yuce_num_"+String.valueOf(count<10?("0"+count):count), yuce_num);
//						mymap.put(month_en, month_en);
					}
				}
				newlist.add(mymap);
			}
			
			
			resultMap.put("data", newlist);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	
	
	/**
	 * 人工维护sku每个月份的值
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveSkuDelta")
	@ResponseBody
	public Object saveSkuDelta(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			String type = paramMap.get("type").toString();
			if (type.equals("其他")) {
				//先查询改记录是否存在，不存在写入，存在更新
				List<Map<String, Object>> data = skuCommonInfoMapper.select_other_Delta(paramMap);
				if (data.size()>0) {
					//说明存在，只要更新记录
					skuCommonInfoMapper.update_Delta_LA_Info(paramMap);
				}else {
					skuCommonInfoMapper.insert_other_Delta(paramMap);
				}
				
			}else if (type.equals("单品")) {
				
			}
			
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	
	
	/**
	 * sku预测页面弹框搜索数据 type
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getToggleData")
	@ResponseBody
	public Object getToggleData(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			
			String className = paramMap.get("className").toString();
			if (className.equals("US_Combo")) {
				paramMap.put("type", "1");
			}else if (className.equals("US_IN")) {
				paramMap.put("type", "2");
			}else if (className.equals("FBA_IN")) {
				paramMap.put("type", "3");
			}else if (className.equals("Vendor_IPOCF")) {
				paramMap.put("type", "4");
			}else if (className.equals("Vendor_IPOWT")) {
				paramMap.put("type", "5");
			}
			List<Map<String, Object>> list = skuCommonInfoMapper.getToggleData(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * SkuEndPos外链页面
	 * @param request
	 * @return
	 * @version V2
	 */
	@RequestMapping(value = "/getSkuEndPosV2")
	@ResponseBody
	public Object getSkuEndPosV2(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			resultMap=inventroryAnalysisServiceForEndPosImpl.generateAnalysisData(paramMap);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	
	/**
	 * SkuEndPos外链页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSkuEndPos")
	@ResponseBody
	public Object getSkuEndPos(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper.getSkuEndPos(paramMap);
			
			
			List<String> dates = new ArrayList<String>();
				//数据重组
				for (Map<String, Object> map : list) {
					String year_month = map.get("year").toString()+"W"+map.get("WEEK_DAY").toString();
					if (!dates.contains(year_month)) {
						dates.add(year_month);
					}
			}
			
			
			List<String> skus = new ArrayList<String>(); 
			//数据重组
			for (Map<String, Object> map : list) {
				String orderItemSku = map.get("orderItemSku").toString();
				if (!skus.contains(orderItemSku)) {
					skus.add(orderItemSku);
				}
			}
			
			List<Map<String, Object>> newlist = new ArrayList<Map<String,Object>>();
			
			Map<String, Object> mymap = null;
			for (String date : dates) {
				mymap = getTargetData(date, skus, list);
				newlist.add(mymap);
			}
			
			
			
			resultMap.put("skus", skus);
			resultMap.put("data", newlist);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	
	
	private Map<String, Object> getTargetData(String date,List<String> skus,List<Map<String, Object>> list){
		//从list中找出和date一样的记录，然后计算相关的数据
		Map<String, Object> mymap  = new HashMap<String,Object>();//这个是要返回的map
		int total=0;int SAmz_sales = 0;int So_sales=0;
		for (Map<String, Object> map : list) {
			String year_month = map.get("year").toString()+"W"+map.get("WEEK_DAY").toString();
			if (date.equals(year_month)) {
				int SAmz = Integer.parseInt(map.get("SAmz_sales").toString());
				int So = Integer.parseInt(map.get("So_sales").toString());
				SAmz_sales=SAmz_sales+SAmz;
				So_sales=So_sales+So;
				total=total+SAmz+So;
			}
		}
		mymap.put("year_week", date);
		mymap.put("total", total);
		mymap.put("SAmz_sales", SAmz_sales);
		mymap.put("So_sales", So_sales);
		
		List<Integer> data = new ArrayList<Integer>();
		for (String sku : skus) {
			int num = 0;
			boolean flag = false;
			for (Map<String, Object> map : list) {
				String year_month = map.get("year").toString()+"W"+map.get("WEEK_DAY").toString();
				String orderItemSku = map.get("orderItemSku").toString();
				int a = Integer.parseInt(map.get("SAmz_sales").toString());
				if (date.equals(year_month)&&sku.equals(orderItemSku)) {
					flag=true;
					num=num+a;
				}
			}
			if (!flag) {
				num=num+0;
			}
			
			data.add(num);
			
		}
		
		mymap.put("arr", data);
		
		
		return mymap;
	}
	
	
	/**
	 * skuOverView总览页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSkuOverView")
	@ResponseBody
	public Object getSkuOverView(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper.getSkuOverView(paramMap);
			List<String> dates = new ArrayList<String>(); 
			//数据重组
			for (Map<String, Object> map : list) {
				String insert_time = map.get("insert_time").toString();
				if (!dates.contains(insert_time)) {
					dates.add(insert_time);
				}
			}
			List<String> skus = new ArrayList<String>(); 
			//数据重组
			for (Map<String, Object> map : list) {
				String ItemNum = map.get("ItemNum").toString();
				if (!skus.contains(ItemNum)) {
					skus.add(ItemNum);
				}
			}
			List<Map<String, Object>> newlist = new ArrayList<Map<String,Object>>();
			
			
			
			
			
			
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 产品线产品获取数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Amazon_InventoryAnalysis_Forecast_ProductLineSKU")
	@ResponseBody
	public Object Amazon_InventoryAnalysis_Forecast_ProductLineSKU(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper.Amazon_InventoryAnalysis_Forecast_ProductLineSKU(paramMap);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 获取总产品线列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Amazon_InventoryAnalysis_Forecast_ProductLineList")
	@ResponseBody
	public Object Amazon_InventoryAnalysis_Forecast_ProductLineList(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getRequestParams(request);
			List<Map<String, Object>> list = skuCommonInfoMapper.Amazon_InventoryAnalysis_Forecast_ProductLineList(paramMap);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

}
