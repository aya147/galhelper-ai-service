package com.galhelper.dialogue.common.enums;

/**
 * ClassName: UserIntentEnum
 * Package: com.galhelper.dialogue.common.enums
 * Description: 用户意图枚举
 *
 * @author aya
 * @date 2026/1/9 - 20:08
 * @project galhelper-ai-service
 */
public enum UserIntentEnum {

    /**
     * 用户的求助需求   1|游戏的安装&运行的报错;2|游戏推荐和找符合条件的游戏;3|游戏资源站&资讯网站;4|游戏相关软件的使用和技术探讨
     */
    GAME_ERROR(1, "游戏的安装&运行的报错"),
    GAME_RECOMMEND(2, "游戏推荐和找符合条件的游戏"),
    RESOURCE_AND_INFO(3, "游戏资源站&资讯网站"),
    TECH_SOFTWARE(4, "游戏相关软件的使用和技术探讨");

    private Integer code;

    private String name;

    UserIntentEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserIntentEnum fromCode(Integer code){
        for(UserIntentEnum e : values()){
            if(e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }
}
