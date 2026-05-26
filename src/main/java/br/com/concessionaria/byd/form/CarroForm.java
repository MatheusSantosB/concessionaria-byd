package br.com.concessionaria.byd.form;

import br.com.concessionaria.byd.model.Carro;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarroForm {

    private Long id;

    @NotBlank(message = "Informe o nome do carro.")
    private String nome;

    @NotBlank(message = "Informe o codigo.")
    @Pattern(regexp = "BYD-[A-Z0-9-]+", message = "Use o formato BYD-MODELO.")
    private String codigo;

    @NotNull(message = "Informe o preco.")
    @DecimalMin(value = "1.00", message = "O preco deve ser maior que zero.")
    private BigDecimal preco;

    @NotBlank(message = "Informe a autonomia.")
    private String autonomia;

    @NotBlank(message = "Informe a potencia.")
    private String potencia;

    @NotBlank(message = "Informe a descricao.")
    private String descricao;

    public static CarroForm de(Carro carro) {
        CarroForm form = new CarroForm();
        form.setId(carro.getId());
        form.setNome(carro.getNome());
        form.setCodigo(carro.getCodigo());
        form.setPreco(carro.getPreco());
        form.setAutonomia(carro.getAutonomia());
        form.setPotencia(carro.getPotencia());
        form.setDescricao(carro.getDescricao());
        return form;
    }
}
