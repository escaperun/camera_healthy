package com.fullsee.camera_healthy.fastdfs.impl;

import com.alibaba.fastjson.JSONObject;
import com.fullsee.camera_healthy.fastdfs.FastDfsService;
import com.fullsee.camera_healthy.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <li>文件名称: 题目名称</li>
 * <li>文件描述: 题目名称 功能描述</li>
 * <li>版权所有: 版权所有© 2005-2019</li>
 * <li>公 司: Fullsee</li>
 * <li>内容摘要: 无</li>
 * <li>其他说明:无</li>
 * <li>完成日期： 2020-08-04 9:55 </li>
 * <li>修改记录: 无</li>
 *
 * @author songyunbing
 * @version 产品版本
 */
@Service
public class FastFastDfsServiceImpl implements FastDfsService {

    private Logger logger = LoggerFactory.getLogger(FastFastDfsServiceImpl.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${fastdfs.fileUploadPath}")
    private String fileUploadPath = "http://200.22.242.42:9085/v1/files";

    private String fileSendPath = "http://222.35.102.26:8084/api/receive";

    private String fileReadPath = "http://200.22.242.42:9093/";

    @Override
    public String upload(String file) {
        logger.info("-- >>>> [ upload ] upload file is {}", file);
        //设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        return upload2DFS(fileSystemResource);
    }

    @Override
    public String uploadImageBYBase64(String base64, Object fileName) {
        String path = null;

        byte[] fileBytes = ImageUtil.changeBase64ToBytes(base64);
        String suffix = ImageUtil.getSuffix(fileBytes);//获取图片的后缀名，也可以是其他任意文件名
        ByteArrayResource fileResource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return fileName + "." + suffix;
            }
        };
        logger.info("-- >>>> [ uploadImageBYBase64 ] upload file is {}", fileName);
        path = upload2DFS(fileResource);
        sendImage(fileReadPath+path);
        return path;
    }

    private String upload2DFS(Object file) {
        try {
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("multipart/form-data");
            headers.setContentType(type);
            //设置请求体，注意是LinkedMultiValueMap
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("file", file);
            form.add("expire", "NEVER");
            //用HttpEntity封装整个请求报文
            HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
            logger.info("-- >>>> [ uploadImageBYBase64 ] dfs upload url is {}", fileUploadPath);
            return restTemplate.postForObject(fileUploadPath, files, String.class);
        } catch (Exception e) {
            logger.error("-- >>>> [ uploadImageBYBase64 ] upload dfs exception ", e);
        }
        return null;
    }

    private String sendImage(Object imageUrl){
        Map<Object, Object> map = new HashMap<>();
        map.put("deviceId","192.168.5.10");
        map.put("deviceType",1);
        map.put("dataType",1);
        map.put("data",imageUrl);
        map.put("dateTime","2020-11-18 15:41:22");
        JSONObject.toJSONString(map);
        return restTemplate.postForObject(fileSendPath, map, String.class);
    }

}
