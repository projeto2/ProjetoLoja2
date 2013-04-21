package filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.hibernate.SessionFactory;
import db.Conexao;

public class Filtro implements Filter
{
	private SessionFactory sessionFactory;
	@Override
	public void destroy() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
	throws IOException, ServletException 
	{
		try
		{
			sessionFactory.getCurrentSession().beginTransaction();
			
			chain.doFilter(request, response);
			
			sessionFactory.getCurrentSession().getTransaction().commit();
			sessionFactory.getCurrentSession().close();
		}
		catch(Exception e)
		{
			if(sessionFactory.getCurrentSession().getTransaction().isActive())
			{
				sessionFactory.getCurrentSession().getTransaction().rollback();
			}
			
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException 
	{
		sessionFactory = Conexao.getConnection();
		
	}

}
