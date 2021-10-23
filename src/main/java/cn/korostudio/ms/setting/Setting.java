package cn.korostudio.ms.setting;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import lombok.Getter;

public class Setting {

    @Getter
    protected static cn.hutool.setting.Setting setting;

    static {
        setting = new cn.hutool.setting.Setting(FileUtil.touch(System.getProperty("user.home")+"/.ms/server/setting.setting"), CharsetUtil.CHARSET_UTF_8,true);
    }


    public static void Init(){

    }
}
