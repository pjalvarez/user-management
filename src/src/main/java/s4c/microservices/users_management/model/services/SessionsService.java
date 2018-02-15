package s4c.microservices.users_management.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s4c.microservices.users_management.model.entity.Sessions;
import s4c.microservices.users_management.model.repository.SessionsRepository;

@Service
public class SessionsService implements ISessionsService{

	@Autowired
	private SessionsRepository sessionsRepository;
	
	/**
	 * Returns a complete list of sessions
	 */
	@Override
	public List<Sessions> listSessions() {
		
		return sessionsRepository.findAll();
	}
	
	@Override
	public Sessions getSessionByIp(String ip){
		return sessionsRepository.findByIp(ip);
	}

	@Override
	public Sessions addSession(Sessions session) {		
		return sessionsRepository.saveAndFlush(session);		
	}
	
	@Override
	public void deleteSession(List<Sessions> sessions){
		this.sessionsRepository.deleteInBatch(sessions);
		this.sessionsRepository.flush();
	}

	@Override
	public void deleteSession(Sessions session){
		this.sessionsRepository.delete(session);
		this.sessionsRepository.flush();
	}
}
