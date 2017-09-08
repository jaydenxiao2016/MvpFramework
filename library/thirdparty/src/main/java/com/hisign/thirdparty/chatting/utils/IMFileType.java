package com.hisign.thirdparty.chatting.utils;

/**
 * 描述：聊天界面文件类型
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：huangyu
 * 版本：V1.0
 * 创建时间：2016-12-27
 * 最后修改时间：2016-12-27
 */
public enum IMFileType {

    image,
    audio,
    video,
    document,
    other;

    public static IMFileType getFileTypeByOrdinal(int ordinal) {
        for (IMFileType type : values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }
        return image;
    }

}
