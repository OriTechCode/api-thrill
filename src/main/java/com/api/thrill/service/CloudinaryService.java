package com.api.thrill.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, String> subirImagen(MultipartFile archivo) throws IOException {
        String nombreUnico = generarNombreUnico(archivo.getOriginalFilename());

        Map<String, Object> params = new HashMap<>();
        params.put("public_id", "thrill/" + nombreUnico);
        params.put("folder", "thrill");
        params.put("resource_type", "auto");
        params.put("overwrite", true);

        Map resultado = cloudinary.uploader().upload(archivo.getBytes(), params);

        return Map.of(
                "url", resultado.get("secure_url").toString(),
                "publicId", resultado.get("public_id").toString()
        );
    }

    public void eliminarImagen(String publicId) throws IOException {
        if (publicId != null && !publicId.isEmpty()) {
            try {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            } catch (Exception e) {
                throw new IOException("Error al eliminar imagen de Cloudinary: " + e.getMessage());
            }
        }
    }

    private String generarNombreUnico(String nombreOriginal) {
        String extension = "";
        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }
}