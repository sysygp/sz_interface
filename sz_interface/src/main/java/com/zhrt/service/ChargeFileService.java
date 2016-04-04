package com.zhrt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.util.date.DateUtil;
import com.zhrt.bo.InfoReqVO;
import com.zhrt.bo.InfoResVO;
import com.zhrt.entity.ChannelAppInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.PropInfo;
import com.zhrt.entity.Spcode;
import com.zhrt.entity.SpcodeLimit;
import com.zhrt.entity.UserLimit;
import com.zhrt.entity.Userinfo;
import com.zhrt.util.CheckSpcodeAndUserLimit;
import com.zhrt.util.IpUtilTaobao;
import com.zhrt.util.PhoneUtil;
import com.zhrt.util.UpdateSpcodeAndUserLimit;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class ChargeFileService {
	private static Logger logger = LoggerFactory.getLogger(ChargeFileService.class);
	
	@Autowired
	private ChannelAppInfoService channelAppInfoService;
	@Autowired
	private PropInfoService propInfoService;
	@Autowired
	private PropSpcodeService propSpcodeService;
	@Autowired
	private UserLimitService userLimitService;
	@Autowired
	private LatnService latnService;
	
	
	@Transactional
	public List<InfoResVO> getChargeFile(InfoReqVO infoReqVO,Integer chanAppVerSeq,Userinfo userinfo){
		try {
			ChannelAppInfo channelAppInfo = channelAppInfoService.getByChanAppVerSeq(chanAppVerSeq);
			if(channelAppInfo != null){
				return getChargeFile(infoReqVO,channelAppInfo.getChannelId(),channelAppInfo.getAppId(),chanAppVerSeq,userinfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	public List<InfoResVO> getChargeFile(InfoReqVO infoReqVO,String channelId,String appId,Integer chanAppVerSeq,Userinfo userinfo){
		List<InfoResVO> infoResVOList = new ArrayList<InfoResVO>();
		try {
			String tel = "";
			String province = "";
			String userUuid = "";
			String operatorType = "1";
			if(userinfo != null){
				tel = userinfo.getTel();
				userUuid = userinfo.getUuid();
				operatorType = PhoneUtil.getOperatorTypeByPhone(tel);
				province = userinfo.getProvince();
				
				if(StringUtils.isBlank(province)){
					String provinceName = IpUtilTaobao.getProNameByIp(infoReqVO.getReqIp());
					if(StringUtils.isNotBlank(provinceName)){
						if(provinceName.endsWith("市") || provinceName.endsWith("省")){
							provinceName = provinceName.substring(0, provinceName.length()-1);
						}
					}
					province = latnService.getProidByProName(provinceName)+"";
				}
			}else{
				String provinceName = IpUtilTaobao.getProNameByIp(infoReqVO.getReqIp());
				if(StringUtils.isNotBlank(provinceName)){
					if(provinceName.endsWith("市") || provinceName.endsWith("省")){
						provinceName = provinceName.substring(0, provinceName.length()-1);
					}
				}
				province = latnService.getProidByProName(provinceName)+"";
				userUuid = infoReqVO.getUuid();
				operatorType = "1";
			}
			
			
			if(StringUtils.isNotBlank(appId)){
				
				//用户限制
				UserLimit userLimit = userLimitService.get();
				
				//查询出该产品下所有道具
				List<PropInfo> propList = propInfoService.getPropsByAppId(appId);
				
				if(propList != null && propList.size() > 0){					
					
					//查询出每个道具分配的计费通道
					List<Spcode> spcodeAllVali = new LinkedList<Spcode>();
					for(PropInfo propInfo : propList){
						String sql = "select s.* from infospcode s where "
								+ " s.operatorType="+operatorType+" and "
								+ " s.id in (select ps.spcodeId from infopropspcode ps where ps.propId='"+propInfo.getId()+"')";
						List<Spcode> spcodeList = propSpcodeService.dynamicSql(sql);
						spcodeAllVali.addAll(spcodeList);
					}
					
					String imsiP = "";
					String imeiP = "";
					if(infoReqVO != null){
						imsiP = infoReqVO.getImsi();
						imeiP = infoReqVO.getImei();
					}
					
					//组装计费方案
					for(Spcode spcode : spcodeAllVali){
						
						//该通道限制
						boolean ifOk = CheckSpcodeAndUserLimit.check( spcode, userLimit, userUuid,province);
						
						if(ifOk){
							InfoResVO infoResVO = new InfoResVO();
							
							//0：注册  1：短信计费  2：wap计费 3：sdk计费 4：pc联网
							infoResVO.setFeeStatus(spcode.getFeeStatus());
							
							if("1".equals(spcode.getFeeStatus())){							
								infoResVO.setFeeCode(spcode.getFeeCode());
							}else if("2".equals(spcode.getFeeCode()) || "4".equals(spcode.getFeeCode())){
								infoResVO.setFeeCode(spcode.getFeeCode().replace("=imsi", "="+imsiP).replace("=imei", "="+imeiP).replace("=phone", "="+tel).replace("=province", "="+province));							
							}
							infoResVO.setFeeNumber(spcode.getFeeNumber());
							infoResVO.setChargeMoney(spcode.getChargeMoney()+"");
							
							
							infoResVO.setInterceptWord(spcode.getInterceptWord());
							infoResVO.setId(userUuid+"#"+spcode.getId()+"#"+chanAppVerSeq);
							infoResVO.setReplyType(spcode.getReplyType());
							
							infoResVO.setReplyContent(spcode.getReplyContent());
							infoResVO.setTel(tel);
							infoResVO.setChargeTimes(spcode.getChargeTimes());
							
							infoResVOList.add(infoResVO);
							
							UpdateSpcodeAndUserLimit.update(spcode, userLimit, userUuid);
						}
					}
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return infoResVOList;
	}
	
}
