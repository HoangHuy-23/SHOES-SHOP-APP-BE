package iuh.fit.dhktpm117ctt.group06.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryProvider {
    @Autowired
    private Cloudinary cloudinary;

    public Map upload(MultipartFile file, String folder, String fileName) throws IOException {
        Map uploadParams = ObjectUtils.asMap(
                "async", "auto",
                "folder", "ShoesShopApp/"+folder,
                "public_id", fileName
        );
        return cloudinary.uploader().upload(file.getBytes(), uploadParams);
    }

    public Map delete(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
