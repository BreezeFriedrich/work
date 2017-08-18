/**
 * 
 */
package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.hysm.domain.Characteristic;
import com.hysm.domain.CharacteristicValue;
import com.hysm.domain.HouseType;
import com.hysm.mapper.sqlpro.FeaturePro;

/**
 * @author john
 * 
 */
public interface FeatrueMapper
{

    /**
     * 查询特色民称
     * 
     * @return
     */
    @SelectProvider(method = "queryAllCharacteristic", type = FeaturePro.class)
    @Options(useCache = true)
    public List<Characteristic> queryAllCharacteristic();

    /**
     * 查询特色属性的特色值
     * 
     * @param chara_id
     * @return
     */
    @SelectProvider(method = "queryCharacteristicValue", type = FeaturePro.class)
    public List<CharacteristicValue> queryCharacteristicValue(String chara_id);

    @SelectProvider(method = "queryCharaHouseTypeByCity", type = FeaturePro.class)
    public List<HouseType> queryCharaHouseTypeByCity(@Param("vid")
    String vid, @Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    @SelectProvider(method = "queryCharaHouseType", type = FeaturePro.class)
    public List<HouseType> queryCharaHouseType(@Param("vid")
    String vid, @Param("stime")
    String stime, @Param("etime")
    String etime, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    @SelectProvider(method = "queryCharaHouseTypeByCityNum", type = FeaturePro.class)
    public int queryCharaHouseTypeByCityNum(@Param("vid")
    String vid, @Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime);

    @SelectProvider(method = "queryCharaHouseTypeNum", type = FeaturePro.class)
    public int queryCharaHouseTypeNum(@Param("vid")
    String vid, @Param("stime")
    String stime, @Param("etime")
    String etime);
}
