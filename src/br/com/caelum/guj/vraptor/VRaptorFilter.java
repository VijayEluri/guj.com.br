package br.com.caelum.guj.vraptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.VRaptor;

/**
 * Para ignorar as URIs do JForum
 * @author Lucas Cavalcanti
 *
 */
public class VRaptorFilter extends VRaptor {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if (request.getRequestURI().endsWith(".java") || request.getRequestURI().endsWith(".guj")) {
			chain.doFilter(req, res);
		} else {
			super.doFilter(req, res, chain);
		}
	}
}
