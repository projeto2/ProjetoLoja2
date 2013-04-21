package usuario;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=Endereco.class, value="enderecoConverter")
public class EnderecoConverter implements Converter 
{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) 
	{
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO.obterEndereco(Integer.parseInt(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componente, Object object) 
	{
		return ((Endereco)object).getId().toString();
	}

}
