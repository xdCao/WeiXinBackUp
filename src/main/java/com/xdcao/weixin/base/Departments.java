package com.xdcao.weixin.base;

/**
 * @Author: buku.ch
 * @Date: 2019-04-27 12:11
 */


public enum Departments {

    NEI_KE(0,"内科"),
    WAI_KE(1,"外科"),
    ER_BI_HOU_KE(2,"耳鼻喉科"),
    MA_ZUI_ZHONG_XIN(3,"麻醉与围手术医学中心"),
    XIN_SHENG_ER_KE(4,"新生儿科"),
    JIZHEN_YINGZHEN_ZHONGXIN(5,"急诊应诊中心"),
    ER_KE(6,"儿科"),
    ER_TONG_BAOJIAN_ZHONGXIN(7,"儿童保健中心"),
    PI_FU_KE(8,"皮肤科"),
    KOU_QIANG_KE(9,"口腔科"),
    ZHONG_ZHEN_KANGFU_KE(10,"中针康复科"),
    TENG_TONG_LI_LIAO_KE(11,"疼痛理疗科"),
    YAO_JI_KE(12,"药剂科"),
    LIN_CHUANG_SHIYAN_ZHONGXIN(13,"临床试验中心"),
    SHU_XUE_KE(14,"输血科"),
    BING_LI_KE(15,"病理科"),
    FANGSHE_YINGXIANG_KE(16,"放射影像科"),
    CHAOSHEN_YINGXIANG_KE(17,"超声影像科"),
    JIE_RU_ZHONGXIN(18,"介入中心"),
    YILIAO_MEIRONG_KE(19,"医疗美容科"),
    JIANKANG_GUANLI_ZHONGXIN(20,"健康管理中心"),
    XIAO_DU_GONGYING_SHI(21,"消毒供应室"),
    QUAN_KE_YI_XUE_KE(22,"全科医学科"),
    YU_FANG_BAOJIAN_KE(23,"预防保健科"),
    ZHI_MING_ZHUAN_JIA(24,"知名专家特需医疗服务中心"),
    GAN_BU_BAOJIAN_KE(25,"干部保健科(含国际医疗,高层次人才医疗保健)"),
    BING_AN_GUAN_LI_KE(26,"病案管理科"),
    YAN_KE(27,"眼科"),
    FU_CHAN_KE(28,"妇产科"),
    YUAN_BAN(29,"院办"),
    ZHENG_GONG_KE(30,"政工科"),
    JIAN_CHA_SHI(31,"监察室"),
    GONG_HUI(32,"工会"),
    REN_SHI_KE(33,"人事科"),
    YI_WU_KE(34,"医务科"),
    HU_LI_BU(35,"护理部"),
    MEN_ZHEN_BU(36,"门诊部"),
    ZHI_GUAN_KE(37,"质管科"),
    JI_KONG_KE(38,"疾控科"),
    XIN_XI_KE(39,"信息科"),
    KE_YAN_KE(40,"科研科"),
    JIAO_XUE_KE(41,"教学科"),
    SHE_BEI_KE(42,"设备科"),
    CAI_WU_KE(43,"财务科"),
    SHEN_JI_KE(44,"审计科"),
    YI_BAO_BAN(45,"医保办"),
    SHI_YE_FA_ZHAN_BU(46,"事业发展部"),
    BAO_WEI_KE(47,"保卫科"),
    ZONG_WU_KE(48,"总务科"),
    BING_YOU_FU_WU_ZHONGXIN(49,"病友服务中心"),
    OUTSIDE(50,"外部人士"),
    OTHERS(10000,"没有此科室");

    private int value;

    private String name;

    Departments(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Departments of(int value) {
        for (Departments departments : Departments.values()) {
            if (departments.getValue() == value) {
                return departments;
            }
        }
        return OTHERS;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
