package com.api.thrill.service;

import com.api.thrill.entity.Imagen;
import com.api.thrill.entity.Producto;
import com.api.thrill.entity.Usuario;
import com.api.thrill.repository.ImagenRepository;
import com.api.thrill.repository.ProductoRepository;
import com.api.thrill.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ImagenService {
    private final CloudinaryService cloudinaryService;
    private final ImagenRepository imagenRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public ImagenService(CloudinaryService cloudinaryService,
                         ImagenRepository imagenRepository,
                         ProductoRepository productoRepository,
                         UsuarioRepository usuarioRepository) {
        this.cloudinaryService = cloudinaryService;
        this.imagenRepository = imagenRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private void validarArchivo(MultipartFile archivo) {
        if (archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        String contentType = archivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("El archivo debe ser una imagen");
        }

        // 5MB máximo
        if (archivo.getSize() > 5_000_000) {
            throw new IllegalArgumentException("El archivo es demasiado grande. Máximo 5MB");
        }
    }

    @Transactional
    public Imagen subirImagenProducto(MultipartFile archivo, Long productoId) throws IOException {
        validarArchivo(archivo);

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

        Map<String, String> resultadoCloudinary = cloudinaryService.subirImagen(archivo);

        Imagen imagen = new Imagen();
        imagen.setUrl(resultadoCloudinary.get("url"));
        imagen.setPublicId(resultadoCloudinary.get("publicId"));
        imagen.setProducto(producto);

        return imagenRepository.save(imagen);
    }

    @Transactional
    public Imagen subirImagenUsuario(MultipartFile archivo, Long usuarioId) throws IOException {
        validarArchivo(archivo);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        // Si el usuario ya tiene una imagen de perfil, la eliminamos
        if (usuario.getImagenPerfil() != null) {
            eliminarImagen(usuario.getImagenPerfil().getId());
        }

        Map<String, String> resultadoCloudinary = cloudinaryService.subirImagen(archivo);

        Imagen imagen = new Imagen();
        imagen.setUrl(resultadoCloudinary.get("url"));
        imagen.setPublicId(resultadoCloudinary.get("publicId"));
        imagen = imagenRepository.save(imagen);

        usuario.setImagenPerfil(imagen);
        usuarioRepository.save(usuario);

        return imagen;
    }

    @Transactional
    public void eliminarImagen(Long imagenId) throws IOException {
        Imagen imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada con ID: " + imagenId));

        // Eliminar de Cloudinary
        if (imagen.getPublicId() != null) {
            cloudinaryService.eliminarImagen(imagen.getPublicId());
        }

        // Si la imagen pertenece a un producto, la removemos de la lista
        if (imagen.getProducto() != null) {
            imagen.getProducto().getImagenes().remove(imagen);
        }

        // Eliminar de la base de datos
        imagenRepository.delete(imagen);
    }

    public List<Imagen> obtenerImagenesProducto(Long productoId) {
        return imagenRepository.findByProductoId(productoId);
    }

    @Transactional
    public void eliminarImagenesProducto(Long productoId) throws IOException {
        List<Imagen> imagenes = imagenRepository.findByProductoId(productoId);
        for (Imagen imagen : imagenes) {
            eliminarImagen(imagen.getId());
        }
    }

    public Imagen obtenerImagen(Long id) {
        return imagenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada con ID: " + id));
    }

    @Transactional
    public Imagen actualizarImagenProducto(Long imagenId, MultipartFile nuevoArchivo) throws IOException {
        Imagen imagenExistente = obtenerImagen(imagenId);

        validarArchivo(nuevoArchivo);

        // Eliminar la imagen anterior de Cloudinary
        if (imagenExistente.getPublicId() != null) {
            cloudinaryService.eliminarImagen(imagenExistente.getPublicId());
        }

        // Subir la nueva imagen
        Map<String, String> resultadoCloudinary = cloudinaryService.subirImagen(nuevoArchivo);

        // Actualizar los datos de la imagen
        imagenExistente.setUrl(resultadoCloudinary.get("url"));
        imagenExistente.setPublicId(resultadoCloudinary.get("publicId"));

        return imagenRepository.save(imagenExistente);
    }

    @Transactional
    public void marcarImagenComoEliminada(Long imagenId) {
        Imagen imagen = obtenerImagen(imagenId);
        imagen.setEliminado(true);
        imagenRepository.save(imagen);
    }
}