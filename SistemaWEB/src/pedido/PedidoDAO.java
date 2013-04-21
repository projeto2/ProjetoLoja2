package pedido;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import usuario.Usuario;

import db.Conexao;

public class PedidoDAO 
{
	private Session session;
	
	public PedidoDAO()
	{
		this.session = Conexao.getConnection().getCurrentSession();
	}
	
	public void salvar(Pedido pedido)
	{
		session.saveOrUpdate(pedido);
	}
	
	public List<Pedido> listaPedido(Integer usuario, Integer numero, Date inicio, Date fim)
	{
		Criteria criteria = session.createCriteria(Pedido.class);
		criteria.createAlias("usuario", "u");
		criteria.add(Restrictions.eq("u.id", usuario));
		
		if(numero != null)
			criteria.add(Restrictions.eq("id", numero));
		
		if(inicio != null)
			criteria.add(Restrictions.ge("data", inicio));
		
		if(fim != null)
			criteria.add(Restrictions.le("data", fim));
		return criteria.list();
	}
	
	public Pedido obter(Integer id)
	{
		Criteria criteria = session.createCriteria(Pedido.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.createAlias("endereco", "end");
		criteria.createAlias("itens", "item");
		
		return (Pedido) criteria.uniqueResult();
		
	}
}
