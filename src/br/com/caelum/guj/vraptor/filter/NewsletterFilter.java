package br.com.caelum.guj.vraptor.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.jforum.entities.UserSession;

import org.hibernate.Session;

import br.com.caelum.guj.dao.GUJUserDAO;
import br.com.caelum.guj.model.NewsletterParticipant;
import br.com.caelum.vraptor.util.hibernate.SessionCreator;

public class NewsletterFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		UserSession user = (UserSession) session.getAttribute("userSession");

		if (user != null) {
			defineParticipantStatus(request, session, user);
		}

		chain.doFilter(req, res);
	}

	private void defineParticipantStatus(HttpServletRequest request, HttpSession session, UserSession user) {
		Boolean isParticipant = (Boolean) session.getAttribute("newsletterParticipant");
		if (isParticipant == null) {
			GUJUserDAO dao = new GUJUserDAO(getHibernateSession(request));
			NewsletterParticipant participant = dao.findParticipantByGujUserId(user.getUserId());
			if (participant != null) {
				session.setAttribute("newsletterParticipant", true);
			} else {
				session.setAttribute("newsletterParticipant", false);
			}
		}
	}

	private Session getHibernateSession(HttpServletRequest request) {
		SessionCreator creator = (SessionCreator) request.getAttribute("sessionCreator");
		return creator.getInstance();
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
	}

}