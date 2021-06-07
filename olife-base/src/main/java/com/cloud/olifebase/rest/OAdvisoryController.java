package com.cloud.olifebase.rest;


import com.cloud.olifebase.entity.OAdvisory;
import com.cloud.olifebase.entity.ORegister;
import com.cloud.olifebase.service.IOAdvisoryService;
import com.cloud.olifebase.service.IORegisterService;
import com.cloud.olifebase.vo.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.22
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@Api("健康咨询资源服务")
@RestController
@RequestMapping("/oad")
public class OAdvisoryController {
    @Resource
    IOAdvisoryService advisoryService;

    @ApiOperation("获取所有健康咨询讯息")
    @GetMapping("/all")
    public RestResponse<List<OAdvisory>> all(@RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) @ApiParam("token请求头") String authentication){
        return new RestResponse<List<OAdvisory>>(HttpStatus.OK.value(), HttpStatus.OK.toString(),advisoryService.list(null));
    }

    @ApiOperation("获取指定的咨询消息")
    @GetMapping("/get/{id}")
    public RestResponse<OAdvisory> get(@PathVariable(name = "id") @ApiParam("健康咨询编号") Long id,
                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) @ApiParam("token请求头") String authentication){
        return new RestResponse<OAdvisory>(HttpStatus.OK.value(), HttpStatus.OK.toString(),advisoryService.getById(id));
    }

    @ApiOperation("添加一段咨询内容")
    @PostMapping("/set")
    public RestResponse<Boolean> set(@RequestBody @ApiParam("健康咨询对象") OAdvisory oAdvisory,
                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) @ApiParam("token请求头") String authentication){
        System.out.println(oAdvisory);
        return new RestResponse<Boolean>(HttpStatus.OK.value(), HttpStatus.OK.toString(),advisoryService.save(oAdvisory));
    }

    @ApiOperation("更新某段咨询内容")
    @PutMapping("/up")
    public RestResponse<Boolean> up(@RequestBody @ApiParam("健康咨询对象") OAdvisory oAdvisory,
                                    @RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) @ApiParam("token请求头") String authentication){
        return new RestResponse<Boolean>(HttpStatus.OK.value(), HttpStatus.OK.toString(),advisoryService.updateById(oAdvisory));
    }

    @ApiOperation("删除指定咨询内容")
    @DeleteMapping(value = "/del/{id}")
    public RestResponse<Boolean> del(@PathVariable(name = "id") @ApiParam("健康咨询编号") Long id,
                                     @RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) @ApiParam("token请求头") String authentication){
        return new RestResponse<Boolean>(HttpStatus.OK.value(), HttpStatus.OK.toString(),advisoryService.removeById(id));
    }
}

