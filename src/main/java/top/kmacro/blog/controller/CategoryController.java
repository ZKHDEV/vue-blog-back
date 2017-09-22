package top.kmacro.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.kmacro.blog.model.Category;
import top.kmacro.blog.model.User;
import top.kmacro.blog.model.vo.KValueVo;
import top.kmacro.blog.model.vo.Response;
import top.kmacro.blog.model.vo.category.FindAllVo;
import top.kmacro.blog.model.vo.category.SaveVo;
import top.kmacro.blog.security.TokenManager;
import top.kmacro.blog.service.CategoryService;
import top.kmacro.blog.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zhangkh on 2017-09-03.
 */
@RestController
@RequestMapping("/cate")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/save")
    public Response save(@Valid @RequestBody SaveVo saveVo, BindingResult result){
        if(result.hasErrors()){
            return FormatValidMessage(result);
        }

        if(categoryService.labelExisted(saveVo.getId(), saveVo.getLabel())){
            return new Response(-11,"分类名已存在");
        }

        categoryService.save(saveVo.getId(), saveVo.getLabel());

        // 获取分类KV
        KValueVo kValueVo = categoryService.getSelectOne(saveVo.getLabel());

        return new Response(0,"success", kValueVo);
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable(value = "id",required = true) String id){
        categoryService.delete(id);
        return new Response(0,"success");
    }

    @GetMapping("/get_select")
    public Response getSelect(){
        return new Response(0,"success", categoryService.getSelect());
    }
}
