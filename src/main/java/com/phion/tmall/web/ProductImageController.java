package com.phion.tmall.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.ProductImage;
import com.phion.tmall.service.ProductImageService;
import com.phion.tmall.service.ProductService;
import com.phion.tmall.util.FileUtil;
import com.phion.tmall.util.ImageUtil;
import com.phion.tmall.util.ProductImageType;

@RestController
public class ProductImageController {
		

	@Autowired ProductImageService productImageService;
	@Autowired ProductService productService;
	
	@GetMapping("/products/{id}/productImages")
	public List<ProductImage> list(@PathVariable(name="id")int pid,String type){
		if(ProductImageType.BRIEF.toString().equals(type))
			return productImageService.listBriefProductImages(pid);
		else if(ProductImageType.DETAIL.toString().equals(type))
			return productImageService.listDetailProductImages(pid);
		else return null;
	}
	

	@DeleteMapping("/productImages/{id}")
	public Object delete(@PathVariable(name="id")int id) {
		//先获取产品信息，删除数据库产品图片记录，最后删除产品图片
		ProductImage productImage = productImageService.get(id);
		productImageService.delete(id);
		
		String folderPath = FileUtil.getResourcesPath()
				+"static"+File.separator+
				"img"+File.separator;
    	if(ProductImageType.BRIEF.toString().equals(productImage.getType())) {
    		folderPath+="productBrief";
    	}else {
    		folderPath+="productDetail";
    	}
    	File imageFolder= new File(folderPath);
		File file = new File(imageFolder,productImage.getId()+".jpg");
		String fileName = file.getName();
		file.delete();
		if(ProductImageType.BRIEF.toString().equals(productImage.getType())){
        	String imageFolder_small= folderPath+"_small";
        	String imageFolder_middle= folderPath+"_middle";	
        	File f_small = new File(imageFolder_small, fileName);
        	File f_middle = new File(imageFolder_middle, fileName);
        	f_small.delete();
        	f_middle.delete();
        }
		return null;
	}
	
	@PostMapping("/productImages")
	public Object add(MultipartFile image,@RequestParam("pid")int pid,
			@RequestParam("type") String type) throws IOException {
		ProductImage productImage = new ProductImage();
		Product product = productService.get(pid);
		productImage.setProduct(product);
		productImage.setType(type);
		
    	productImageService.add(productImage);//此时productImage的数据已经更新
		String folderPath = FileUtil.getResourcesPath()
				+"static"+File.separator+
				"img"+File.separator;
    	if(ProductImageType.BRIEF.toString().equals(type)) {
    		folderPath+="productBrief";
    	}else {
    		folderPath+="productDetail";
    	}
		
		File imageFolder= new File(folderPath);
		File file = new File(imageFolder,productImage.getId()+".jpg");
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		image.transferTo(file);//将文件以我们所取的文件名保存
		BufferedImage img = ImageUtil.change2jpg(file);//将文件转为jpg格式
		//将生成的jpg数据覆盖原图
		ImageIO.write(img, "jpg", file);
		
		String fileName = file.getName();
		
		//对于简要图片，我们需要中，小两种分辨率的的图片
		if(ProductImageType.BRIEF.toString().equals(productImage.getType())){
        	String imageFolder_small= folderPath+"_small";
        	String imageFolder_middle= folderPath+"_middle";	
        	File f_small = new File(imageFolder_small, fileName);
        	File f_middle = new File(imageFolder_middle, fileName);
        	f_small.getParentFile().mkdirs();
        	f_middle.getParentFile().mkdirs();
        	ImageUtil.resizeImage(file, 56, 56, f_small);
        	ImageUtil.resizeImage(file, 217, 190, f_middle);
        }		
		
		return productImage;
	}
}
