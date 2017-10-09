package top.kmacro.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.model.vo.post.PostVo;
import top.kmacro.blog.model.vo.post.PublishVo;
import top.kmacro.blog.model.vo.post.SaveVo;
import top.kmacro.blog.model.vo.post.SearchVo;
import top.kmacro.blog.service.AdminPostService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin_post")
public class AdminPostController extends BaseController{

    @Autowired
    private AdminPostService adminPostService;

    @PostMapping("/search")
    public Response search(@RequestBody SearchVo searchVo) {
        return new Response(0,"success",adminPostService.search(searchVo));
    }

    @PostMapping("/save")
    public Response save(@Valid @RequestBody SaveVo saveVo, BindingResult result){
        if(result.hasErrors()){
            return FormatValidMessage(result);
        }
        if(adminPostService.titleExisted(saveVo.getId(),saveVo.getTitle())){
            return new Response(-12,"文章名已存在");
        }

        SaveVo backSaveVo = adminPostService.save(saveVo);

        return new Response(0,"success",backSaveVo);
    }

    @GetMapping("/get_post/{id}")
    public Response getPost(@PathVariable(name = "id",required = true) String id){
        return new Response(0,"success", adminPostService.getPostSaveVo(id));
    }

    @PostMapping("/publish")
    public Response publish(@RequestBody PublishVo publishVo){
        adminPostService.publish(publishVo);
        return new Response(0,"success");
    }

    @GetMapping("/unpublish/{id}")
    public Response unpublish(@PathVariable(name = "id",required = true) String id){
        adminPostService.unpublish(id);
        return new Response(0,"success");
    }

    @GetMapping("/get_publish_post/{id}")
    public Response getPublishPost(@PathVariable(name = "id",required = true) String id){
        PublishVo publishVo = adminPostService.getPublishPost(id);
        if(publishVo != null){
            return new Response(0,"success", publishVo);
        } else {
            return new Response(-10,"文章未发布");
        }

    }

    @PostMapping("/top")
    public Response top(@RequestBody PublishVo publishVo){
        adminPostService.top(publishVo);
        return new Response(0,"success");
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable(value = "id",required = true) String id){
        adminPostService.delete(id);
        return new Response(0,"success");
    }

    @PostMapping("/delete_batch")
    public Response delete_batch(@RequestBody String[] ids){
        if(ids != null && ids.length > 0){
            adminPostService.delete(ids);
        }
        return new Response(0,"success");
    }

    @GetMapping("/recycle/{id}")
    public Response recycle(@PathVariable(value = "id",required = true) String id){
        adminPostService.recycle(id);
        return new Response(0,"success");
    }

    @PostMapping("/recycle_batch")
    public Response recycle_batch(@RequestBody String[] ids){
        if(ids != null && ids.length > 0){
            adminPostService.recycle(ids);
        }
        return new Response(0,"success");
    }

    @GetMapping("/recover/{id}")
    public Response recover(@PathVariable(value = "id",required = true) String id){
        adminPostService.recover(id);
        return new Response(0,"success");
    }

    @PostMapping("/recover_batch")
    public Response recover_batch(@RequestBody String[] ids){
        if(ids != null && ids.length > 0){
            adminPostService.recover(ids);
        }
        return new Response(0,"success");
    }
}
