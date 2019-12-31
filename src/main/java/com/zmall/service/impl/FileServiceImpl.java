package com.zmall.service.impl;

import com.google.common.collect.Lists;
import com.zmall.service.IFileService;
import com.zmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName: FileServiceImpl
 * @Date 2019-09-16 21:12
 * @Author duanxin
 **/
@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //扩展名
        assert fileName != null;
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件，上传文件的文件名：{}，上传文件的路径：{}，新文件名：{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            boolean isSuccess = fileDir.setWritable(true);
            if (!isSuccess) {
                log.info("{} set writable error！", fileDir);
                throw new RuntimeException("设置文件权限失败！");
            }
            isSuccess = fileDir.mkdirs();
            if (!isSuccess) {
                log.info("{} mkdirs error！", fileDir);
                throw new RuntimeException("创建目录失败！");
            }
        }
        File targetFile = new File(path, uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经将targetFIle上传到FTP服务器上

            //上传完之后，删除upload下面的文件
            boolean b = targetFile.delete();
            log.info("{} delete result: ", b);
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }

}
