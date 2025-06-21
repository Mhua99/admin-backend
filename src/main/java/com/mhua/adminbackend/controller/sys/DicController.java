package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.DicQueryDTO;
import com.mhua.adminbackend.pojo.entity.Dic;
import com.mhua.adminbackend.pojo.entity.DicItem;
import com.mhua.adminbackend.pojo.vo.DicVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.DicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dic")
@Tag(name = "字典管理")
public class DicController {

    @Autowired
    private DicService dicService;

    @GetMapping("/list")
    public Result<PageResult<Dic>> list(DicQueryDTO dicQueryDTO) {
        PageResult<Dic> pageResult = dicService.list(dicQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result<DicVO> insert(@RequestBody DicVO dicVO) {
        DicVO insert = dicService.insert(dicVO);
        return Result.success(insert);
    }

    @PutMapping
    public Result<DicVO> update(@RequestBody DicVO dicVO) {
        DicVO update = dicService.update(dicVO);
        return Result.success(update);
    }

    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable("ids") Integer id) {
        Boolean delete = dicService.delete(id);
        return Result.success(delete);
    }

    @GetMapping("/{id}")
    public Result<DicVO> getById(@PathVariable("id") Integer id) {
        DicVO dicVO = dicService.getById(id);
        return Result.success(dicVO);
    }

    @GetMapping("/dicItem/{sign}")
    public Result<List<DicItem>> getItemById(@PathVariable("sign") String sign) {
        List<DicItem> dicItemList = dicService.getItemBySign(sign);
        return Result.success(dicItemList);
    }
}
