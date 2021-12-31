package cn.linshenkx.halosyncserver.manager;

import cn.linshenkx.halosyncserver.model.dto.post.BasePostDetailDTO;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostSimpleDTO;

import java.util.List;

public interface HaloManager {
    
    List<BasePostSimpleDTO> getAllBasePostSimpleDTO();

    BasePostDetailDTO importMarkdown(String fileContent);

    List<BasePostSimpleDTO> getPostListByTitle(String title);

    void deletePost(String name);

    void updateMarkdown(String oldMarkdown, String newMarkdown);
}
