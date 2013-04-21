package produto;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import db.Conexao;

public class ProdutoDAO 
{
	private Session session;
	
	public ProdutoDAO()
	{
		this.session = Conexao.getConnection().getCurrentSession();
	}
	
	public List<Produto> buscaInicial()
	{
		Criteria criteria = session.createCriteria(Produto.class);
		criteria.setMaxResults(10);
		
		return criteria.list();
	}
	
	public Produto obter(Integer id)
	{
		Criteria criteria = session.createCriteria(Produto.class);
		criteria.add(Restrictions.eq("id", id));
		
		return (Produto) criteria.uniqueResult();
	}
	
	public List<Produto> buscaCategoria(String categoria)
	{
		Criteria criteria = session.createCriteria(Produto.class);
		criteria.add(Restrictions.eq("categoria", categoria));
		
		return criteria.list();
	}
	
	public List<Produto> buscar(String texto)
	{
		Criteria criteria = session.createCriteria(Produto.class);
		criteria.add(Restrictions.like("descricao", "%"+texto+"%"));
		
		
		return criteria.list();
	}
}
