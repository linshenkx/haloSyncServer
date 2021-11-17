package cn.linshenkx.halosyncserver.manager.impl;

import cn.hutool.core.io.FileUtil;
import cn.linshenkx.halosyncserver.manager.HaloManager;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostSimpleDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
@Slf4j
class HaloManagerImplTest {
    @Resource
    private HaloManager haloManager;

    @Test
    void getPost() {
        List<BasePostSimpleDTO> postListByKeyword = haloManager.getPostListByTitle("大数据通用计算平台(支持flink、spark等)(1)系统调研及设计");
        for (BasePostSimpleDTO post : postListByKeyword) {
            log.info(post.getSlug() + ":" + post.getTitle());
        }
    }

    @Test
    public void getLoginToken() {
        System.out.println("token:" + haloManager.getLoginToken(false));
    }

    @Test
    public void getAllBasePostSimpleDTO() {
        System.out.println(JSON.toJSONString(haloManager.getAllBasePostSimpleDTO(), SerializerFeature.PrettyFormat));
    }

    @Test
    void importMarkdown() {
        haloManager.importMarkdown(FileUtil.readString("C:\\Users\\Shinelon\\Desktop\\HBCK2修复RIT实践笔记.md", StandardCharsets.UTF_8));
    }

    @Test
    void deletePost() {
        haloManager.deletePost("HBCK2修复RIT实践笔记");
    }

    @Test
    public void deleteAllPost() {
        for (BasePostSimpleDTO post : haloManager.getAllBasePostSimpleDTO()) {
            haloManager.deletePost(post.getTitle());
        }
    }

}
