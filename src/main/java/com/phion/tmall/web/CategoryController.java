package com.phion.tmall.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phion.tmall.pojo.Category;
import com.phion.tmall.service.CategoryService;
import com.phion.tmall.util.FileUtil;
import com.phion.tmall.util.ImageUtil;
import com.phion.tmall.util.Page4Navigator;

@RestController
public class CategoryController {
	
	@Autowired CategoryService categoryService;
	
	@GetMapping(value = "/categories")
	public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size){
		return categoryService.list(start,size);
	}

	@PostMapping(value = "/categories")
	public Object add(Category bean, MultipartFile image) throws IllegalStateException, IOException {
		categoryService.add(bean);
		saveOrUpdateImage(bean,image);
		return bean;
	}
	
	/**
	 * 保存图片
	 * @param bean
	 * @param image
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void saveOrUpdateImage(Category bean, MultipartFile image) throws IllegalStateException, IOException {
		File imageFolder= new File(FileUtil.getResourcesPath()
				+"static"+File.separator+
				"img"+File.separator
				+"category");
		File file = new File(imageFolder,bean.getId()+".jpg");
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		image.transferTo(file);
		BufferedImage img = ImageUtil.change2jpg(file);
		//将生成的jpg数据覆盖原图
		ImageIO.write(img, "jpg", file);
	}
	
	@DeleteMapping("/categories/{id}")
	public Object delete(@PathVariable(value = "id")int id) {
		categoryService.delete(id);
		File imageFolder= new File(FileUtil.getResourcesPath()
				+"static"+File.separator+
				"img"+File.separator
				+"category");
		File img = new File(imageFolder,id+".jpg");
		if(img.exists()) img.delete();
		return null;
	}
	
	@PutMapping("/categories/{id}")
	public Object update(Category bean, MultipartFile image,String name) throws IllegalStateException, IOException {
		if(name!=null) bean.setName(name);
		/*System.out.println("----------------更新---------------");
		System.out.println(bean);
		System.out.println("----------------更新---------------");*/
		categoryService.update(bean);
		if(image!=null)
			saveOrUpdateImage(bean,image);
		return null;
	}
	@GetMapping("/categories/{id}")
	public Object get(@PathVariable(value = "id")int id) {
		return categoryService.get(id);
	}
	
}
