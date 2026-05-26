package br.com.concessionaria.byd.controller;

import br.com.concessionaria.byd.form.CarroForm;
import br.com.concessionaria.byd.model.Carro;
import br.com.concessionaria.byd.service.CarroService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarroController {

    private final CarroService carroService;
    @GetMapping({"/", "/index"})
    public String index(Model model, HttpSession session) {
        model.addAttribute("carros", carroService.listarAtivos());
        model.addAttribute("quantidadeCarrinho", carrinho(session).size());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("carros", carroService.listarTodos());
        return "admin";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("carroForm", new CarroForm());
        return "formulario";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam Long id, Model model) {
        Carro carro = carroService.buscarPorId(id);
        model.addAttribute("carroForm", CarroForm.de(carro));
        return "formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute CarroForm carroForm,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "formulario";
        }

        carroService.salvar(carroForm);
        redirectAttributes.addFlashAttribute("mensagem", "Atualizacao ocorreu com sucesso.");
        return "redirect:/admin";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        model.addAttribute("carro", carroService.buscarPorId(id));
        return "detalhes";
    }

    @GetMapping("/deletar")
    public String deletar(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        carroService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Remocao ocorreu com sucesso.");
        return "redirect:/index";
    }

    @GetMapping("/restaurar")
    public String restaurar(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        carroService.restaurar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Restauracao ocorreu com sucesso.");
        return "redirect:/admin";
    }

    @GetMapping("/adicionarCarrinho")
    public String adicionarCarrinho(@RequestParam Long id,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        carrinho(session).add(carroService.buscarPorId(id));
        redirectAttributes.addFlashAttribute("mensagem", "Carro adicionado ao carrinho.");
        return "redirect:/index";
    }

    @GetMapping("/verCarrinho")
    public String verCarrinho(HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        List<Carro> carrinho = carrinho(session);
        if (carrinho.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Nao existem itens no carrinho.");
            return "redirect:/index";
        }

        model.addAttribute("carros", carrinho);
        model.addAttribute("quantidadeCarrinho", carrinho.size());
        model.addAttribute("totalCarrinho", total(carrinho));
        return "carrinho";
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(@RequestParam String pagamento,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        session.removeAttribute("carrinho");
        redirectAttributes.addFlashAttribute("mensagem",
                "Sua compra foi finalizada com sucesso. Seu ou seus automoveis chegaram em ate 30 dias no seu endereco. Pagamento escolhido: " + pagamento + ".");
        return "redirect:/index";
    }

    // Mantem o carrinho na sessao HTTP, conforme pedido na prova.
    @SuppressWarnings("unchecked")
    private List<Carro> carrinho(HttpSession session) {
        Object valor = session.getAttribute("carrinho");
        if (valor == null) {
            List<Carro> novoCarrinho = new ArrayList<>();
            session.setAttribute("carrinho", novoCarrinho);
            return novoCarrinho;
        }
        return (List<Carro>) valor;
    }

    private BigDecimal total(List<Carro> carrinho) {
        return carrinho.stream()
                .map(Carro::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
