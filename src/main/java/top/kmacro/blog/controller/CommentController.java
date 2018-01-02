package top.kmacro.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.model.vo.comment.SaveVo;
import top.kmacro.blog.model.vo.comment.SearchVo;
import top.kmacro.blog.security.IgnoreSecurity;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private CommentService commentService;

//    @PostMapping("/search")
//    public Response search(@RequestBody SearchVo searchVo){
//        return new Response(0,"success", commentService.search(searchVo));
//    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable(value = "id",required = true) String id){
        commentService.delete(id);
        return new Response(0,"success");
    }

    @PostMapping("/delete_batch")
    public Response delete_batch(@RequestBody String[] ids){
        if(ids != null && ids.length > 0){
            commentService.delete(ids);
        }
        return new Response(0,"success");
    }

    @IgnoreSecurity
    @GetMapping("/get_all/{postId}")
    public Response getAll(@PathVariable(value = "postId",required = true) String postId){
        return new Response(0,"success", commentService.getParComments(postId));
    }

    @PostMapping("/save")
    public Response save(@Valid @RequestBody SaveVo saveVo, BindingResult result){
        if(result.hasErrors()){
            return FormatValidMessage(result);
        }
        return new Response(0,"success", commentService.save(saveVo));
    }
}
