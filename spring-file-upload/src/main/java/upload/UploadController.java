package upload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {
    private String uploadPath = "/Users/junyoungkim/Documents/upload";

    @RequestMapping("/upload/form")
    public String form() {
        return "upload/fileUploadForm";
    }
    @RequestMapping(value = "/upload/multipartFile", method = RequestMethod.POST)
    public String uploadByMultipartFile(
            @RequestParam("f") MultipartFile multipartFile,
            @RequestParam("title") String title, Model model) throws IOException {
        if(!multipartFile.isEmpty()) {
//            byte[] bytes = multipartFile.getBytes();
//            File file = new File(uploadPath, multipartFile.getOriginalFilename());
//            FileCopyUtils.copy(bytes, file);

            //MultipartFile 인터페이스 transferTo 메서드 사용하여 간단하게 개선
            File file = new File(uploadPath, multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            model.addAttribute("title", title);
            model.addAttribute("fileName", multipartFile.getOriginalFilename());
            model.addAttribute("uploadPath", file.getAbsolutePath());

            return "upload/fileUploaded";
        }
        return "upload/noUploadFile";
    }

    @RequestMapping(value = "/upload/multipartHttpServletRequest", method = RequestMethod.POST)
    public String uploadByMultipartHttpServletRequest(
            MultipartHttpServletRequest request, Model model) throws IOException {
        MultipartFile multipartFile = request.getFile("f");
        if(!multipartFile.isEmpty()) {
            File file = new File(uploadPath, multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            model.addAttribute("title", request.getParameter("title"));
            model.addAttribute("fileName", multipartFile.getOriginalFilename());
            model.addAttribute("uploadPath", file.getAbsolutePath());

            return "upload/fileUploaded";
        }
        return "upload/noUploadFile";
    }
}
