package s4c.microservices.users_management.model.services;

import java.util.List;

import s4c.microservices.users_management.model.entity.Sessions;

public interface ISessionsService {
	
	List<Sessions> listSessions();
	Sessions getSessionByIp(String ip);
	Sessions addSession(Sessions session);
	void deleteSession(List<Sessions> sessions);
	void deleteSession(Sessions session);

}
