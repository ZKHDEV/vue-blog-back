package top.kmacro.blog.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.kmacro.blog.model.Post;
import top.kmacro.blog.model.vo.PageVo;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.model.vo.post.SaveVo;
import top.kmacro.blog.model.vo.post.SearchVo;
import top.kmacro.blog.service.PostService;
import top.kmacro.blog.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Zhangkh on 2017-08-30.
 */
@RestController
@RequestMapping(path = "/post")
public class PostController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @PostMapping("/search")
    public Response search(@RequestBody SearchVo searchVo) {
        return new Response(0,"success",postService.search(searchVo));
    }

    @PostMapping("/save")
    public Response save(@Valid @RequestBody SaveVo saveVo, BindingResult result){
        if(result.hasErrors()){
            return FormatValidMessage(result);
        }
        if(postService.titleExisted(saveVo.getId(),saveVo.getTitle())){
            return new Response(-12,"文章名已存在");
        }

        SaveVo newSaveVo = postService.save(saveVo);

        return new Response(0,"success",newSaveVo);
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable(value = "id",required = true) String id){
        postService.delete(id);
        return new Response(0,"success");
    }

    @PostMapping("/delete_batch")
    public Response delete_batch(@RequestBody String[] ids){
        if(ids != null && ids.length > 0){
            postService.deleteBatch(ids);
        }
        return new Response(0,"success");
    }
}
