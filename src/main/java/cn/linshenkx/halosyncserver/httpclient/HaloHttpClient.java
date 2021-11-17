package cn.linshenkx.halosyncserver.httpclient;

import cn.linshenkx.halosyncserver.model.AuthToken;
import cn.linshenkx.halosyncserver.model.PageObject;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostDetailDTO;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostSimpleDTO;
import cn.linshenkx.halosyncserver.model.enums.PostStatus;
import cn.linshenkx.halosyncserver.model.params.LoginParam;
import cn.linshenkx.halosyncserver.model.params.PostParam;
import cn.linshenkx.halosyncserver.model.params.PostQuery;
import cn.linshenkx.halosyncserver.model.support.BaseResponse;
import cn.linshenkx.halosyncserver.model.vo.PostDetailVO;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "halo", url = "${halo-sync.halo.url}", configuration = FeignConfiguration.class)
public interface HaloHttpClient {

    @PostMapping("/api/admin/login")
    BaseResponse<AuthToken> auth(@RequestBody LoginParam loginParam);

    @GetMapping("/api/admin/posts/status/{status}")
    BaseResponse<PageObject<BasePostSimpleDTO>> pageByStatus(@PathVariable(name = "status") PostStatus status, @SpringQueryMap Pageable pageable, @RequestParam Boolean more);

    @GetMapping("/api/admin/posts/{postId}")
    BaseResponse<PostDetailVO> getByPostId(@PathVariable(name = "postId") Integer postId);

    @GetMapping("/api/admin/posts")
    BaseResponse<PageObject<BasePostSimpleDTO>> pageBy(@QueryMap Pageable pageable, @SpringQueryMap PostQuery postQuery, @RequestParam Boolean more);

    @DeleteMapping("/api/admin/posts")
    BaseResponse<List<BasePostSimpleDTO>> deletePermanentlyInBatch(@RequestBody List<Integer> ids);

    @PostMapping(value = "/api/admin/backups/markdown/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResponse<BasePostDetailDTO> backupMarkdowns(@RequestPart("file") MultipartFile file);

    @PutMapping("/api/admin/posts/{postId}")
    BaseResponse<PostDetailVO> updateBy(@RequestBody PostParam postParam,
                                        @PathVariable("postId") Integer postId,
                                        @RequestParam("autoSave") Boolean autoSave
    );

    @DeleteMapping("/api/admin/posts/{postId}")
    BaseResponse<Void> deletePermanently(@PathVariable("postId") Integer postId);

//    @RequestMapping(method = RequestMethod.POST, value = "/jobs/{jobId}/stop")
//    void stopJob(URI uri, @PathVariable("jobId") String jobId, StopJobBody stopJobBody);
//
//    @RequestMapping(method = RequestMethod.POST, value = "/jobs/{jobId}/savepoints")
//    TriggerResult savepoints(URI uri, @PathVariable("jobId") String jobId, SavepointBody savepointBody);
//
//    @RequestMapping(method = RequestMethod.GET, value = "/jobs/{jobId}/savepoints/{triggerid}")
//    TriggerQueryResult getSavepointTriggerResult(URI uri, @PathVariable("jobId") String jobId, @PathVariable("triggerid") String triggerid);


}
