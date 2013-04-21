package pedido;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import usuario.Endereco;
import usuario.Usuario;

@Entity
@Table(name="pedido")
public class Pedido 
{
	@Id
	@GeneratedValue
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="pedido", fetch=FetchType.EAGER)
	private List<Item> itens;
	
	@OneToMany(cascade=CascadeType.ALL)
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="endereco_pedido", joinColumns=@JoinColumn(name="pedido_id"))
	private List<Endereco> endereco;
	
	@Column
	private Integer formaPagamento;
	
	@Column
	private String status;
	
	@OneToOne
	private Usuario usuario;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public List<Item> getItens() {
		return itens;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Endereco> getEndereco() {
		return endereco;
	}
	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}
	public Integer getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(Integer formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
} 
