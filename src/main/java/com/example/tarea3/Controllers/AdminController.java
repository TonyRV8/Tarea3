package com.example.tarea3.Controllers;

import com.example.tarea3.Models.Usuario;
import com.example.tarea3.Services.UsuarioService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder;

    public AdminController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    // Cargar formulario de edición
    @GetMapping("/editar/{id}")
public String editarUsuario(@PathVariable Long id, Model model) {
    Usuario usuario = usuarioService.obtenerPorId(id);
    if (usuario == null) {
        return "redirect:/admin/usuarios"; // Si no existe, redirigir
    }
    model.addAttribute("usuario", usuario);
    return "admin/editar_usuario"; // ⚠ Asegúrate de que existe en templates/admin/
}



    // Guardar cambios en usuario
    @PostMapping("/editar/{id}")
    public String actualizarUsuario(@PathVariable Long id,
                @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam(required = false) String password) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        if (password != null && !password.isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }
        usuarioService.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/admin/usuarios"; // 🔥 Redirige en lugar de devolver una vista
    }
    
}
