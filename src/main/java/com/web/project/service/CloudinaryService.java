package com.web.project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dbuf1c9ao",
                "api_key", "163661337527294",
                "api_secret", "91GalhM_GR_7NI7iiecrnZxL8Ec"
        ));
    }

    public Map<String, String> subirImagen(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String url = (String) uploadResult.get("secure_url");
            String publicId = (String) uploadResult.get("public_id");

            Map<String, String> resultado = new HashMap<>();
            resultado.put("url", url);
            resultado.put("public_id", publicId);
            return resultado;
        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen", e);
        }
    }

    public void eliminarImagen(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar imagen de Cloudinary", e);
        }
    }


    public String extraerPublicId(String nuevaUrl) {
        try {
            String[] partes = nuevaUrl.split("/upload/");
            if (partes.length < 2) return null;

            String rutaRelativa = partes[1];
            if (rutaRelativa.startsWith("v")) {
                rutaRelativa = rutaRelativa.substring(rutaRelativa.indexOf("/") + 1);
            }

            int punto = rutaRelativa.lastIndexOf(".");
            if (punto != -1) {
                rutaRelativa = rutaRelativa.substring(0, punto);
            }

            return rutaRelativa;
        } catch (Exception e) {
            return null;
        }
    }

}
