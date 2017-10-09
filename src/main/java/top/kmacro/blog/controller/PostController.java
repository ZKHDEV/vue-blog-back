package top.kmacro.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.model.vo.post.PageSearchVo;
import top.kmacro.blog.model.vo.post.SaveVo;
import top.kmacro.blog.model.vo.post.SearchVo;
import top.kmacro.blog.security.IgnoreSecurity;
import top.kmacro.blog.service.PubPostService;
import top.kmacro.blog.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/post")
public class PostController extends BaseController {

    @Autowired
    private PubPostService pubPostService;

    @IgnoreSecurity
    @PostMapping("/get_page")
    public Response getPage(@RequestBody PageSearchVo pageSearchVo){
        return new Response(0,"success", pubPostService.getPage(pageSearchVo));
    }

    @IgnoreSecurity
    @GetMapping("/get_post/{id}")
    public Response getPost(@PathVariable(name = "id",required = true) String id){
        return new Response(0,"success", pubPostService.getPost(id));
    }

}
