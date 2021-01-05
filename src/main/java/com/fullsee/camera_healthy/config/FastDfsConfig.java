package com.fullsee.camera_healthy.config;

import java.util.Optional;

/**
 * <li>文件名称: 题目名称</li>
 * <li>文件描述: 题目名称 功能描述</li>
 * <li>版权所有: 版权所有© 2005-2019</li>
 * <li>公 司: Fullsee</li>
 * <li>内容摘要: 无</li>
 * <li>其他说明:无</li>
 * <li>完成日期： 2020-08-04 10:41 </li>
 * <li>修改记录: 无</li>
 *
 * @author songyunbing
 * @version 产品版本
 */
public class FastDfsConfig {
    //dfs文件上传地址http://114.242.232.39:9092/v1/files  ,如果有/结尾，设置的时候去掉
    private String dfsUploadFilePath;
    //nginx访问地址http://114.242.232.39:9093/ ，如果没有/结尾，设置的时候加上
    private String nginxAccessUrl;

    public FastDfsConfig(String dfsUploadFilePath, String nginxAccessUrl){
        this.dfsUploadFilePath = Optional.ofNullable(dfsUploadFilePath).map(x->x.endsWith("/") ? x.substring(0, x.length() -1) : x).orElse(null);
        this.nginxAccessUrl = Optional.ofNullable(nginxAccessUrl).map(x->x.endsWith("/") ? x : x + "/").orElse(null);
    }

    public String getDfsUploadFilePath() {
        return dfsUploadFilePath;
    }

    public void setDfsUploadFilePath(String dfsUploadFilePath) {
        this.dfsUploadFilePath = Optional.ofNullable(dfsUploadFilePath).map(x->x.endsWith("/") ? x.substring(0, x.length() -1) : x).orElse(null);
    }

    public String getNginxAccessUrl() {
        return nginxAccessUrl;
    }

    public void setNginxAccessUrl(String nginxAccessUrl) {
        this.nginxAccessUrl = Optional.ofNullable(nginxAccessUrl).map(x->x.endsWith("/") ? x : x + "/").orElse(null);
    }
}
