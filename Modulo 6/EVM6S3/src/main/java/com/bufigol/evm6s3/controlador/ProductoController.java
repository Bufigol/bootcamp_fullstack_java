package com.bufigol.evm6s3.controlador;

import com.bufigol.evm6s3.modelo.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoController {
    private List<Producto> productos = new ArrayList<>();
    private int nextId = 1;

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    @PostMapping("/procesar")
    public String procesarFormulario(@ModelAttribute Producto producto) {
        producto.setId(nextId++);
        productos.add(producto);
        return "redirect:/producto/mostrar";
    }

    @GetMapping("/mostrar")
    public String mostrarProductos(Model model) {
        model.addAttribute("productos", productos);
        return "lista";
    }
}