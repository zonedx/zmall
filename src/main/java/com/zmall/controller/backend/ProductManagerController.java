package com.zmall.controller.backend;

import com.google.common.collect.Maps;
import com.zmall.common.ServerResponse;
import com.zmall.pojo.Product;
import com.zmall.service.IFileService;
import com.zmall.service.IProductService;
import com.zmall.util.PropertiesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName: ProductManageController
 * @Date 2019-09-15 20:59
 * @Author duanxin
 **/
@Api(tags = "product-manager")
@RestController
@RequestMapping("/manager/product/")
public class ProductManagerController {

    private IProductService iProductService;

    private IFileService iFileService;

    @Autowired
    public ProductManagerController(IProductService iProductService, IFileService iFileService) {
        this.iProductService = iProductService;
        this.iFileService = iFileService;
    }


    /**
     * 上架新产品
     */
    @ApiOperation(value = "上架新产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product", value = "product对象", required = true, paramType = "query", dataType = "Product")
    })
    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    public ServerResponse productSave(Product product){
        //全部通过拦截器验证是否登录以及权限
        return iProductService.saveOrUpdateProduct(product);
    }

    /**
     * 设置产品状态（在售：1|下架：0）
     */
    @ApiOperation(value = "设置产品状态（在售：1|下架：0）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "产品状态", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.POST)
    public ServerResponse setSaleStatus(Integer productId,Integer status){
        //全部通过拦截器验证是否登录以及权限
        return iProductService.setSaleStatus(productId,status);

    }

    /**
     * 查看产品详情
     */
    @ApiOperation(value = "查看产品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品编号", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    public ServerResponse getDetail(Integer productId){
        //全部通过拦截器验证是否登录以及权限
        return iProductService.manageProductDetail(productId);
    }

    /**
     * 分页查询产品列表
     */
    @ApiOperation(value = "产品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页，默认1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量，默认10", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    public ServerResponse getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        //全部通过拦截器验证是否登录以及权限
        return iProductService.getProductList(pageNum,pageSize);
    }

    @ApiOperation(value = "查询产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", value = "产品名称", paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "productId", value = "产品id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "当前页，默认1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量，默认10", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "search.do",method = RequestMethod.GET)
    public ServerResponse productSearch(String productName,Integer productId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10")int pageSize){
        //全部通过拦截器验证是否登录以及权限
        return iProductService.searchProduct(productName,productId,pageNum,pageSize);
    }

    @ApiOperation(value = "更新产品")
    @RequestMapping(value = "upload.do",method = RequestMethod.POST)
    public ServerResponse upload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        //全部通过拦截器验证是否登录以及权限
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtils.getProperty("ftp.server.http.prefix")+targetFileName;

        Map<String,String> fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @ApiOperation(value = "更新产品，可上传富文本")
    @RequestMapping("richtext_img_upload.do")
    public Map richtextImgUpload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map<String,String> resultMap = Maps.newHashMap();
        //全部通过拦截器验证是否登录以及权限
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        if(StringUtils.isBlank(targetFileName)){
            resultMap.put("success","false");
            resultMap.put("msg","上传失败");
            return resultMap;
        }

        String url = PropertiesUtils.getProperty("ftp.server.http.prefix")+targetFileName;
        resultMap.put("success","true");
        resultMap.put("msg","上传成功");
        resultMap.put("file_path",url);

        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }

}
