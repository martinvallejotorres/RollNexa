import {useEffect,useState} from 'react';
import {Link} from 'react-router-dom';
import {api} from '../api';
import type {User} from '../types';
import '../friends.css';

type Friendship = {
  id: number;
  user: User;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  incoming: boolean;
  online: boolean;
};

export default function FriendsSidebar({user}:{user:User|null}) {
  const [friends,setFriends]=useState<Friendship[]>([]);

  useEffect(()=>{
    if(!user){setFriends([]);return;}
    let active=true;
    const load=()=>api<Friendship[]>('/api/friends')
      .then(items=>{
        if(active)setFriends(items
          .filter(item=>item.status==='ACCEPTED')
          .sort((a,b)=>Number(b.online)-Number(a.online)||a.user.username.localeCompare(b.user.username)));
      })
      .catch(()=>{if(active)setFriends([])});
    void load();
    const timer=window.setInterval(load,60_000);
    return()=>{active=false;window.clearInterval(timer)};
  },[user]);

  if(!user)return null;
  const onlineCount=friends.filter(friend=>friend.online).length;

  return <div className="side-section friends-online">
    <span className="side-title">AMIGOS EN LÍNEA <b>{onlineCount}</b></span>
    {friends.length===0
      ? <p className="muted small friends-empty">Tus amigos aparecerán acá cuando acepten la solicitud.</p>
      : friends.map(friend=><Link className="online-friend" to={`/profile/${friend.user.username}`} key={friend.id}>
          <span className="friend-avatar">
            {friend.user.avatarUrl
              ? <img src={friend.user.avatarUrl} alt=""/>
              : friend.user.username.slice(0,2).toUpperCase()}
            <i className={friend.online?'online':'offline'}/>
          </span>
          <span className="friend-presence">
            <strong>{friend.user.username}</strong>
            <small className={friend.online?'is-online':''}>{friend.online?'En línea':'Desconectado'}</small>
          </span>
        </Link>)}
  </div>;
}
