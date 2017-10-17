package top.kmacro.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.model.vo.post.PhoneSearchVo;
import top.kmacro.blog.security.IgnoreSecurity;
import top.kmacro.blog.service.PubPostService;

@RestController
@RequestMapping(path = "/post")
public class PostController extends BaseController {

    @Autowired
    private PubPostService pubPostService;

    @IgnoreSecurity
    @PostMapping("/get_page")
    public Response getPage(@RequestBody PhoneSearchVo phoneSearchVo){
        return new Response(0,"success", pubPostService.getPage(phoneSearchVo));
    }

    @IgnoreSecurity
    @GetMapping("/get_post/{id}")
    public Response getPost(@PathVariable(name = "id",required = true) String id){
        return new Response(0,"success", pubPostService.getPost(id));
    }

    @IgnoreSecurity
    @GetMapping("/get_new_kv_list_by_phone/{phone}")
    public Response getNewKVListByPhone(@PathVariable(name = "phone",required = true) String phone){
        return new Response(0,"success", pubPostService.getNewKVListByPhone(phone));
    }

}
