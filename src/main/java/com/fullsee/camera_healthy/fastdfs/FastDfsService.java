package com.fullsee.camera_healthy.fastdfs;

import org.springframework.stereotype.Component;

/**
 * <li>文件名称: 题目名称</li>
 * <li>文件描述: 题目名称 功能描述</li>
 * <li>版权所有: 版权所有© 2005-2019</li>
 * <li>公 司: Fullsee</li>
 * <li>内容摘要: 无</li>
 * <li>其他说明:无</li>
 * <li>完成日期： 2020-08-04 9:54 </li>
 * <li>修改记录: 无</li>
 *
 * @author songyunbing
 * @version 产品版本
 */
public interface FastDfsService {
    /**
     * 通过文件路径上传文件到 DFS 文件服务器
     *
     * @param file 文件路径
     * @return
     */
    String upload(String file);

    /**
     * 通过 base64 字符串上传文件到 DFS 文件服务器
     *
     * @param base64   base64 字符串
     * @param fileName 文件名称(带后缀名)
     * @return
     */
    String uploadImageBYBase64(String base64, Object fileName);
}
