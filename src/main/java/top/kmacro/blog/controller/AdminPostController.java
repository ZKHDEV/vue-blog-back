package top.kmacro.blog.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.model.vo.post.SearchVo;

@RestController
@RequestMapping("/admin_post")
public class AdminPostController extends BaseController{
    @PostMapping("/search")
    public Response search(@RequestBody SearchVo searchVo) {
        return new Response(0,"success",savePostService.search(searchVo));
    }
}
