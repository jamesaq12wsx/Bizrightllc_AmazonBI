package com.analyze.dao;

import com.analyze.model.AmazonAdConsumeItemDO;
import com.analyze.model.AmazonAdConsumeSettingDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:spring-applicationcontext.xml",
        "classpath*:spring-datasource.xml"})
public class AmazonAdConsumeSettingDOMapperTest {

    @Autowired
    private AmazonAdConsumeSettingDOMapper amazonAdConsumeSettingDOMapper;

    @Test
    public void testGetAllSetting() {

        List<AmazonAdConsumeSettingDO> settings = amazonAdConsumeSettingDOMapper.getAllSetting();

        Assert.assertTrue(settings.size() > 0);

    }

    @Test
    public void testGetItemById(){
        AmazonAdConsumeItemDO itemDO = amazonAdConsumeSettingDOMapper.getSettingItemById(2L);
        Assert.assertEquals(Long.valueOf(2), itemDO.getId());
    }
}
